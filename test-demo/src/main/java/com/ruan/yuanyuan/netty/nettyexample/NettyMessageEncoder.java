package com.ruan.yuanyuan.netty.nettyexample;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName NettyMessageEncoder
 * @Author ruanyuanyuan
 * @Date 2020/10/2-18:43
 * @Version 1.0
 * @Description TODO 用于将数据进行编码为二进制
 **/
public class NettyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("NettyMessageEncoder 被调用进行编码");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
