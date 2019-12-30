package com.ruan.yuanyuan.nettyproject.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: BioTest
 * @author: ruanyuanyuan
 * @date: 2019/12/28 15:15
 * @version: 1.0
 * @description: BIO 测试用例
 * TODO BIO 阻塞 一个服务端只能处理一个客户端，只有处理完一个客户端，这个客户端并退出后，才能处理下一个客户端
 *               accept()方法如果没有客户端请求这里会被阻塞
 *               read()读取数据时如果没有数据则会被阻塞
 **/
public class BioServerTest {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.err.println("服务端启动。。。。。");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("有新的客户端连接");
            InputStream stream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (true){
                int data = stream.read(bytes);
                if(data != -1){
                    String result = new String(bytes,0,data,"UTF-8");
                    System.out.println("接收到数据为："+result);
                }else{
                    break;
                }
            }
        }
    }
}
