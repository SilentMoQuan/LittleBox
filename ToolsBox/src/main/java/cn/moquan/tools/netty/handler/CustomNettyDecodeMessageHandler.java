package cn.moquan.tools.netty.handler;

import cn.moquan.tools.core.Assert;
import cn.moquan.tools.log.Log;
import cn.moquan.tools.modbus.CRC;
import cn.moquan.tools.netty.NettyConstant;
import cn.moquan.tools.netty.NettyServerBaseException;
import cn.moquan.tools.string.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:13 </b><br />
 */
public class CustomNettyDecodeMessageHandler extends ByteToMessageDecoder implements Log {

    private static final Logger logger = LoggerFactory.getLogger(CustomNettyDecodeMessageHandler.class);

    private static final CRC CRC_UTIL = new CRC();

    /**
     * 消息解码
     * 1. 数据最短长度问题
     * 2. 数据内容异常问题(数据长度信息异常导致的数据读取失败)
     * 3. 数据CRC校验失败问题
     * 4. 数据半包处理
     * 5. 数据粘包处理
     * 6. 消息转码
     * 注意:
     * 1. 不要使用绝对位置进行数据获取, 尤其是调用 ByteBuf.getByte(int); 接口, 其获取的是绝对地址
     * 2. 慎用 ByteBuf.resetReaderIndex() 接口, 在循环解码处理半包问题时, 重置读指针会造成重读问题
     *
     * @param ctx
     * @param in
     * @param out
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 消息长度 < 魔数长度的均为半包
        if (in.readableBytes() < NettyConstant.MAGICAL_BYTE_LEN) {
            return;
        }

        protocolCheck(in);

        int oldLen = in.readableBytes();

        boolean isEnd = messageDecoder(in, out);

        if (isEnd) {
            debug("decode msg : {}", out.toString());
        }

        if (oldLen == in.readableBytes() && !out.isEmpty()) {
            error("消息处理异常, 即将引发 netty 读取错误异常");
        }

    }

    private boolean messageDecoder(ByteBuf in, List<Object> out) {

        do {
            in.markReaderIndex();
            int readableLen = in.readableBytes();
            if (readableLen < NettyConstant.CUSTOM_PROTOCOL_MIN_BYTE_LEN) {
                return false;
            }

            byte[] dataLenBuff = new byte[2];
            in.readBytes(dataLenBuff, 8, 2);
            int dataLen = dataLenBuff[0] << 8 + dataLenBuff[1];

            in.resetReaderIndex();
            int completeMessageLen = 8 + 4 + 4 + 4 + dataLen + 4;
            if (readableLen < completeMessageLen){
                return false;
            }

            byte[] buff = new byte[completeMessageLen];
            in.readBytes(buff);

            String msg = StringUtil.bytesToHexString(buff);
            if (!CRC_UTIL.checkCRC16(msg)) {
                // discard
                return true;
            }
            out.add(msg);

        } while (0 == in.readableBytes());

        return true;
    }

    private void protocolCheck(ByteBuf in) {
        // 标记当先 reader 指针
        in.markReaderIndex();

        // 缓存
        byte[] buff = new byte[NettyConstant.MAGICAL_BYTE_LEN];
        in.readBytes(buff);

        // 将字节转换为 Hex 字符串
        String magical = StringUtil.bytesToHexString(buff);

        // 检查是否为支持的协议
        boolean isCustomProtocol = Objects.equals(magical.length(), NettyConstant.MAGICAL_HEX_LEN) &&
                Objects.equals(magical, NettyConstant.CUSTOM_PROTOCOL_PREFIX);

        // 重置 reader 指定到标记处
        in.resetReaderIndex();

        // 检查
        Assert.isTrue(isCustomProtocol, () -> new NettyServerBaseException("不支持的协议类型"));
    }

    @Override
    public String getPrefix() {
        return "[ Netty Message Decode Handler ] :: ";
    }

    @Override
    public Logger getLog() {
        return logger;
    }
}
