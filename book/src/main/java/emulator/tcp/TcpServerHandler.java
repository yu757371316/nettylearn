package emulator.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger logger = LogManager.getLogger();

//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//       logger.info("SERVER接收到消息:"+msg);
//		ctx.channel().writeAndFlush("yes, server is accepted you ,nice !"+msg);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().writeAndFlush("yes, server is accepted you ,nice !" + msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }
}