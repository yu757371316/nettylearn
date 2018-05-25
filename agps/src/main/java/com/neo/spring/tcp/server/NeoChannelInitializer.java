package com.neo.spring.tcp.server;

import com.neo.spring.tcp.codec.SmartDecoder;
import com.neo.spring.tcp.codec.SmartEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("neoChannelInitializer")
public class NeoChannelInitializer extends ChannelInitializer<SocketChannel> implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //tcpServerHandler必须是每次请求都重新创建一个，底层pipiline不是可共享的,否则多次请求下将报错
        TcpServerHandler tcpServerHandler = ((TcpServerHandler) context.getBean("tcpServerHandler"));

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("smartDecoder", new SmartDecoder());
        pipeline.addLast("smartEncoder", new SmartEncoder());

        // //心跳监测0表示关闭 读超时:5s内没有数据接收，写超时:没有数据发送 全部空闲时间:没有数据接收或者发送
        pipeline.addLast("ping", new IdleStateHandler(3, 0, 0));
        pipeline.addLast(tcpServerHandler);
    }
}
