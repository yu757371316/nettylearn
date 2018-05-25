package com.neo.spring.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * User: 20160301301
 * Date: 2017/12/24 15:27
 * Comment: TCP服务端
 */
public class TcpServer implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LogManager.getLogger();

    @Value("${tcp.neoway.port}")
    private int port;

    @Autowired
    private NeoChannelInitializer neoChannelInitializer;

    //用于分配处理业务线程的线程组个数
    protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;    //默认

    //业务出现线程大小
    protected static final int BIZTHREADSIZE = 8;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        if (evt.getApplicationContext().getParent() == null) {
            try {
                logger.info("Tcp监听端口：" + port + " 正在启动TcpServer ......");
                bind(port);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("TcpServer 启动失败");
            }
        }
    }

    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
        EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 64 * 1024) //水位高值
                    .option(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 32 * 1024)   //水位低值
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)   //Java版的Jemalloc内存管理库,使用对象池，重用缓冲区
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT) //容量可自动动态调整的接收缓冲区分配器，减少内存使用
                    .childHandler(neoChannelInitializer);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            logger.info("TcpServer 已关闭");
        }
    }
}
