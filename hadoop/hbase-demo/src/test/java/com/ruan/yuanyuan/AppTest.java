package com.ruan.yuanyuan;

import static org.junit.Assert.assertTrue;

import com.ruan.yuanyuan.service.PhoenixQueryDataService;
import com.ruan.yuanyuan.service.QueryDataService;
import com.ruan.yuanyuan.service.impl.PhoenixQueryDataServiceImpl;
import com.ruan.yuanyuan.service.impl.QueryDateServiceImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.protobuf.generated.ComparatorProtos;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    static Connection conn = null;
    static Admin admin = null;

    @Before
    public void testBefore(){
        System.out.println("初始化HBase连接...");
        Configuration conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", "yuanyuan.local");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断表是否存在
    @Test
    public void testTableExists(){
        try{
            admin = conn.getAdmin();
            TableName tableName1 = TableName.valueOf("stu");
            if(admin.tableExists(tableName1)){
                System.out.println("table exist...");
                return;
            }
            System.out.println("table do not exist...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //创建表
    @Test
    public void testCreateTable(){
        try {
            admin = conn.getAdmin();
            TableName tname = TableName.valueOf("stu");
            //判断表是否存在
            if (admin.tableExists(tname) ){
                System.out.println("stu表已经存在,不能重复创建.");
                return;
            }
            //创建表
            TableDescriptorBuilder tdesc = TableDescriptorBuilder.newBuilder(tname);
            //指定列族
            ColumnFamilyDescriptor cfd = ColumnFamilyDescriptorBuilder.of("info");
            tdesc.setColumnFamily(cfd);
            TableDescriptor desc=tdesc.build();
            admin.createTable(desc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //添加数据
    @Test
    public void testPutData(){
        try{
            Table table = conn.getTable(TableName.valueOf("stu"));
            Put put = new Put(Bytes.toBytes("20210120_1633"));
            put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"),Bytes.toBytes("ryy"));
            put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("age"),Bytes.toBytes("30"));
            table.put(put);
            table.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //根据rowkey查询数据
    @Test
    public void testGet(){

        try{
            Table table = conn.getTable(TableName.valueOf("stu"));
            Get get = new Get(Bytes.toBytes("20210120_1633"));
            Result result = table.get(get);
            List<Cell> cells = result.listCells();
            if(null == cells){
                return;
            }
            cells.forEach(obj ->{
                String family = Bytes.toString(obj.getFamilyArray(), obj.getFamilyOffset(), obj.getFamilyLength());
                String columnName = Bytes.toString(obj.getQualifierArray(), obj.getQualifierOffset(), obj.getQualifierLength());
                String values  = Bytes.toString(obj.getValueArray(), obj.getValueOffset(), obj.getValueLength());
                System.out.println("family:"+family+",columnName:"+columnName+",values="+values);
            });
            table.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //删除数据
    @Test
    public void testDelete(){
        try{
            Table table = conn.getTable(TableName.valueOf("stu"));
            Delete delete = new Delete(Bytes.toBytes("20210120_1633"));
            table.delete(delete);
            table.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //使用scan和Filter联合查询
    @Test
    public void testScanAndFilterQuery(){
        try{
            Table table = conn.getTable(TableName.valueOf("stu"));
            Scan scan = new Scan();
            //开始时间(大于等于)
            SingleColumnValueFilter startDate = new SingleColumnValueFilter(
                    Bytes.toBytes("info"),
                    Bytes.toBytes("name"),
                    CompareOperator.GREATER_OR_EQUAL, //大于等于
                    new BinaryComparator(Bytes.toBytes("2021-01-01"))
                    );
            //结束时间(小于等于)
            SingleColumnValueFilter endDate = new SingleColumnValueFilter(
                    Bytes.toBytes("info"),
                    Bytes.toBytes("name"),
                    CompareOperator.LESS_OR_EQUAL, //小于等于
                    new BinaryComparator(Bytes.toBytes("2021-01-01"))
            );
            //【MUST_PASS_ALL】这两个时间都必须满足。【MUST_PASS_ONE】只需要满足其中一个
            FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,startDate,endDate);
            scan.setFilter(filterList);
            ResultScanner scanner = table.getScanner(scan);
            Iterator<Result> iterator = scanner.iterator();
            while (iterator.hasNext()){
                Result result = iterator.next();
                List<Cell> cells = result.listCells();
                if(null == cells){
                    return;
                }
                cells.forEach(obj ->{
                    String family = Bytes.toString(obj.getFamilyArray(), obj.getFamilyOffset(), obj.getFamilyLength());
                    String columnName = Bytes.toString(obj.getQualifierArray(), obj.getQualifierOffset(), obj.getQualifierLength());
                    String values  = Bytes.toString(obj.getValueArray(), obj.getValueOffset(), obj.getValueLength());
                    System.out.println("family:"+family+",columnName:"+columnName+",values="+values);
                });

            }
            scanner.close();
            table.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //测试生成10W条数据到Hbase中
    @Test
    public void testBigData() throws IOException {
        Table table = conn.getTable(TableName.valueOf("MOMO_CHAT:MSG"));
        String i="12";
        String date ="2020-12-01";
        StringBuilder builder = new StringBuilder();
        builder.append(i);
        builder.append("_");
        builder.append("ruanyuanyuan"+i);
        builder.append("_");
        builder.append(date);
        String md5AsHex = MD5Hash.getMD5AsHex(builder.toString().getBytes());
        String rowKey = md5AsHex.substring(0,8)+"_"+builder.toString();
        Put put = new Put(rowKey.getBytes());
        put.addColumn(Bytes.toBytes("C1"),Bytes.toBytes("name"),Bytes.toBytes("rry"+i));
        put.addColumn(Bytes.toBytes("C1"),Bytes.toBytes("age"),Bytes.toBytes(i));
        put.addColumn(Bytes.toBytes("C1"),Bytes.toBytes("date"),Bytes.toBytes(date));
        table.put(put);
        table.close();
    }

    @Test
    public void testPhoenix() throws SQLException, ClassNotFoundException {
        PhoenixQueryDataService phoenixQueryDataService = new PhoenixQueryDataServiceImpl();
        phoenixQueryDataService.query();
        phoenixQueryDataService.close();
    }


    @Test
    public void testQuery() throws Exception {
         QueryDataService queryDataService = new QueryDateServiceImpl();
         queryDataService.findData("","","");
        queryDataService.close();
    }

    @After
    public void testAfter(){
        try {
            if(admin != null){
                admin.close();
            }
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
