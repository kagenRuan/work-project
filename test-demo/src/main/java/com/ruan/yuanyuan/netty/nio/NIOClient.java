package com.ruan.yuanyuan.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NIOClient
 * @Author ruanyuanyuan
 * @Date 2020/9/26-23:37
 * @Version 1.0
 * @Description TODO
 **/
public class NIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        InetSocketAddress socketAddress = new InetSocketAddress("localhost",8888);
        if(!socketChannel.connect(socketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("客户端连接需要时间，可以做其他工作不会进行阻塞");
            }
        }

        //如果连接成功就发送字符串
        String message ="helloNio";
        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
        //将Buffer数据写入到Channel
        socketChannel.write(byteBuffer);
        socketChannel.close();
    }
}
