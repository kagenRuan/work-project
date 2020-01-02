package com.ruan.yuanyuan.nettyproject.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName: ClientHandler
 * @author: ruanyuanyuan
 * @date: 2019/12/31 14:38
 * @version: 1.0
 * @description: 客户端的Handler
 **/
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    //处理服务端返回的数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] data = new byte[byteBuf.readableBytes()];//byteBuf.readableBytes():获取可用的长度
        byteBuf.readBytes(data);//数据读取到数组中
        String string = new String(data,"utf-8");
        System.out.println("客户端接受到服务端返回的数据:"+string);
    }

    /**
     * 当客户端与服务端连接成功后会触发此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端与服务端连接成功");
        Channel channel = ctx.channel();
        for(int i=0;i<100;i++){
            String msg = "我是客户端"+i;
            byte[] bytes = msg.getBytes();
            channel.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
        }
    }

    /**
     * 异常情况
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印信息
        ctx.close();//关闭通道
    }
}
