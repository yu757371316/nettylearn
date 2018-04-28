package emulator.http;

import emulator.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 模拟客户端处理器
 * @author lh
 * @date 2015-08-11 14:32
 * @version 1.0
 *
 */
public class HttpFileClientHandler extends
		SimpleChannelInboundHandler<FullHttpResponse> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		String xml = null;
		try {
			xml = FileUtils.readFileToString(new File(Constants.ROOT_DIR + "\\xml\\account\\manage\\req.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		URI uri = null;
		try {
			uri = new URI("http://"+Constants.DEFAULT_IP+":"+Constants.DEFAULT_PORT);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		FullHttpRequest req = new DefaultFullHttpRequest(HTTP_1_1,
				HttpMethod.GET,uri.toASCIIString(),
				Unpooled.copiedBuffer(xml, CharsetUtil.UTF_8));

		req.headers().set(HttpHeaders.Names.HOST, Constants.DEFAULT_IP);
        req.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        req.headers().set(HttpHeaders.Names.CONTENT_LENGTH, req.content().readableBytes());

		ctx.writeAndFlush(req);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			FullHttpResponse msg) throws Exception {

		ByteBuf buf =  msg.content();
		byte [] resp = new byte[buf.readableBytes()];
        buf.readBytes(resp);
        String xml = new String(resp,"UTF-8");
//        Service service =
		System.out.println("server cotent:\n"+xml);
	}
}
