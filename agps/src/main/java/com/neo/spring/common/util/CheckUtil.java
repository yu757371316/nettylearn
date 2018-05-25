package com.neo.spring.common.util;

/**
 * Created by 20120703007 on 2017/10/11.
 */
public class CheckUtil {

    /**
     * fletcher16校验
     * 帧头(2Byte)+识别码(2Byte)+数据长度(2Byte)+数据(nByte)+校验和(2Byte)+结束符(0D0A)  小端模式
     * 23 3E { 04 02 10 00 20 E1 07 0A 0B 02 3B 21 00 00 00 00 00 28 6B EE } 12 F6 0D 0A
     * @param data
     * @param len
     * @return
     */
    public static int fletCher16(final short[] data, int len)
    {
        int sum1 = 0;
        int sum2 = 0;
        int index = 0;
        while (len -- > 0) {
            sum1 += data[index++];
            sum2 += sum1;
        }
        return getUint16((sum2 << 8) | (sum1 & 0xFF));
    }

    private static int getUint16(int i){
        return i & 0x0000ffff;
    }

    /**
     * 字节数组按位异或求校验和
     */
    public static byte getXorCheck(short contentLength, byte[] content) {
        byte high = (byte) (contentLength >> 8);
        byte low = (byte) (contentLength & 0x00FF);
        byte checkResult = high;
        checkResult ^= low;
        for (int i = 1; i < content.length; i++) {
            checkResult ^= content[i];
        }
        return checkResult;
    }

    /**
     * 16进制字符串按位异或获取校验和算法
     */
    public static String checkcode(String para) {
        int length = para.length() / 2;
        String[] dateArr = new String[length];

        for (int i = 0; i < length; i++) {
            dateArr[i] = para.substring(i * 2, i * 2 + 2);
        }
        String code = "00";
        for (int i = 0; i < dateArr.length; i++) {
            code = xor(code, dateArr[i]);
        }
        return code;
    }

    private static String xor(String strHex_X,String strHex_Y){
        //将x、y转成二进制形式
        String anotherBinary=Integer.toBinaryString(Integer.valueOf(strHex_X,16));
        String thisBinary=Integer.toBinaryString(Integer.valueOf(strHex_Y,16));
        String result = "";
        //判断是否为8位二进制，否则左补零
        if(anotherBinary.length() != 8){
            for (int i = anotherBinary.length(); i <8; i++) {
                anotherBinary = "0"+anotherBinary;
            }
        }
        if(thisBinary.length() != 8){
            for (int i = thisBinary.length(); i <8; i++) {
                thisBinary = "0"+thisBinary;
            }
        }
        //异或运算
        for(int i=0;i<anotherBinary.length();i++){
            //如果相同位置数相同，则补0，否则补1
            if(thisBinary.charAt(i)==anotherBinary.charAt(i))
                result+="0";
            else{
                result+="1";
            }
        }
        return Integer.toHexString(Integer.parseInt(result, 2));
    }
}
