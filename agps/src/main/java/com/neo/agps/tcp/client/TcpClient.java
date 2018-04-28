package com.neo.agps.tcp.client;

import com.neo.agps.tcp.codec.SmartDecoder;
import com.neo.agps.tcp.codec.SmartEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * User: 20160301301
 * Date: 2017/12/24 16:04
 * Comment: TCP客户端
 */
public class TcpClient {

    private static Logger logger = LogManager.getLogger();

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY , true)
                    //.option(ChannelOption.SO_KEEPALIVE, false)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("smartDecoder", new SmartDecoder());
                            ch.pipeline().addLast("smartEncoder", new SmartEncoder());
//                            ch.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//                            ch.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new TcpClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 7500;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.fillInStackTrace();
            }
        }
        new TcpClient().connect(port, "127.0.0.1");
    }
}
