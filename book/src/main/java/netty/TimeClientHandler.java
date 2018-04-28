package netty;

import emulator.Constants;
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
 * @author Administrator
 *
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

	private final ByteBuf firstMessage;
	public TimeClientHandler() {
		byte [] req = "query time order".getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//super.channelActive(ctx);
		String xml = null;
		try {
			xml = FileUtils.readFileToString(new File(Constants.ROOT_DIR + "\\xml\\account\\manage\\req.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte [] req = xml.getBytes();
		ByteBuf buf = Unpooled.buffer(req.length);
		buf.writeBytes(req);
		ctx.writeAndFlush(buf);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//super.channelRead(ctx, msg);
		ByteBuf buf = (ByteBuf)msg;
		byte [] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("now is :"+body);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		//super.exceptionCaught(ctx, cause);
		logger.warning("unexpected exception from downstream:"+ cause.getMessage());
		ctx.close();
	}

}
