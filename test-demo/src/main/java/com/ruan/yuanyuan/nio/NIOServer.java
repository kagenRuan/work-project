package com.ruan.yuanyuan.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName Server
 * @Author ruanyuanyuan
 * @Date 2020/9/26-14:33
 * @Version 1.0
 * @Description TODO
 **/
public class NIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(InetAddress.getLocalHost(),8888));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //创建一个Selector
        Selector selector = Selector.open();
        //把ServerSocketChannel注册到Selector上，关心OP_ACCEPT请求连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true){
            //表示当前没有事件发生
            if(0 == selector.select(1000)){
                System.out.println("当前没有事件发生");
                continue;
            }
            //获取到相关的事件SelectionKey,并循环这些事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey selectionKey = keyIterator.next();
                if(selectionKey.isAcceptable()){//连接事件
                    /**
                     * @Author: ruanyuanyuan
                     * @Date: 2020/9/26 23:26
                     * @Description: 获取到一个SocketChannel
                     * TODO 而在BIO中这里是会阻塞的因为在BIO中它不知道连接事件，而在NIO是不会阻塞的原因是因为它知道当前的请求是不是连接请求
                     *      进行三次握手
                     **/
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //TODO 将SocketChannel注册到Selector上绑定一个【OP_READ】读事件，同时为这个【SocketChannel】分配一个ByteBuffer
                    socketChannel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE,ByteBuffer.allocate(1024));

                }
                if(selectionKey.isReadable()){//读事件
                    //TODO 如果是读事件，则需要获取到对应的SocketChannel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //TODO 获取到SocketChannel绑定的ByteBuffer
                    ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
                    channel.read(byteBuffer);
                    System.out.println("获取到客户端发送的消息："+new String(byteBuffer.array()));
                    channel.register(selector,SelectionKey.OP_WRITE);
                }
                if(selectionKey.isWritable()){//写事件
                    //TODO 如果是读事件，则需要获取到对应的SocketChannel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //TODO 获取到SocketChannel绑定的ByteBuffer
                    ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
                    byteBuffer.put("服务端接受到数据".getBytes());
                    channel.write(byteBuffer);
                    channel.register(selector,SelectionKey.OP_READ);
                }

                //手动删除事件，否则会造成事件的重复消费
                keyIterator.remove();
            }
        }
    }
}
