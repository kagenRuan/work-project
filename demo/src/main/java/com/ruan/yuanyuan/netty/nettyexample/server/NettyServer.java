package com.ruan.yuanyuan.netty.nettyexample.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
                    .channel(NioServerSocketChannel.class) //服务端的通道实现为NioServerSocketChannel
                    .option(ChannelOption.SO_BACKLOG,128) //设置线程队列连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接
                    .childHandler(new NettyServerChannelInit());//设置work中的EventLoop对应的管道处理业务逻辑
            System.out.println("服务端。。。is ready");
            //绑定一个端口并同步
            ChannelFuture future = bootstrap.bind(8888).sync();;
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
