package com.ruan.yuanyuan;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName WordCountJob
 * @Author ruanyuanyuan
 * @Date 2021/1/22-13:58
 * @Version 1.0
 * @Description TODO 主要是计算文件中的单词数量
 * 需求：读取HDFS上的文件统计文件中的单词数量
 * 举例：文件内容如下
 * hello you
 * hello me
 * 最后的结果为：
 * hello 2
 * you 1
 * me 1
 **/
public class WordCountJob {

    static AtomicInteger atomicInteger = new AtomicInteger(0);


    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/22 14:01
     * @Description: 使用的MapperReduce框架，继承Mapper类主要是负责对数据进行分片
     * Mapper类需要传入四个泛型前面两个表示输入数据的类型，后面两个表示输出数据的类型
     **/
    public static class MyMapper extends Mapper<LongWritable,Text, Text,LongWritable> {

        /**
         * @Author: ruanyuanyuan
         * @Date: 2021/1/22 14:08
         * @param k1: 表示文件中每一行的偏移量
         * @param v1: 表示文件中每一行的内容
         **/
        @Override
        protected void map(LongWritable k1,Text v1,  Context context) throws IOException, InterruptedException {
            String value = v1.toString();
            String[] fields = value.split(" ");
            for (String field : fields) {
                Text k2 = new Text(field);
                LongWritable v2 = new LongWritable(1l);
                context.write(k2,v2);
            }


        }
    }


    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/22 14:01
     * @Description: 使用的MapperReduce框架，继承Reducer类主要是负责对数据进行统计并写入到文件
     * Reducer类需要传入四个泛型前面两个表示输入数据的类型，后面两个表示输出数据的类型
     **/
    public static class MyReducer extends Reducer<Text, LongWritable,Text,LongWritable>{
        /**
         * @Author: ruanyuanyuan
         * @Date: 2021/1/22 14:07
         * @Description: 主要是对v2集合中的数据进行求和，最终生成数据为k3,v3然后把数据写入文件中
         * @param k2: 表示从磁盘上写入的key
         * @param v2: 表示从磁盘上写入的valule
         **/
        @Override
        protected void reduce(Text k2, Iterable<LongWritable> v2, Context context) throws IOException, InterruptedException {
            System.out.println(atomicInteger.addAndGet(1));
            //统计单词的数量
            long sum = 0;
            for (LongWritable longWritable : v2) {
                sum +=longWritable.get();
            }
            //组装可k3,v3
            Text k3 = new Text(k2);
            LongWritable v3 = new LongWritable(sum);
            context.write(k3,v3);
        }
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/22 14:21
     * @Description: 装JOB执行任务
     * @param args:
     **/
    public static void main(String[] args) {

        try {
            Configuration configuration = new Configuration();
            Job job = Job.getInstance(configuration);
            //指定输入路径(可以是文件也可以是路径)
            FileInputFormat.setInputPaths(job,new Path(args[0]));
            //指定输出路径(输出路径必须是不存在的，否则会报错)
            FileOutputFormat.setOutputPath(job,new Path(args[1]));

            //指定当前类
            job.setJarByClass(WordCountJob.class);

            //开始组装MapperReduce
            //设置Mapper的类
            job.setMapperClass(MyMapper.class);
            //指定mapper阶段输出数据的类型
            job.setMapOutputKeyClass(Text  .class);
            job.setMapOutputValueClass(LongWritable.class);
            //设置Reduce的类
            job.setReducerClass(MyReducer.class);
            //设置Reduce阶段输出数据的类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(LongWritable.class);


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

