package com.ruan.yuanyuan.netty.nettyexample.server;

import com.ruan.yuanyuan.netty.nettyexample.MessageProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName NettyServerWeChartHandler
 * @Author ruanyuanyuan
 * @Date 2020/9/28-16:52
 * @Version 1.0
 * @Description TODO
 **/
public class NettyServerWeChartHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count=0;

    /**
     * GlobalEventExecutor.INSTANCE : 创建一个单例的全局事件执行器
     **/
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     *  当建立连接成功，则就会马上出发次方法
     **/
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {


        /**
         *  TODO 只要有客户端建立连接成功就会推送消息给其他客户端
         *  此方法可以直接推送消息给其他客户端
         **/
        channelGroup.writeAndFlush("客户端【"+ctx.channel().remoteAddress()+ "】"+getDateTime()+" 加入聊天 \r\n");
        //TODO 只要有客户端建立连接成功，就将其加入到全局Channel集合中
        channelGroup.add(ctx.channel());
    }

    /**
     * 此方法表示客户端建立连接成功后，处于活跃状态
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端【"+ctx.channel().remoteAddress()+"】"+getDateTime()+" 上线 \r\n");
        ctx.channel().writeAndFlush("ssssss");
    }

    /**
     * 此方法表示客户端建立连接成功后，处于离线状态
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端【"+ctx.channel().remoteAddress()+"】"+getDateTime()+" 离线 \r\n");
    }

    /**
     * 断开连接，将断开连接推送给其他所有在线客户端
     **/
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush("客户端【"+ctx.channel().remoteAddress()+"】"+getDateTime()+" 离开 \r\n");
    }

    /**
     * 读事件触发方法
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLength();
        String content = new String(msg.getContent(), CharsetUtil.UTF_8);
        System.out.println("服务端接收到数据长度为：【"+length+"】，内容为：【"+content+"】,类型："+msg.getType()+" 服务包数量：【"+ (++count)+"】");

        //服务端接受到数据后，会送数据给客户端
        Channel channel = ctx.channel();

        MessageProtocol messageProtocol = new MessageProtocol();
        channelGroup.forEach(obj ->{
            String response = "";
            if(channel != obj){//排除当前channel
                response ="客户端【"+ channel.remoteAddress()+"】"+getDateTime()+" 发送的消息："+ content +" \r\n";
                messageProtocol.setLength(response.getBytes(CharsetUtil.UTF_8).length);
                messageProtocol.setContent(response.getBytes(CharsetUtil.UTF_8));
                obj.writeAndFlush(messageProtocol);
            }else{
                response = "自己发送的消息："+content +"\r\n";
                messageProtocol.setLength(response.getBytes(CharsetUtil.UTF_8).length);
                messageProtocol.setContent(response.getBytes(CharsetUtil.UTF_8));
                obj.writeAndFlush(messageProtocol);
            }
        });
    }

    /**
     * 异常关闭channel
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public String getDateTime(){
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss"));
        return dateTime;
    }
}
