package com.ruan.yuanyuan.netty.nettyexample.client;

import com.ruan.yuanyuan.netty.nettyexample.MessageProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName NettyClient
 * @Author ruanyuanyuan
 * @Date 2020/9/28-09:27
 * @Version 1.0
 * @Description TODO Netty客户端
 **/
public class NettyClient {

    public static void main(String[] args) {

        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            //设置客户端的启动参数
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClienChanneltInit());

            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8888)).sync();
            Channel channel = future.channel();
            System.out.println("-------客户端【"+channel.remoteAddress()+"】-------started");
            //TODO 客户端启动成功后每隔5秒发送一次消息给服务端用于检测服务端是否断开连接
            idleDateTime(channel);
            //TODO 客户端发送消息给服务端
            writeAndFlush(channel);
            //异步编程，如果有关闭事件后才会执行关闭动作
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static void writeAndFlush(Channel channel){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();
            channel.writeAndFlush(msg);
        }
    }


    public static void idleDateTime(Channel channel){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String msg = "心跳【丁儿当，丁儿当，丁二小叮当...】";
                byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
                MessageProtocol messageProtocol = new MessageProtocol();
                messageProtocol.setLength(bytes.length);
                messageProtocol.setContent(bytes);
                messageProtocol.setType("IDLE");
                channel.writeAndFlush(messageProtocol);
            }
        },5000,5000);
    }
}
