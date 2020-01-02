package com.ruan.yuanyuan.nettyproject.netty.server.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

/**
 * @ClassName: HttpHandler
 * @author: ruanyuanyuan
 * @date: 2019/12/30 14:44
 * @version: 1.0
 * @description: 拦截HTTP协议并处理
 **/
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private URL baseUrl = HttpHandler.class.getProtectionDomain().getCodeSource().getLocation();

    /**
     * 对接收到的数据进行处理
     * @param ctx
     * @param request
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        System.out.println("HttpHandler#channelRead0");
        //如果当前请求头包含ws则让该请求执行下一个handler
        if("/ws".equalsIgnoreCase(request.uri())){
            ctx.fireChannelRead(request.retain());
            return;
        }

        String uri = request.getUri();//获取到请求的uri
        String page = uri.equals("/")?"chart.html":uri;
        RandomAccessFile file = new RandomAccessFile(getFileFormRoot(page),"r");
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        String contentType= "text/html;";
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,contentType+"charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,file.length());
        ctx.write(response);
        ctx.write(new DefaultFileRegion(file.getChannel(),0,file.length()));
        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);//表示数据已发完
        future.addListener(ChannelFutureListener.CLOSE);//如果是长连接这里则不能关闭
        file.close();//关闭文件
    }


    private File getFileFormRoot(String fileName) throws Exception{
     String path = baseUrl.toURI()+"/"+fileName;
     path = path.startsWith("file:")?path.substring(5):path;
     path = path.replaceAll("//","/");
     return new File(path);
    }

    /**
     * 对异常的处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("HttpHandler#exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}
