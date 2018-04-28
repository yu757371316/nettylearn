package emulator.netty5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


public class TcpServer {

	public void bind(int port)throws Exception{

		//配置服务端Nio线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
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
			ChannelPipeline pipeline = channel.pipeline();
			pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
			pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
			pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
			pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
			pipeline.addLast(new TcpServerHandler());
		}
	}

	public static void main(String[] args) throws Exception{
		int port = 8083;
		new TcpServer().bind(port);
	}
}
