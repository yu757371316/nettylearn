package emulator.netty5;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class TcpClient {

	public void connect(int port,String host)throws Exception{

		//配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();

		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
						pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
						pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
						pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
						pipeline.addLast("handler", new TcpClientHandler());
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
		new TcpClient().connect(port, "127.0.0.1");
	}
}
