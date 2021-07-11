package com.ruan.yuanyuan.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Servcer
 * @Author ruanyuanyuan
 * @Date 2020/9/26-13:49
 * @Version 1.0
 * @Description TODO
 **/
public class Server {

    public static void main(String[] args) throws IOException {
        //创建连接池，无限大小的连接池
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("获取到客户端连接请求");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }
    }
    public static void handler(Socket socket){
        System.out.println("当前处理请求的线程"+Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        try {
            //获取到输入流,读取输入流
            InputStream stream = socket.getInputStream();
            while (true){
                int red = stream.read(bytes);
                if(red != -1){
                    System.out.println(new String(bytes,0,red));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
