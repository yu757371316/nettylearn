package netty;

import emulator.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class TimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//super.channelRead(ctx, msg);
		ByteBuf buf = (ByteBuf) msg;
		byte [] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("The time server receive order:"+body);
//		String currentTime = "query time order".equalsIgnoreCase(body)?new Date(
//				System.currentTimeMillis()).toString():"bad order";
//		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());

		String dir = Constants.ROOT_DIR;
		String retUrl =  dir + "\\xml\\error.xml";
		String retCtt = null;
		try {
			retCtt = FileUtils.readFileToString(new File(retUrl));




		} catch (IOException e) {
			e.printStackTrace();
		}

		byte [] respb = retCtt.getBytes();
		ByteBuf resp = Unpooled.buffer(respb.length);
		resp.writeBytes(respb);
		ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//super.channelReadComplete(ctx);
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		//super.exceptionCaught(ctx, cause);
		ctx.close();
	}

}

