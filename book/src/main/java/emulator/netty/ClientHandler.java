package emulator.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author lh
 *
 */
public class ClientHandler extends ChannelHandlerAdapter {

	private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String retUrl = "D:\\workspaces\\eclipse-huifu\\emulator\\xml\\account\\manage\\req.xml";
		String ret = null;
		try {
			ret = FileUtils.readFileToString(new File(retUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ByteBuf req = Unpooled.copiedBuffer(ret.getBytes());
		ctx.writeAndFlush(req);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
//		ByteBuf buf = (ByteBuf)msg;
//		byte [] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req, "UTF-8");
		String body = (String)msg;
		System.out.println("server response :\n"+body);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.warning("unexpected exception from downstream:"+ cause.getMessage());
		ctx.close();
	}

}
