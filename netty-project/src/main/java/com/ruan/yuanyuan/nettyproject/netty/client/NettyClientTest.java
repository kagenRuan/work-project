package com.ruan.yuanyuan.nettyproject.netty.client;

import com.ruan.yuanyuan.nettyproject.netty.client.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName: NettyClientTest
 * @author: ruanyuanyuan
 * @date: 2019/12/31 14:27
 * @version: 1.0
 * @description: NettyClient test
 **/
public class NettyClientTest {

    public static void main(String[] args) {
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientHandler());
                }
            });
            //连接服务端
            ChannelFuture sync =bootstrap.connect("127.0.0.1",8888).sync();
            //开启同步阻塞监听,直到断开连接才关闭通道
            sync.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭线程组
            workGroup.shutdownGracefully();
        }
    }
}
