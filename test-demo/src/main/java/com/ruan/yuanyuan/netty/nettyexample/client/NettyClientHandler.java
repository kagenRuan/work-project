package com.ruan.yuanyuan.netty.nettyexample.client;

import com.ruan.yuanyuan.netty.nettyexample.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @ClassName NettyClientHandler
 * @Author ruanyuanyuan
 * @Date 2020/9/28-09:33
 * @Version 1.0
 * @Description TODO SimpleChannelInboundHandler类是ChannelInboundHandler的子类
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    /**
     * 客户端连接成功后发送数据方法
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i <5 ; i++) {
            String msg = "成都"+i;
            MessageProtocol messageProtocol = new MessageProtocol();
            byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
            messageProtocol.setLength(bytes.length);
            messageProtocol.setContent(bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }


    /**
     * ChannelInboundHandler类中的方法
     * 当通道有【read】事件就会触发该方法
     * ChannelHandlerContext：上下文，其中Pipeline,channel,连接地址
     * msg：客户端发送的数据
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLength();
        String content = new String(msg.getContent(), CharsetUtil.UTF_8);
        System.out.println();
        System.out.println("客户端【"+ctx.channel().remoteAddress()+"】接收到消息："+content +"\r\n");
    }

    /**
     * 异常关闭
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常");
        ctx.close();
    }
}
