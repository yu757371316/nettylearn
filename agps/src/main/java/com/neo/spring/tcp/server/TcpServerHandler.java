package com.neo.spring.tcp.server;

import com.neo.spring.common.entity.AgpsUser;
import com.neo.spring.common.util.CheckUtil;
import com.neo.spring.service.itf.AgpsUserServiceI;
import com.neo.spring.service.itf.TechtotopServiceI;
import com.neo.spring.tcp.client.ClientChannelMap;
import com.neo.spring.tcp.protocol.SmartProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;

/**
 * User: 20160301301
 * Date: 2017/12/24 15:37
 * Comment: Tcp服务器实际的业务判断处理
 */
//@Shareable
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private AgpsUserServiceI agpsUserServiceImpl;

    @Autowired
    private TechtotopServiceI techtotopServiceImpl;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ClientChannelMap.remove(ctx.channel());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.debug("CLIENT" + ctx.channel().remoteAddress().toString() + " 接入连接");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientChannelMap.remove(ctx.channel());
        super.channelInactive(ctx);
        ctx.close();
        logger.debug("CLIENT" + ctx.channel().remoteAddress().toString() + " 断开连接");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                logger.debug("读数据超时");
                //todo 发送心跳数据
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                logger.debug("写数据超时");
                ClientChannelMap.remove(ctx.channel());
                ctx.close();
            } else {
                logger.debug("总读写数据超时");
                ClientChannelMap.remove(ctx.channel());
                ctx.close();
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SmartProtocol reqSmartProtocol = (SmartProtocol) msg;
        logger.debug("receive client : [" + reqSmartProtocol.toString() + "]");
        ByteBuf reqBuffer = Unpooled.copiedBuffer(reqSmartProtocol.getContent());
        //判断请求字符串是不是指定的格式
        String[] params = reqBuffer.toString(Charset.defaultCharset()).split("&");
        if (params.length != 3) {
            String resp = "参数格式错误";
            sendResponse(ctx, resp);
        }
        String id = params[0];
        String st = params[1];
        String key = params[2];
        try {
            //参数验证
            checkAuthority(id, st, key);
        } catch (Exception e) {
            String resp = e.getMessage();
            sendResponse(ctx, resp);
        }
        finally {
            ReferenceCountUtil.release(msg); //手动回收，避免内存泄漏。
        }
        //拼接返回的agps协议信息
//        String retUrl = "D:\\Workspaces\\IDEA\\netty\\src\\main\\resources\\agps_tcp.txt";
//        String ret = null;
//        try {
//            ret = FileUtils.readFileToString(new File(retUrl));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //Short.parseShort(String.valueOf(ret.length()));
//        ret = ret.replace(" ", "");
//        String head = "Techtotop A-GNSS Server\n" + "Data Length:2363\n";
//        for (int i = 0;i< StringUtil.hexStringToByteArray(ret).length;i++) {
//            System.out.print(StringUtil.hexStringToByteArray(ret)[i] + " ");
//        }
//        byte[] content = ByteUtil.bytesMerger(head.getBytes(), StringUtil.hexStringToByteArray(ret));
//
//        short contentLength = Short.parseShort(String.valueOf(content.length));
//        byte[] checkBit = new byte[]{0x3C};
//        SmartProtocol smartProtocol = new SmartProtocol(contentLength, content, checkBit[0]);
        SmartProtocol smartProtocol = techtotopServiceImpl.getTcpEphemeris();
        ctx.writeAndFlush(smartProtocol).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * TCP客户端参数的验证
     * @param id
     * @param st
     * @param key
     * @throws Exception
     */
    private void checkAuthority(String id, String st, String key) throws Exception {
        if (StringUtils.isEmpty(id.split("=")[1])) {
            throw new Exception("id is null.");
        }
        //TODO IMEI判断
//        if (StringUtils.isEmpty(st.split("=")[1])) {
//            throw new Exception("st is null.");
//        }
        if (StringUtils.isEmpty(key.split("=")[1])) {
            throw new Exception("key is null.");
        }
        AgpsUser agpsUser = agpsUserServiceImpl.getAgpsUserById(key.split("=")[1]);
        if (agpsUser == null) {
            throw new Exception("key is invalid.");
        }
    }

    /**
     * 发送响应发回客户端
     * @param ctx
     * @param resp
     */
    private void sendResponse(ChannelHandlerContext ctx, String resp) {
        if (resp == null) {
            resp = "";
        }
        short contentLength = Short.parseShort(String.valueOf(resp.length()));
        byte checkBit = CheckUtil.getXorCheck(contentLength, resp.getBytes());
        SmartProtocol smartProtocol = new SmartProtocol(contentLength, resp.getBytes(), checkBit);
        ctx.writeAndFlush(smartProtocol).addListener(ChannelFutureListener.CLOSE);
    }
}
