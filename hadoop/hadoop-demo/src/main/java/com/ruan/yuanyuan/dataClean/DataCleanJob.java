package com.ruan.yuanyuan.dataClean;

import com.ruan.yuanyuan.WordCountJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @ClassName DataCleanJob
 * @Author ruanyuanyuan
 * @Date 2021/1/22-16:05
 * @Version 1.0
 * @Description TODO 数据清洗
 * 需求描述：从原始数据文件(JSON)中过滤我们需要的字段以及字段值，
 **/
public class DataCleanJob {

    public static void main(String[] args) {
        try {
            Configuration configuration = new Configuration();
            Job job = Job.getInstance(configuration);
            //指定输入路径(可以是文件也可以是路径)
            FileInputFormat.setInputPaths(job,new Path(args[0]));
            //指定输出路径(输出路径必须是不存在的，否则会报错)
            FileOutputFormat.setOutputPath(job,new Path(args[1]));

            //指定当前类
            job.setJarByClass(DataCleanJob.class);

            //开始组装MapperReduce
            //设置Mapper的类
            job.setMapperClass(DataCleanMap.class);
            //指定mapper阶段输出数据的类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            //禁用Reduce，这里只是过滤数据，而不对数据进行计算
            job.setNumReduceTasks(0);

            //提交Job
            job.waitForCompletion(true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
