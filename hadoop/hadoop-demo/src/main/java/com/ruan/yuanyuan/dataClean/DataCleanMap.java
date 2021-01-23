package com.ruan.yuanyuan.dataClean;

import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ClassName DataCleanMap
 * @Author ruanyuanyuan
 * @Date 2021/1/22-16:16
 * @Version 1.0
 * @Description TODO 自定义实现Mapper，主要作用就是对数据进行清洗逻辑
 **/
public class DataCleanMap extends Mapper<LongWritable, Text,Text,Text> {
    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/22 16:18
     * @Description: 主要就是过滤不要的字段
     * @param k1:
     * @param v1:
     * @param context:
     * @return: void
     **/
    @Override
    protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
        String line = v1.toString();
        JSONObject object = JSONObject.parseObject(line);
        //从JSON数据中获取需要的字段
        String uid = object.getString("uid");//主播的ID
        int gold = object.getIntValue("gold");
        int watchnumpv = object.getIntValue("watchnumpv");
        int follower = object.getIntValue("follower");
        int length = object.getIntValue("length");
        if(gold >=0 && watchnumpv>=0 && follower>=0 && length >=0){
            Text k2 = new Text();
            k2.set(uid);
            Text v2 = new Text();
            v2.set(gold+"\t"+watchnumpv+"\t"+follower+"\t"+length+"\t");
            context.write(k2,v2);
        }
    }
}
