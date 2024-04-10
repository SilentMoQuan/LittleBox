package cn.moquan.tools.netty.tool;

import io.netty.channel.ChannelHandlerContext;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:19 </b><br />
 */
public class NettyUtil {

    private NettyUtil() {
        throw new IllegalStateException("util class");
    }

    public static String channelLongId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }

}
