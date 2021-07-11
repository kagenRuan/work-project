package com.ruan.yuanyuan.nio.nio_liaotian_example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @ClassName Client
 * @Author ruanyuanyuan
 * @Date 2020/9/27-09:43
 * @Version 1.0
 * @Description TODO 聊天室例子，使用NIO
 **/
public class Server {

    public Selector selector;
    public ServerSocketChannel listenServerSocketChannel;
    public static final int PORT= 6666;

    public Server() {
        try {
            listenServerSocketChannel = ServerSocketChannel.open();
            listenServerSocketChannel.socket().bind(new InetSocketAddress(PORT));
            listenServerSocketChannel.configureBlocking(false);
            selector = Selector.open();
            listenServerSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO  监听并初始化Server
    public void listen(){
        while (true){
            try {
                int count = selector.select();
                if(count > 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if(key.isAcceptable()){
                            SocketChannel socket = listenServerSocketChannel.accept();
                            socket.configureBlocking(false);
                            socket.register(selector,SelectionKey.OP_READ);
                            socket.configureBlocking(false);
                            System.out.println(socket.getRemoteAddress()+"上线");
                        }
                        if(key.isReadable()){
                            readMessage(key);
                        }
                        iterator.remove();
                    }

                }else {
                    System.out.println("等待中");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO 读取所有通道发送的消息然后将其转发给其他客户端，同时排除自己
    public void readMessage(SelectionKey selectionKey){
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(byteBuffer);
            if(count > 0){
                String message = new String(byteBuffer.array());
                System.out.println("客户端："+message);
                //将消息转发给其他客户端(排查自己)
                sendMessageToOtherClients(message,socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了");
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //TODO 转发消息给其他客户端(排查自己)
    public void sendMessageToOtherClients(String message,SocketChannel self) throws IOException {
        for (SelectionKey key : selector.keys()){
           Channel channel = key.channel();
           if(channel instanceof SocketChannel && channel != self){
               ByteBuffer wrap = ByteBuffer.wrap(message.getBytes());
               ((SocketChannel) channel).write(wrap);
           }
        }
    }

    //TODO 服务端发送消息给其他客户端
    public void sendMessageAllClients(String message){
        try{
            for (SelectionKey key : selector.keys()){
                Channel channel = key.channel();
                if(channel instanceof SocketChannel){
                    ByteBuffer wrap = ByteBuffer.wrap(message.getBytes());
                    ((SocketChannel) channel).write(wrap);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        //创建一个线程每隔3秒读取从服务端读取消息
        new Thread(() ->{
            while (true){
                server.listen();
            }
        }).start();

        //发送数据给其他客户端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            server.sendMessageAllClients(s);
        }
    }
}
