package cn.moquan.tools.netty.handler;

import cn.moquan.tools.log.Log;
import cn.moquan.tools.netty.tool.NettyUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:13 </b><br />
 */
public class CustomNettyExceptionHandler extends ChannelDuplexHandler implements Log {

    private static final Logger logger = LoggerFactory.getLogger(CustomNettyExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String longId = NettyUtil.channelLongId(ctx);
        error("处理设备信息时出现异常, 连接即将关闭, channelLongId: {}", longId, cause);
        ctx.close().addListener(future -> logger.info("异常连接已关闭, channelLongId: {}", longId));
    }

    @Override
    public String getPrefix() {
        return " [ Netty Exception Handler ] :: ";
    }

    @Override
    public Logger getLog() {
        return logger;
    }
}
