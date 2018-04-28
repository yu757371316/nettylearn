package emulator.netty5;


import emulator.Constants;
import emulator.util.Dom4JUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TcpServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String)msg;
		System.out.println("request content:\n"+ body);
		//响应
		resp(ctx, body);
	}


	/**
	 *
	 * @param xml
	 */
	private void resp(ChannelHandlerContext ctx, String xml){
		String transCode = Dom4JUtil.header(xml, "TransCode");
		String retUrl = "D:\\workspaces\\eclipse-huifu\\emulator\\xml\\error.xml";
		String retCtt = null;
		System.out.println("交易代码："+transCode);
		if(equal(transCode, Constants.TC_DZZH)){//电子账户
			retUrl = "D:\\workspaces\\eclipse-huifu\\emulator\\xml\\account\\manage\\resp.xml";
		}
		try {
			retCtt = FileUtils.readFileToString(new File(retUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ByteBuf resp = Unpooled.copiedBuffer(retCtt.getBytes());
		ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);

	}

	public static boolean equal(String var, String cons){
		return isNotEmpty(var) && cons.equals(var);
	}

	private static boolean isNotEmpty(String s){
		return (null != s && !"".equals(s));
	}


	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		//ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		//super.exceptionCaught(ctx, cause);
		ctx.close();
	}

}

