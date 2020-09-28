package com.ruan.yuanyuan.netty.netty_http_example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @ClassName NettyHttpServerHandler
 * @Author ruanyuanyuan
 * @Date 2020/9/28-13:58
 * @Version 1.0
 * @Description TODO SimpleChannelInboundHandler ：是ChannelInboundHandler的子类
 *                   HttpObject：表示客户端的数据将被封装为HttpObject对象
 **/
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){

            HttpRequest request = (HttpRequest) msg;
            //对特定的URI请求进行过滤
            URI uri = new URI(request.uri());
            if(uri.getPath().contains("favicon.ico")){
                return;
            }

            System.out.println("客户端【"+ctx.channel().remoteAddress()+"】");
            //然后对客户端进行相应
            ByteBuf count = Unpooled.copiedBuffer("hello 我是服务端", CharsetUtil.UTF_8);
            //构造Http响应对象
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,count);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,count.readableBytes());
            ctx.writeAndFlush(httpResponse);
        }
    }
}
