package com.neo.agps.service.itf;

import com.neo.agps.tcp.protocol.SmartProtocol;

/**
 * Creator      : 20160301301
 * Created Date : 2018/1/3
 * Comment      : 9:14
 */
public interface TechtotopServiceI {
    SmartProtocol getTcpEphemeris() throws Exception ;
}
