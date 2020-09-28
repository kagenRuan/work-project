package com.ruan.yuanyuan.netty.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NIOZeroCopyServer
 * @Author ruanyuanyuan
 * @Date 2020/9/27-14:04
 * @Version 1.0
 * @Description TODO
 **/
public class NIOZeroCopyServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(7000));
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            while (-1 != readCount){
                readCount = socketChannel.read(byteBuffer);
            }
        }
    }
}
