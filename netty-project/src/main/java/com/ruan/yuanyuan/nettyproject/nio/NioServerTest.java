package com.ruan.yuanyuan.nettyproject.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @ClassName: NioServceTest
 * @author: ruanyuanyuan
 * @date: 2019/12/28 15:41
 * @version: 1.0
 * @description: NIO server端 测试用例
 * TODO NIO 非阻塞 单线程Reactor模型 基于事件处理请求
 **/
public class NioServerTest {
    private Selector selector = null;
    public static void main(String[] args) {
        NioServerTest nioServerTest = new NioServerTest();
        try {
            nioServerTest.initServer(8888);
            nioServerTest.listenSelector();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initServer(int port) throws IOException {
        //获取一个channel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//设置通道为非阻塞
        serverSocketChannel.socket().bind(new InetSocketAddress(port));//绑定端口
        selector = Selector.open();//获得一个通道管理器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("启动服务端，并注册一个连接事件");
    }


    public void listenSelector() throws IOException {
        while (true){
            this.selector.select();
            Iterator iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                //轮询获取到事件
                SelectionKey selectionKey = (SelectionKey)iterator.next();
                iterator.remove();
                //根据获取到的时间处理请求
                handler(selectionKey);
            }
        }
    }

    //处理请求
    public void  handler(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isAcceptable()){//连接请求事件
            System.out.println("连接事件请求，有新的客户端连接。。。。");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();//获取到客户端的连接通道并与之连接
            socketChannel.configureBlocking(false);//设置非阻塞
            socketChannel.register(this.selector,SelectionKey.OP_READ);
        }else if(selectionKey.isReadable()){//读事件
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int readData  = socketChannel.read(byteBuffer);
            if(readData > 0){
                String result = new String(byteBuffer.array(),"UTF-8").trim();
                System.out.println("服务端收到消息："+result);
                //往客户端写数据
                ByteBuffer callback = ByteBuffer.wrap("服务端返回数据".getBytes("UTF-8"));
                socketChannel.write(callback);
            }
        }else{
            System.out.println("客户端关闭");
            selectionKey.cancel();
        }
    }
}
