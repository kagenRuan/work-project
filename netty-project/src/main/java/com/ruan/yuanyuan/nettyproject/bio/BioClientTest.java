package com.ruan.yuanyuan.nettyproject.bio;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: BioClientTest
 * @author: ruanyuanyuan
 * @date: 2019/12/28 15:25
 * @version: 1.0
 * @description:BIO 客户端测试用例
 **/
public class BioClientTest {
    private static final BufferedReader KEYBOARD_INPUT = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1",8888);
        Scanner scan = new Scanner(client.getInputStream());
        scan.useDelimiter("\n");
        PrintStream out = new PrintStream(client.getOutputStream());
        boolean flag = true;
        while (flag){
            String inputData = BioClientTest.getString("请输入要发送的内容：").trim();
            System.out.println(inputData);
            out.println(inputData);
            if (scan.hasNext()){
                String str = scan.next();
                System.out.println(str);
            }else {
                break;
            }
        }
        client.close();
    }

    public static String getString(String prompt) {

        String str = null;
        System.out.println(prompt);
        while (true) {
            try {
                str = KEYBOARD_INPUT.readLine();    // 读取一行数据
                if (str == null || "".equals(str)) {
                    System.out.println("数据输入错误，不允许为空！");
                } else {
                   break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
