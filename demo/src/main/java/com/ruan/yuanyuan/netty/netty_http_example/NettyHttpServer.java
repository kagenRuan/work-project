package com.ruan.yuanyuan.netty.netty_http_example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName NettyHttpServer
 * @Author ruanyuanyuan
 * @Date 2020/9/28-13:58
 * @Version 1.0
 * @Description TODO  使用Netty编写Http服务器案例
 **/
public class NettyHttpServer {

    public static void main(String[] args){
        EventLoopGroup boos = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boos,work)
                    .channel(NioServerSocketChannel.class) //服务端的通道实现为NioServerSocketChannel
                    .childHandler(new NettyHttpServerChannelInit());
            ChannelFuture sync = serverBootstrap.bind(8080).sync();
            sync.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boos.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
