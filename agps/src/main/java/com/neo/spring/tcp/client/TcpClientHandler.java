package com.neo.spring.tcp.client;

import com.neo.spring.common.util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * User: 20160301301
 * Date: 2017/12/24 16:12
 * Comment:
 */
public class TcpClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LogManager.getLogger();

    private int counter;
    //static final String ECHO_REQ = "<id=123456789012345&st=0&key=9de730385f0a41a3a8d9806ddc3686e5";
    static final String ECHO_REQ = "7F003c69643D3132333435363738393031323334352673743D30266B65793D39646537333033383566306134316133613864393830366464633336383665353c";

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000);
            //System.out.println(hexStringToBytes(ECHO_REQ));
            ByteBuf byteBuf = Unpooled.copiedBuffer(StringUtil.hexStringToBytes(ECHO_REQ));
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("This is the " + ++counter + " times receive server : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
