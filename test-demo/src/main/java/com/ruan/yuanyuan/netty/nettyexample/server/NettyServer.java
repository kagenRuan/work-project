package com.ruan.yuanyuan.netty.nettyexample.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName NettyServer
 * @Author ruanyuanyuan
 * @Date 2020/9/28-08:53
 * @Version 1.0
 * @Description TODO
 **/
public class NettyServer {

    public static void main(String[] args) {
        //仅处理连接请求
        EventLoopGroup boss = new NioEventLoopGroup();
        //处理业务逻辑
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            //设置服务端启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,work)
                    //主要设置服务端通信类型，
                    .channel(NioServerSocketChannel.class) //服务端的通道实现为NioServerSocketChannel
                    //主要为服务端通道设置连接属性
                    .option(ChannelOption.SO_BACKLOG,128) //设置处理请求的个数
                    //主要为给用来接收的通道添加属性
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接
                    //这里添加的Handler主要是针对boos线程的,为Boos线程增加日志Handler
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //这里添加的Handler主要是针对work线程的
                    .childHandler(new NettyServerChannelInit());//设置work中的EventLoop对应的管道处理业务逻辑
            //绑定一个端口并同步
            ChannelFuture future = bootstrap.bind(8888).sync();;
            Channel channel = future.channel();
            System.out.println("-------服务端【"+channel.localAddress()+"】------started");
            //对关闭通道进行监听(当有关闭通道这个事件才会对其进行关闭，并不是马上进行关闭)
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //关闭
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }



    }
}
