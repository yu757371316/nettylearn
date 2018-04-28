package emulator.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;


public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {
	private static Logger logger = LogManager.getLogger();


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		super.channelActive(ctx);

		String retUrl = "D:\\Workspaces\\practice\\emulator\\xml\\account\\manage\\agps.txt";
		String ret = null;
		try {
			ret = FileUtils.readFileToString(new File(retUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ByteBuf req = Unpooled.copiedBuffer(ret.getBytes());
		ctx.writeAndFlush(ret);
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {

//		ByteBuf buf =  (ByteBuf)msg;
//		byte [] resp = new byte[buf.readableBytes()];
//        buf.readBytes(resp);
//        String xml = new String(resp,"UTF-8");
		String xml = (String) msg;
		System.out.println("server cotent:\n"+xml);

	}


}