package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class TimeServer {

	public void bind(int port)throws Exception{

		//配置服务端Nio线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				//.childHandler(new TimeServerHandler());
				.childHandler(new ChildChannelHandler());
			//绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();

		}finally{
			//退出时释放资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}


	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
			channel.pipeline().addLast(new TimeServerHandler());
		}
	}

	public static void main(String[] args) throws Exception{
		int port = 8083;
		if(args!=null && args.length > 0){
			port = Integer.valueOf(args[0]);
		}
		new TimeServer().bind(port);
	}
}
