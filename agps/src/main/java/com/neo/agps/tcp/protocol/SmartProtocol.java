package com.neo.agps.tcp.protocol;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: 20160301301
 * Date: 2017/12/25 18:48
 * Comment: 自定义协议封装
 * +------------+------------+------------+------------+
 * |协议开始标志 |    长度    |    数据    | 校验位(异或)|
 * +------------+------------+------------+------------+
 * |   0x7F     |    2字节   |    数据    |    1字节   |
 * +------------+------------+------------+------------+
 */
public class SmartProtocol {

    /**
     * 自定义协议的开始位置
     */
    private static final byte HEAD_DATA = 0x7F;

    /**
     * 消息的长度
     */
    private short contentLength;

    /**
     * 消息的内容
     */
    private byte[] content;

    /**
     * 校验位
     */
    private byte checkBit;

    public SmartProtocol(short contentLength, byte[] content, byte checkBit) {
        this.contentLength = contentLength;
        this.content = content;
        this.checkBit = checkBit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("HEAD_DATA", SmartProtocol.HEAD_DATA)
                .append("contentLength", contentLength)
                .append("content", String.valueOf(content).toString())
                .append("checkBit", checkBit)
                .toString();
    }

    public static byte getHeadData() {
        return HEAD_DATA;
    }

    public short getContentLength() {
        return contentLength;
    }

    public void setContentLength(short contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte getCheckBit() {
        return checkBit;
    }

    public void setCheckBit(byte checkBit) {
        this.checkBit = checkBit;
    }
}
