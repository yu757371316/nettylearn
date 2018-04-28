package emulator.http4;

import emulator.Constants;
import emulator.util.Dom4JUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.util.CharsetUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {


    private FullHttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        if (msg instanceof FullHttpRequest) {
            request = (FullHttpRequest) msg;
        }

        ByteBuf buf = request.content();
        byte [] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String xml = new String(req,"UTF-8");
        buf.release();

       resp(ctx, xml);

    }


    /**
	 *
	 * @param xml
	 */
	private void resp(ChannelHandlerContext ctx, String xml){
		String transCode = Dom4JUtil.header(xml, "transcode");
		String retUrl = "D:\\workspaces\\eclipse-huifu\\emulator\\xml\\error.xml";
		String retCtt = null;
		if(equal(transCode, Constants.TC_DZZH)){//电子账户

			retUrl = "D:\\workspaces\\eclipse-huifu\\emulator\\xml\\account\\manage\\querySuccess.xml";

		}else if(equal(transCode, Constants.TC_YHZHBD)){//银行卡绑定

		}

		try {
			retCtt = FileUtils.readFileToString(new File(retUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}

		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
				Unpooled.copiedBuffer(retCtt, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/xml; charset=UTF-8");
		response.headers().set(CONTENT_LENGTH,
				response.content().readableBytes());
		if (HttpHeaders.isKeepAlive(request)) {
			response.headers().set(CONNECTION, Values.KEEP_ALIVE);
		}
		ctx.writeAndFlush(response);

//		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
//				OK, Unpooled.copiedBuffer(retCtt, CharsetUtil.UTF_8));
//		response.headers().set(CONTENT_TYPE, "text/xml; charset=UTF-8");
//		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

	}


	public static boolean equal(String var, String cons){
		return isNotEmpty(var) && cons.equals(var);
	}

	private static boolean isNotEmpty(String s){
		return (null != s && !"".equals(s));
	}

	private static void sendError(ChannelHandlerContext ctx,
			HttpResponseStatus status) {
		String ret =  null;
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				status, Unpooled.copiedBuffer(ret, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/xml; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}