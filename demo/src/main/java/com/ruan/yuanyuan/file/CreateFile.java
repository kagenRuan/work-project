package com.ruan.yuanyuan.file;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CreateFile
 * @Author ruanyuanyuan
 * @Date 2021/1/22-16:40
 * @Version 1.0
 * @Description TODO
 **/
public class CreateFile {

    private static void createFile(){
        try {
            File file = new File("/Users/ruanyuanyuan/my.txt");
            boolean b = file.createNewFile();
            if(b) {
                Writer out = new FileWriter(file);
                for (int i = 0; i <8 ; i++) {
                    Map<String,Object> data = new HashMap<>();
                    data.put("uid",i+"");
                    data.put("gold",i);
                    data.put("watchnumpv",i);
                    data.put("follower",i);
                    data.put("length",i);
                    String jsonString = JSONObject.toJSONString(data);
                    out.append(jsonString+"\r\n");//用于换行
                }
                out.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createFile();
//        try {
//            parseJSONObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private static void parseJSONObject() throws IOException {
        File file = new File("/Users/ruanyuanyuan/my.txt");
        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader bf = new BufferedReader(inputReader);
        String str;
        while ((str = bf.readLine()) != null) {
            String[] fields = str.split("");
            for (String field : fields) {
                JSONObject jsonObject = JSONObject.parseObject(field);
                System.out.println(jsonObject);
            }
        }
    }
}
