package com.ruan.yuanyuan.netty.nettyexample.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ClassName NettyServerIdleHandler
 * @Author ruanyuanyuan
 * @Date 2020/9/28-22:36
 * @Version 1.0
 * @Description TODO 主要是处理心跳检测Handler
 **/
public class NettyClientIdleHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent){
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            switch (stateEvent.state()){
                case READER_IDLE:
                    System.out.println("服务端【"+ctx.channel().remoteAddress()+"】超时事件发生--->：读空闲");
                    break;
                case WRITER_IDLE:
                System.out.println("服务端【"+ctx.channel().remoteAddress()+"】超时事件发生--->：写空闲");
                    break;
                case ALL_IDLE:
                    System.out.println("服务端【"+ctx.channel().remoteAddress()+"】超时事件发生--->：读写空闲");
                    break;
            }


        }
    }
}
