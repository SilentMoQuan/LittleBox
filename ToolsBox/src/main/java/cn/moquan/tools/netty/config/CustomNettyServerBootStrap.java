package cn.moquan.tools.netty.config;

import cn.moquan.tools.netty.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:08 </b><br />
 */
public class CustomNettyServerBootStrap {

    public static ServerBootstrap getServer(){

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        return new ServerBootstrap()
                .group(bossGroup, workGroup)
                // 选用 nio channel
                .channel(NioServerSocketChannel.class)
                // so backlog, 当工作线程(worker)使用完之后, 存放已完成三次握手的队列最大长度
                // backlog 积压的任务
                // 当参数小于 1 或者未设置时使用默认值: 50
                .option(ChannelOption.SO_BACKLOG, 50)
                // 是否允许其他程序重复使用本地的地址和端口
                // 两个程序同时使用一个端口是否会报错
                .option(ChannelOption.SO_REUSEADDR, false)
                // ByteBuff 的分配器, 重用缓冲区, 默认值: ByteBufAllocator.DEFAULT
                .option(ChannelOption.ALLOCATOR, ByteBufAllocator.DEFAULT)
                // 小数据包不再进行延时发送
                .option(ChannelOption.TCP_NODELAY, true)
                // TCP消息接收缓冲区大小(接收滑窗大小) 暂时使用默认值
                //.option(ChannelOption.SO_RCVBUF, )
                // TCP消息发送缓冲器大小(发送滑窗大小) 暂时使用默认值
                //.option(ChannelOption.SO_SNDBUF, )
                // 连接超时时间, 目前使用默认值 30000, 单位:毫秒
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30 * 1000)
                // 当关闭连接时, 发送挥手包的延时时间, -1(默认值) 表示无限时
                .option(ChannelOption.SO_LINGER, -1)
                // 连接保活, 默认心跳间隔为 2 小时, 默认值 false
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 控制写入的高低水位标记, 控制写入数据的速度, 具体信息百度, 这里使用默认值
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark.DEFAULT)
                // 自动读取, 默认值: true, 每次有数到达后会自动调用 channel.read()
                .option(ChannelOption.AUTO_READ, true)
                // 一个 loop 中写操作的执行最大次数, 若对大数据量的写操作大于设置值还没有完成就会提交一个新的写任务给 eventLoop
                // 防止被大数据量写影响其他写请求
                // 默认值: 16
                .option(ChannelOption.WRITE_SPIN_COUNT, 16)
                // 使用单线程进行整个pipeline, 默认值: true
                .option(ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP, true)
                // 添加处理链
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 添加一个触发器, 60 秒内没有读操作, 会触发一个 IdleState.READER_IDLE 的事件
                                .addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS))
                                // 心跳时间处理器
                                .addLast(new CustomNettyHeartbeatHandler())
                                // 消息解码器
                                .addLast(new CustomNettyDecodeMessageHandler())
                                // 消息编码器
                                .addLast(new CustomNettyEncodeMessageHandler())
                                // 消息处理器
                                .addLast(new CustomNettyServerHandler())
                                // 添加一个异常捕获处理器, 可以将 pipeline 处理中出现的错误进行捕获并处理
                                .addLast(new CustomNettyExceptionHandler());
                    }
                });
    }

}
