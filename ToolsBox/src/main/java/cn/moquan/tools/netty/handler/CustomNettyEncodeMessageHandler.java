package cn.moquan.tools.netty.handler;

import cn.moquan.tools.core.ByteUtil;
import cn.moquan.tools.log.Log;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:13 </b><br />
 */
public class CustomNettyEncodeMessageHandler extends MessageToByteEncoder<String> implements Log {

    private static final Logger logger = LoggerFactory.getLogger(CustomNettyEncodeMessageHandler.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        byte[] encodedMessage = ByteUtil.hexStringToBytes(msg);
        debug("message : {}, encoded Message : {}", msg, encodedMessage);
        out.writeBytes(encodedMessage);
    }

    @Override
    public String getPrefix() {
        return " [ Netty Encode Message Handler ] :: ";
    }

    @Override
    public Logger getLog() {
        return logger;
    }
}
