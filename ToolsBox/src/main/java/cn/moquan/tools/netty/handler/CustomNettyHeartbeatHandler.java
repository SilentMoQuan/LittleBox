package cn.moquan.tools.netty.handler;

import cn.moquan.tools.log.Log;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:13 </b><br />
 */
public class CustomNettyHeartbeatHandler extends ChannelDuplexHandler implements Log {

    private static final Logger logger = LoggerFactory.getLogger(CustomNettyHeartbeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            IdleState state = event.state();

            switch (state) {
                case READER_IDLE:
                    logger.info("reader idle handler");
                    break;
                case WRITER_IDLE:
                    logger.info("writer idle handler");
                    break;
                case ALL_IDLE:
                    logger.info("all idle handler");
                    break;
                default:
                    logger.error("未知的心跳事件状态, state: {}", state);
                    throw new IllegalArgumentException("未知的心跳事件状态, state: " + state);
            }
        }
    }

    @Override
    public String getPrefix() {
        return " [ Netty Heartbeat Handler ] :: ";
    }

    @Override
    public Logger getLog() {
        return logger;
    }
}
