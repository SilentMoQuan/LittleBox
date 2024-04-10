package cn.moquan.tools.netty.handler;

import cn.moquan.tools.core.Assert;
import cn.moquan.tools.log.Log;
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

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 消息长度 < 魔数长度的均为半包
        if (in.readableBytes() < NettyConstant.MAGICAL_BYTE_LEN) {
            return;
        }

        protocolCheck(in);

        int oldLen = in.readableBytes();

        boolean isEnd = false;
        if ("A1".equals(address) || "01".equals(address)) {
            isEnd = NettyHandlerHolder.getByType(DeviceTypeEnum.MASTER_CONTROLLER).messageDecode(ctx, in, out);
        }

        if (isEnd) {
            debug("decode msg : {}", out.toString());
        }

        if (oldLen == in.readableBytes() && !out.isEmpty()) {
            error("消息处理异常, 即将引发 netty 读取错误异常");
        }

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
