package com.ruan.yuanyuan.netty.nettyexample.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName NettyServerHandler
 * @Author ruanyuanyuan
 * @Date 2020/9/28-09:10
 * @Version 1.0
 * @Description TODO Netty服务端处理业务使用的Handler
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    //将所有的Channel进行保存，推送消息给所有的Channel
    public static final Map<Integer, Channel> allChannel = new HashMap<>();

    /**
     * 当通道有【read】事件就会触发该方法
     * ChannelHandlerContext：上下文，其中Pipeline,channel,连接地址
     * msg：客户端发送的数据
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        System.out.println("ChannelHandlerContext: "+ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端接受到客户端【"+ctx.channel().remoteAddress()+"】消息："+byteBuf.toString(CharsetUtil.UTF_8));

        //服务端自定义普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("服务端自定义普通任务执行");
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello Client2",CharsetUtil.UTF_8));
            }
        });

        //对所有的Channel推送消息
        pushMessage(ctx,byteBuf);

    }

    /**
     * 该方法对应NIO【write】事件
     * 数据库读取完毕后，给客户端响应
     **/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello Client1",CharsetUtil.UTF_8));
    }


    /**
     * 异常处理，通常如果发送异常关闭通道
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    //对所有的Channel都进行推送消息
    public void pushMessage(ChannelHandlerContext ctx,ByteBuf byteBuf){
        allChannel.forEach((k,v) ->v.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                v.pipeline().channel().writeAndFlush(Unpooled.copiedBuffer(byteBuf.toString(CharsetUtil.UTF_8)+k,CharsetUtil.UTF_8));
            }
        }));
    }


    public NettyServerHandler() {

    }

    public NettyServerHandler(Channel channel) {
        allChannel.put(channel.hashCode(),channel);
        System.out.println(allChannel.size()+"===channel的数量");
    }
}
