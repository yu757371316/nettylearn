package emulator.xml.server;

import emulator.Constants;
import emulator.bean.Service;
import emulator.xml.codec.HttpXmlRequestDecoder;
import emulator.xml.codec.HttpXmlResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 *
 * @author lh
 *
 */
public class HttpXmlServer {
	public void run(final int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast("http-decoder",
									new HttpRequestDecoder());
							ch.pipeline().addLast("http-aggregator",
									new HttpObjectAggregator(65536));
							ch.pipeline().addLast(
									"xml-decoder",
									new HttpXmlRequestDecoder(Service.class,
											true));
							ch.pipeline().addLast("http-encoder",
									new HttpResponseEncoder());
							ch.pipeline().addLast("xml-encoder",
									new HttpXmlResponseEncoder());
							ch.pipeline().addLast("xmlServerHandler",
									new HttpXmlServerHandler());
						}
					});
			ChannelFuture future = b.bind(Constants.DEFAULT_IP,port).sync();
			System.out.println("HTTP订购服务器启动，网址是 : " + "http://"+ Constants.DEFAULT_IP+":"	+ port);
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 8080;
		new HttpXmlServer().run(port);
	}
}
