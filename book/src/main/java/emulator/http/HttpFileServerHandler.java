package emulator.http;

import emulator.Constants;
import emulator.util.Dom4JUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 模拟服务端处理器
 * @author lh
 * @date 2015-08-11 14:33
 * @version 1.0
 *
 */
public class HttpFileServerHandler extends
		SimpleChannelInboundHandler<FullHttpRequest> {

	@Override
	public void messageReceived(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {

		if (!request.getDecoderResult().isSuccess()) {
			sendError(ctx, BAD_REQUEST);
			return;
		}

		ByteBuf buf =  request.content();
		byte [] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String xml = new String(req,"UTF-8");
        resp(ctx,xml);
	}

	/**
	 *
	 * @param xml
	 */
	private void resp(ChannelHandlerContext ctx, String xml){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(new Date()));
		System.out.println("request content:\n"+xml);
		System.out.println("");
		String transCode = Dom4JUtil.header(xml, "TransCode");
		String operType =  Dom4JUtil.request(xml, "OperType");

		Map<String, String> headNodes = Dom4JUtil.header(xml);
		Map<String, String> reqNodes = Dom4JUtil.request(xml);


		String dir = Constants.ROOT_DIR;

		String retUrl = dir + "\\xml\\error.xml";
		String retCtt = null;
		if(equal(transCode, Constants.TC_DZZH)){//电子账户
			if("1".equals(operType)){
				retUrl = dir + "\\xml\\account\\manage\\resp.xml";
				reqNodes.put("EAcctNo", System.currentTimeMillis()+"");
			}

		}else if(equal(transCode, Constants.TC_YHZHBD)){//银行卡绑定

			if("1".equals(operType)){//绑定
				retUrl = dir + "\\xml\\bankcard\\rt_bindCard_s.xml";

			}else if("2".equals(operType)){//解绑
				retUrl = dir + "\\xml\\bankcard\\rt_unbindCard_s.xml";

			}else if("3".equals(operType)){//查询
				retUrl = dir + "\\xml\\bankcard\\rt_queryCard_s.xml";
			}

		}else if(equal(transCode, Constants.TC_HQZHYZM)){//获取账户验证码
			retUrl = dir + "\\xml\\bankcard\\rt_bindCardVerifyCode_s.xml";
		}

		try {
			retCtt = FileUtils.readFileToString(new File(retUrl));
			//头部分
			retCtt = Dom4JUtil.modifyHeader(retCtt, headNodes);
			//响应部分，写入request传递内容
			retCtt = Dom4JUtil.modifyResponse(retCtt, reqNodes);


		} catch (IOException e) {
			e.printStackTrace();
		}
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				HttpResponseStatus.FOUND, Unpooled.copiedBuffer(retCtt, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/xml; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

	}


	public static boolean equal(String var, String cons){
		return isNotEmpty(var) && cons.equals(var);
	}

	private static boolean isNotEmpty(String s){
		return (null != s && !"".equals(s));
	}

	/**
	 * 错误处理
	 * @param ctx
	 * @param status
	 */
	private static void sendError(ChannelHandlerContext ctx,
			HttpResponseStatus status) {
		String ret =  null;
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				status, Unpooled.copiedBuffer(ret, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/xml; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
		cause.printStackTrace();
	}

}
