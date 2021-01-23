package com.ruan.yuanyuan;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HBaseApiTest
 * @Author ruanyuanyuan
 * @Date 2021/1/18-14:07
 * @Version 1.0
 * @Description TODO  主要用于测试HBase的API
 **/
public class HBaseApiTest {

    static Connection conn = null;

    static {
        System.out.println("初始化HBase连接...");
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "132.232.96.106");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //判断表是否存在
    public static boolean isTableExist(String tableName) throws IOException {
        try{
            Admin admin = conn.getAdmin();
            TableName tableName1 = TableName.valueOf(tableName);
            if(admin.tableExists(tableName1)){
                System.out.println("table exist...");
                return true;
            }
            System.out.println("table do not exist...");
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            conn.close();
        }
        return false;
    }

    //创建表
    public static void  createTable(String tableName, String... familys){
        try {
            Admin admin = conn.getAdmin();
            TableName tname = TableName.valueOf(tableName);
            if (admin.tableExists(tname) ){
                System.out.println(tableName + "表已经存在,不能重复创建.");
            } else {
                TableDescriptorBuilder tdesc = TableDescriptorBuilder.newBuilder(tname);
                for(String family: familys){
                    ColumnFamilyDescriptor cfd = ColumnFamilyDescriptorBuilder.of(family);
                    tdesc.setColumnFamily(cfd);
                }
                TableDescriptor desc=tdesc.build();
                admin.createTable(desc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //添加数据
    public static void insert(String tableName,
                              String row,
                              String columnFamily,
                              List<String> columns,
                              List<String> values){

        try{
            Table table = conn.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(row));
            for(int i = 0; i < columns.size(); i++){
                put.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(columns.get(i)),Bytes.toBytes(values.get(i)));
                table.put(put);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public static void main(String[] args) throws IOException {
        HBaseApiTest.createTable("stu","info");
        //添加数据
//        ArrayList<String> cloums = new ArrayList<>();
//        cloums.add("name");
//        cloums.add("age");
//        ArrayList<String> values = new ArrayList<>();
//        values.add("ryy");
//        values.add("30");
//        HBaseApiTest.insert("stu","20210120_1632","info",cloums,values);
    }

}
