package cn.moquan.tools.netty;

import cn.moquan.tools.log.Log;
import io.netty.bootstrap.ServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 16:59 </b><br />
 */
public class NettyServer implements Log {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private static final ServerBootstrap NETTY_SERVER = new ServerBootstrap();

    public static void start() {
        try {
            NETTY_SERVER.bind(9898).sync();
        } catch (InterruptedException e) {
            logger.error("Netty server 运行时出现中断异常, 请检查!");
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String getPrefix() {
        return "[ Netty Server ] :: ";
    }

    @Override
    public Logger getLog() {
        return logger;
    }
}
