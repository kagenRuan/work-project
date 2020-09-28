package com.ruan.yuanyuan.netty.nettyexample.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

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
                    .handler(new NettyClientInit());

            System.out.println("客户端。。is ok");
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8888)).sync();
            //异步编程，如果有关闭事件后才会执行关闭动作
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            loopGroup.shutdownGracefully();
        }
    }
}
