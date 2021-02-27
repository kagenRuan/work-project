package com.ruan.yuanyuan.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.RegionLocator;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.util.MapReduceExtendedCell;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class FileDataJob {

    public static void main(String[] args) {
        try {
            Configuration configuration = HBaseConfiguration.create();
            Connection connection = ConnectionFactory.createConnection(configuration);
            Table tableName = connection.getTable(TableName.valueOf("ORDER:MY_DATA"));
            //获取Job对象
            Job job = Job.getInstance(configuration);
            //指定JAR包运行的类
            job.setJarByClass(FileDataJob.class);
            //指定输入的类型
            job.setInputFormatClass(TextInputFormat.class);

            //开始组装MapperReduce
            //设置Mapper的类
            job.setMapperClass(FileDataMap.class);
            //指定mapper阶段输出数据的类型
            job.setMapOutputKeyClass(ImmutableBytesWritable.class);
            job.setMapOutputValueClass(MapReduceExtendedCell.class);

            //设置文件的输入路径
            FileInputFormat.setInputPaths(job,new Path("hdfs://192.168.0.106:9000/order/input"));
            //设置文件的输出路径
            FileOutputFormat.setOutputPath(job,new Path("hdfs://192.168.0.106:9000/order/output"));

            //获取表在那个region的信息
            RegionLocator regionLocator = connection.getRegionLocator(TableName.valueOf("ORDER:MY_DATA"));
            HFileOutputFormat2.configureIncrementalLoad(job,tableName,regionLocator);

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
