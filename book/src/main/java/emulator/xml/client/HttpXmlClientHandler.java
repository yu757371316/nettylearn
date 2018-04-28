package emulator.xml.client;

import emulator.bean.ServiceFactory;
import emulator.xml.codec.HttpXmlRequest;
import emulator.xml.codec.HttpXmlResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author lh
 *
 */
public class HttpXmlClientHandler extends
		SimpleChannelInboundHandler<HttpXmlResponse> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		HttpXmlRequest request = new HttpXmlRequest(null,
				ServiceFactory.req("31001"));
		System.out.println(request.getBody().toString());
		ctx.writeAndFlush(request);
	}
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			HttpXmlResponse msg) throws Exception {
		System.out.println("The client receive response of http header is : "
				+ msg.getHttpResponse().headers().names());
		System.out.println("The client receive response of http body is : "
				+ msg.getResult());
	}
}
