package com.neo.spring.common.util;

/**
 * User: 20160301301
 * Date: 2017/12/26 16:36
 * Comment: Byte数据的各种操作
 */
public class ByteUtil {

    /**
     * 合并两个byte数组
     */
    public static byte[] bytesMerger(byte[] byte1, byte[] byte2) {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }

}
