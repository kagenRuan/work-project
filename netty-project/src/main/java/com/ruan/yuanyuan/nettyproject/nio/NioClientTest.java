package com.ruan.yuanyuan.nettyproject.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @ClassName: NioClientTest
 * @author: ruanyuanyuan
 * @date: 2019/12/28 15:41
 * @version: 1.0
 * @description:NIO client 测试用例
 **/
public class NioClientTest {

    private Selector selector = null;

    public static void main(String[] args) {
        NioClientTest nioClientTest = new NioClientTest();
        try {
            nioClientTest.initClient("127.0.0.1",8888);
            nioClientTest.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initClient(String host, int port) {
        try {
            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(false);
            this.selector = Selector.open();
            sc.register(selector, SelectionKey.OP_CONNECT);
            sc.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() throws IOException {
        while (true) {
            int events = selector.select();
            if (events > 0) {
                Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                while (selectionKeys.hasNext()) {
                    SelectionKey selectionKey = selectionKeys.next();
                    selectionKeys.remove();
                    handler(selectionKey);
                }
            }
        }
    }

    public void handler(SelectionKey selectionKey ) throws IOException {
        //连接事件
        if (selectionKey.isConnectable()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            if (socketChannel.isConnectionPending()) {
                socketChannel.finishConnect();
            }
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            socketChannel.write(ByteBuffer.wrap(("Hello this is " + Thread.currentThread().getName()).getBytes()));
        } else if (selectionKey.isReadable()) {
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            sc.read(buffer);
            buffer.flip();
            String result = new String(buffer.array(),"UTF-8").trim();
            System.out.println("收到服务端的数据："+result);
        }
    }

}
