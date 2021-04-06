package com.ruan.yuanyuan.netty.nio_liaotian_example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @ClassName Server
 * @Author ruanyuanyuan
 * @Date 2020/9/27-09:42
 * @Version 1.0
 * @Description TODO 聊天室例子，使用NIO
 **/
public class Client {

    private static  final String HOST= "localhost";
    private static  final int PORT= 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public Client() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username+"is ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向服务端发送消息
    public void sendMessageToServer(String message){
        message = username + "说："+message;
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从服务端读取消息
    public void readMessageFromServer(){
        try {
            int op = selector.select();//看当前是否有事件发送，如果返回值大于0则表示有事件发送
            if(op > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();//获取所有的SelectionKey
                while (iterator.hasNext()){//循环所有的SelectionKey
                    SelectionKey key = iterator.next();
                    if(key.isReadable()){//如果SelectionKey是【读事件】
                       SocketChannel socketChannel = (SocketChannel) key.channel();
//                       ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(byteBuffer);
                        String message = new String(byteBuffer.array());
                        System.out.println(message);
                    }
                }
                iterator.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Client client = new Client();

        //创建一个线程每隔3秒读取从服务端读取消息
        new Thread(() ->{
            while (true){
                client.readMessageFromServer();
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        //发送数据个服务端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            client.sendMessageToServer(s);
        }
    }
}
