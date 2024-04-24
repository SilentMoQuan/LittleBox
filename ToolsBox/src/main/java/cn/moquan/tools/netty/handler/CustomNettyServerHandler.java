package cn.moquan.tools.netty.handler;

import cn.moquan.tools.log.Log;
import cn.moquan.tools.netty.tool.NettyUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:13 </b><br />
 */
public class CustomNettyServerHandler extends ChannelDuplexHandler implements Log {

    private static final Logger logger = LoggerFactory.getLogger(CustomNettyServerHandler.class);
//
//    /**
//     * channel 注册完成时会调用该方法
//     */
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        debug("channelRegistered: {}", getChannelLongId(ctx));
//    }
//
//    @Override
//    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        debug("channelUnregistered: {}", getChannelLongId(ctx));
//    }
//
//    /**
//     * 信道处于活跃状态时调用
//     */
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        String channelLongId = NettyUtil.channelLongId(ctx);
////        if (CommonNettyCacheContext.containsKeyChannel(ctx)) {
////            warn("netty 连接, channel 缓存对象已存在, 请检查, channelLongId: {}", channelLongId);
////            return;
////        }
//
////        CommonNettyCacheContext.putChannelCache(ctx);
//        debug("netty 连接, channel 缓存对象生成成功, channelLongId: {}", channelLongId);
//
//        String queryIdOrder = "A103AAAAAAAAA24D";
//
//        // 发送板号查询指令
//        ctx.channel().writeAndFlush(queryIdOrder);
//        debug("netty 连接, 板号查询指令已发送, order: {}", queryIdOrder);
//    }
//
//    /**
//     * 信道不再活跃时调用
//     */
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        String channelLongId = getChannelLongId(ctx);
//        CommonNettyCache<?> cache = CommonNettyCacheContext.getCacheByChannel(ctx);
//        if (Objects.isNull(cache)) {
//            warn("natty 连接断开, channel 缓存对象为空, 请检查, channelLongId: {}", channelLongId);
//            return;
//        }
//
//        // 删除channel缓存
//        CommonNettyCacheContext.removeByChannel(ctx);
//
//        // 删除设备缓存
//        String deviceId = cache.deviceId();
//        if (Objects.nonNull(deviceId)) {
//            CommonNettyCacheContext.removeByDeviceId(deviceId);
//            DeviceCacheContext.removeByKey(deviceId);
//        }
//
//        debug("netty server handler, channelInactive, 缓存删除完成, channelLongId: {}", channelLongId);
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        debug("msg: {}", msg);
//        NettyUtil.getNettyHandler(ctx).deviceMessageHandler(ctx, msg);
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        debug("channelReadComplete: {}", getChannelLongId(ctx));
//    }
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        debug("handlerAdder: {}", getChannelLongId(ctx));
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        debug("handlerRemoved: {}", getChannelLongId(ctx));
//    }
//
//    private String getChannelLongId(ChannelHandlerContext ctx) {
//        return NettyUtil.channelLongId(ctx);
//    }

    @Override
    public String getPrefix() {
        return "[ Netty Server Handler ] :: ";
    }

    @Override
    public Logger getLog() {
        return logger;
    }
}
