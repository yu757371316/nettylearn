package emulator.http;

import emulator.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

/**
 * 模拟客户端
 *
 * @author lh
 * @date 2015-08-11 14:31
 * @version 1.0
 *
 */
public class HttpFileClient {

	public void connect(int port) throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {

							// 客户端，对请求编码
							ch.pipeline().addLast("http-encoder",
									new HttpRequestEncoder());
							// 客户端，对响应解码
							ch.pipeline().addLast("http-decoder",
									new HttpResponseDecoder());
							// 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
							ch.pipeline().addLast("http-aggregator",
									new HttpObjectAggregator(65536));
							// 块写入处理器
							ch.pipeline().addLast("http-chunked",
									new ChunkedWriteHandler());
							// 自定义客户端处理器
							ch.pipeline().addLast("fileClientHandler",
									new HttpFileClientHandler());

						}
					});

			// 发起异步连接操作
			ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();

			// 当前客户端链路关闭
			f.channel().closeFuture().sync();

		} finally {
			// 优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new HttpFileClient().connect(Constants.DEFAULT_PORT);
	}
}
