package emulator.tcp;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TcpClient {
	private static Logger logger = LogManager.getLogger();
	public static String HOST = "127.0.0.1";
	public static int PORT = 9999;

//	public static Bootstrap bootstrap =  getBootstrap();
//	public static Channel channel = getChannel(HOST,PORT);
	/**
	 * 初始化Bootstrap
	 * @return
	 */
	public static final Bootstrap getBootstrap(){
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4, false));
				pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
				pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
				pipeline.addLast("handler", new TcpClientHandler());
			}
		});
//		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.option(ChannelOption.TCP_NODELAY, true);
		return b;
	}

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


//	public static final Channel getChannel(String host,int port){
//		Channel channel = null;
//		try {
//			channel = bootstrap.connect(host, port).sync().channel();
//		} catch (Exception e) {
//			logger.error(String.format("连接Server(IP[%s],PORT[%s])失败", host,port),e);
//			return null;
//		}
//		return channel;
//	}
//
//	public static void sendMsg(String msg) throws Exception {
//		if(channel!=null){
//			channel.writeAndFlush(msg).sync();
//		}else{
//			logger.warn("消息发送失败,连接尚未建立!");
//		}
//	}



    public static void main(String[] args) throws Exception {
    	new TcpClient().connect(PORT, HOST);
    }
}

