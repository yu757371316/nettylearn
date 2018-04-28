package emulator.http;

import emulator.Constants;
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
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 模拟服务端
 * @author lh
 * @date 2015-08-11 14:33
 * @version 1.0
 * 
 */
public class HttpFileServer {

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
							// 服务端，对请求解码
							ch.pipeline().addLast("http-decoder",
									new HttpRequestDecoder());
							// 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
							ch.pipeline().addLast("http-aggregator",
									new HttpObjectAggregator(65536));
							// 服务端，对响应编码
							ch.pipeline().addLast("http-encoder",
									new HttpResponseEncoder());
							// 块写入处理器
							ch.pipeline().addLast("http-chunked",
									new ChunkedWriteHandler());
							// 自定义服务端处理器
							ch.pipeline().addLast("fileServerHandler",
									new HttpFileServerHandler());
						}
					});
			ChannelFuture future = b.bind(Constants.DEFAULT_IP, port).sync();
			System.out.println("HTTP文件目录服务器启动，网址是 : " + "http://" + Constants.DEFAULT_IP	+ ":" + port);
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new HttpFileServer().run(Constants.DEFAULT_PORT);
	}
}
