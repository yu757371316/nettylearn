package com.neo.spring.tcp.codec;

import com.neo.spring.common.util.CheckUtil;
import com.neo.spring.tcp.protocol.SmartProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * User: 20160301301
 * Date: 2017/12/25 19:07
 * Comment: 自定义解码器
 */
public class SmartDecoder extends ByteToMessageDecoder {

    private static Logger logger = LogManager.getLogger();

    /**
     * 协议开始的标准HEAD_DATA，byte类型，占据1个字节.
     * 表示数据的长度contentLength，short类型，占据2个字节.
     */
    public final int BASE_LENGTH = 1 + 2;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 可读长度必须大于基本长度
        if (byteBuf.readableBytes() >= BASE_LENGTH) {
            // 防止socket字节流攻击/客户端传来的数据过大
            if (byteBuf.readableBytes() > 128) {
                byteBuf.skipBytes(byteBuf.readableBytes());
            }
            // 记录包头开始的index
            int beginReader;
            while (true) {
                // 获取包头开始的index
                beginReader = byteBuf.readerIndex();
                // 标记包头开始的index
                byteBuf.markReaderIndex();
                // 读到了协议的开始标志，结束while循环
                if (byteBuf.readByte() == SmartProtocol.getHeadData()) {
                    break;
                }
                // 未读到包头，略过一个字节
                // 每次略过，一个字节，去读取，包头信息的开始标记
                byteBuf.resetReaderIndex();
                byteBuf.readByte();

                // 当略过一个字节之后，数据包的长度可能又变得不满足
                // 此时应该结束。等待后面的数据到达
                if (byteBuf.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }

            // 消息的长度
            short contentLength = byteBuf.readShort();
            // 判断请求数据包数据是否到齐
            if (byteBuf.readableBytes() < contentLength) {
                // 还原读指针
                byteBuf.readerIndex(beginReader);
                return;
            }
            // 读取data数据
            byte[] data = new byte[contentLength];
            byteBuf.readBytes(data);

            // 判断校验位请求数据包数据是否到齐
            if (byteBuf.readableBytes() < 1) {
                // 还原读指针
                byteBuf.readerIndex(beginReader);
                return;
            }
            // 读取校验位数据
            byte checkBit = byteBuf.readByte();
            //校验判断数据是否出错
            byte checkResult = CheckUtil.getXorCheck(contentLength, data);
            logger.debug("receive checkBit:" + checkBit + " calculate checkResult:" + checkResult);
            if (checkBit == checkResult) {
                SmartProtocol protocol = new SmartProtocol(contentLength, data, checkBit);
                list.add(protocol);
            }
        }
    }
}
