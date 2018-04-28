package emulator.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class Client {

	public void connect(int port,String host)throws Exception{

		//配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();

		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
//						.addLast(new LineBasedFrameDecoder(1024))
						.addLast(new StringDecoder())
						.addLast(new ClientHandler());
					};
				});

			//发起异步连接操作
			ChannelFuture f = b.connect(host,port).sync();
			//等待客户端链路关闭
			f.channel().closeFuture().sync();
		}finally{
			//退出，释放资源
			group.shutdownGracefully();
		}

	}

	public static void main(String[] args)throws Exception {
		int port = 8083;
		new Client().connect(port, "127.0.0.1");
	}
}
