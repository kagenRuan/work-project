package com.ruan.yuanyuan.netty.bio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @ClassName Client
 * @Author ruanyuanyuan
 * @Date 2020/9/26-13:49
 * @Version 1.0
 * @Description TODO
 **/
public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        BufferedWriter writer = null;
        try {
            socket = new Socket("localhost",8080);
            //向服务器发送消息,获取输出字符流
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("hello bio");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
