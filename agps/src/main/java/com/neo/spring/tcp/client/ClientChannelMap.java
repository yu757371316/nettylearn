package com.neo.spring.tcp.client;

import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: 20160301301
 * Date: 2018/4/26 17:10
 * Comment: 用户保存客户端
 */
@Service("cilentChannelMap")
public class ClientChannelMap {
    private static Map<String, Channel> clientMap = new ConcurrentHashMap<String, Channel>();

    public static void add(String imei, Channel channel) {
        clientMap.put(imei, channel);
    }

    public static Channel get(String imei) {
        return clientMap.get(imei);
    }

    public static String get(Channel channel) {
        for (Map.Entry entry : clientMap.entrySet()) {
            if (entry.getValue() == channel) {
                return String.valueOf(entry.getKey());
            }
        }
        return null;
    }

    public static void remove(Channel channel) {
        for (Map.Entry entry : clientMap.entrySet()) {
            if (entry.getValue() == channel) {
                clientMap.remove(entry.getKey());
            }
        }
    }
}
