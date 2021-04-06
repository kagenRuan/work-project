package com.ruan.yuanyuan.netty.nettyexample;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ClassName NettyMessageDecoder
 * @Author ruanyuanyuan
 * @Date 2020/10/2-18:46
 * @Version 1.0
 * @Description TODO 用于解码
 **/
public class NettyMessageDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("NettyMessageDecoder decode被调用");
        //将得到的二进制字节码转换为MessageProtocol协议包
        int length = in.readInt();//读取发送数据的长度
        byte[] bytes = new byte[length];
        in.readBytes(bytes);//根据单个数据包的长度，再进行读取数据
        //将字节的长度和内容封装为一个MessageProtocol，并交给下一个Handler处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLength(length);
        messageProtocol.setContent(bytes);
        out.add(messageProtocol);

    }
}
