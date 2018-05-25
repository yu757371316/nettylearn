package com.neo.spring.service.impl;

import com.neo.spring.common.util.ByteUtil;
import com.neo.spring.common.util.CheckUtil;
import com.neo.spring.service.itf.TechtotopServiceI;
import com.neo.spring.tcp.protocol.SmartProtocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * User: 20160301301
 * Date: 2018/1/3 9:15
 * Comment:
 */
@Service
public class TechtotopServiceImpl implements TechtotopServiceI {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public SmartProtocol getTcpEphemeris() throws Exception {
        byte[] ephBytes = "this is test data".getBytes();
        //获取长度
        int len = ephBytes.length + 1;  //+1，从第1列开始计算，2403列，即第2404列
        //获取GPS/BDS星历
        String headContent = "this is test header \n data length: " + Integer.toString(len) + "\n";
        byte[] content = ByteUtil.bytesMerger(headContent.getBytes(), ephBytes);   //GPS/BDS星历
        short contentLength = Short.parseShort(String.valueOf(content.length));
        byte checkBit = CheckUtil.getXorCheck(contentLength, content);
        SmartProtocol smartProtocol = new SmartProtocol(contentLength, content, checkBit);
        return smartProtocol;
    }
}
