package com.ruan.yuanyuan.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @ClassName: NettyServer
 * @author: ruanyuanyuan
 * @date: 2020/6/2 10:01
 * @version: 1.0
 * @description:
 **/
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boos = new NioEventLoopGroup();//boos 线程池，默认CPU核数*2
        EventLoopGroup work = new NioEventLoopGroup();//work 线程池，默认CPU核数*2

        //服务端启动入口
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boos,work)
                //设置Channel类型，通过ReflectiveChannelFactory类newChannel()方法进行反射创建
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new HttpResponseEncoder());
                        socketChannel.pipeline().addLast(new HttpRequestDecoder());
                        socketChannel.pipeline().addLast(new MyHandler());//业务处理Handler
                    }
                })
                //针对主线程配置，分配最大线程梳理为128
                .option(ChannelOption.SO_BACKLOG,128)
                //针对子线程的配置，保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE,true);

        //启动方法
        ChannelFuture future = serverBootstrap.bind(8080).sync();
        System.out.println("Netty Server Starting port:"+8080);

    }
}
