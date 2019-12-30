package com.ruan.yuanyuan.nettyproject.netty;

import com.ruan.yuanyuan.nettyproject.nio.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

/**
 * @ClassName: NettyTest
 * @author: ruanyuanyuan
 * @date: 2019/12/29 13:30
 * @version: 1.0
 * @description: Netty Server测试实例
 * TODO server启动过程 doBind()->initAndRegister()方法里面创建，初始化channel并绑定到selector上
 * 1、创建服务端channel并初始化channel
 *    1）、通过反射的方式创建channel
 *    2）、初始化channel调用init()方法
 *        1）、保存服务端设置channelAttrs通道属性
 *        2）、保存服务端设置childAttrs连接属性
 *        3）、保存服务端配置config handler
 *        4）、默认添加serverBootstrapAccept连接器将之前配置的属性进行关联到一个线程上
 * 2、将初始化好的channel注册到selector上面
 *    1）、调用AbstractChannel.register()进行注册
 *         1）、绑定线程池 TODO AbstractChannel.this.eventLoop = eventLoop;
 *         2）、register0()实际注册
 *              1）、doRegister()调用JDK底层注册
 *              2）、invokeHandlerAddedIfNeeded（）
 *              3）、fireChannelRegistered（）方法注册通道传播方法
 * 3、端口绑定 TODO bind->doBind->doBind0()方法进行绑定
 *
 *
 * 问题：1、服务端的socket初始化在什么地方？
 *      2、在哪里accept连接？
 *
 **/
public class NettyServerTest {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY,true) //设置连接信息
                    .childAttr(AttributeKey.newInstance("childAttr"),"childAttrValue") //为请求连接增加属性
                    .handler(new ServerHandler()) //添加业务处理Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //配置Handler对每个请求处理流的逻辑处理
//                            ch.pipeline().addLast()
                        }
                    });
            ChannelFuture future = bootstrap.bind(8888).sync();//绑定端口并启动Server端
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
