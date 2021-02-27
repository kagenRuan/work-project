package com.ruan.yuanyuan.hadoop;

import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MapReduceExtendedCell;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * bulkload 编写：特点就是直接读取文件中的内容将数据直接写入HDFS中的HFile文件中，
 *               避免了直接和HBase直接连接使用HBase中的put进行操作添加数据
 * 主要是读取文件中的数据，保存到HBase文件中
 * LongWritable -》行号
 * Text -》输入文本
 * ImmutableBytesWritable -》输出行号对应Hbase中的RowKey
 * MapReduceExtendedCell -》对应HBase中的一行数据
 */
public class FileDataMap extends Mapper<LongWritable, Text, ImmutableBytesWritable, MapReduceExtendedCell> {



    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        JSONObject object = JSONObject.parseObject(value.toString());
        StringBuilder rowkey = new StringBuilder();
        rowkey.append(new Date().getTime()).append("_").append(object.getString("name"));
        String family = "C1";
        //构建HBase中的rowkey
        ImmutableBytesWritable immutableBytesWritable = new ImmutableBytesWritable(Bytes.toBytes(rowkey.toString()));
        KeyValue nameValue = new KeyValue(Bytes.toBytes(rowkey.toString()), Bytes.toBytes(family), Bytes.toBytes("name"), Bytes.toBytes(object.getString("name")));
        KeyValue ageValue = new KeyValue(Bytes.toBytes(rowkey.toString()), Bytes.toBytes(family), Bytes.toBytes("age"), Bytes.toBytes(object.getString("age")));
        KeyValue dateValue = new KeyValue(Bytes.toBytes(rowkey.toString()), Bytes.toBytes(family), Bytes.toBytes("date"), Bytes.toBytes(object.getString("date")));

        //输出
        context.write(immutableBytesWritable,new MapReduceExtendedCell(nameValue));
        context.write(immutableBytesWritable,new MapReduceExtendedCell(ageValue));
        context.write(immutableBytesWritable,new MapReduceExtendedCell(dateValue));
    }
}
