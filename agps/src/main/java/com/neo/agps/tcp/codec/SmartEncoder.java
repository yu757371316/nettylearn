package com.neo.agps.tcp.codec;

import com.neo.agps.tcp.protocol.SmartProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * User: 20160301301
 * Date: 2017/12/25 19:01
 * Comment: 自定义编码器
 */
public class SmartEncoder extends MessageToByteEncoder<SmartProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SmartProtocol smartProtocol, ByteBuf byteBuf) throws Exception {
        //1、写入消息头开始标志
        byteBuf.writeByte(SmartProtocol.getHeadData());
        //2、写入消息长度
        byteBuf.writeShort(smartProtocol.getContentLength());
        //3、写入数据
        byteBuf.writeBytes(smartProtocol.getContent());
        //4、写入校验位数据
        byteBuf.writeByte(smartProtocol.getCheckBit());
    }
}
