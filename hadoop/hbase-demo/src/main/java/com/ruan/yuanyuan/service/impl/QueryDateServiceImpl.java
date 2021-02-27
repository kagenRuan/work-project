package com.ruan.yuanyuan.service.impl;

import com.ruan.yuanyuan.entity.Msg;
import com.ruan.yuanyuan.service.QueryDataService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class QueryDateServiceImpl implements QueryDataService {

    static Connection conn = null;

    static {
        try {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findData(String name,String date,String roeKey) throws Exception {
        Scan scan = new Scan();
        SingleColumnValueFilter startDate = new SingleColumnValueFilter(Bytes.toBytes("C1"),Bytes.toBytes("date"), CompareOperator.GREATER_OR_EQUAL,new BinaryComparator(Bytes.toBytes("2020-01-01")));
        SingleColumnValueFilter endDate = new SingleColumnValueFilter(Bytes.toBytes("C1"),Bytes.toBytes("date"), CompareOperator.LESS_OR_EQUAL,new BinaryComparator(Bytes.toBytes("2020-10-01")));
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,startDate,endDate);
        scan.setFilter(filterList);
        Table table = conn.getTable(TableName.valueOf("MOMO_CHAT:MSG"));
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result result = iterator.next();
            List<Cell> cells = result.listCells();
            if(null == cells){
                return;
            }
            String row = Bytes.toString(result.getRow());
            cells.forEach(obj ->{
                String family = Bytes.toString(obj.getFamilyArray(), obj.getFamilyOffset(), obj.getFamilyLength());
                String columnName = Bytes.toString(obj.getQualifierArray(), obj.getQualifierOffset(), obj.getQualifierLength());
                String values  = Bytes.toString(obj.getValueArray(), obj.getValueOffset(), obj.getValueLength());
                System.out.println("rowkey:"+row+",family:"+family+",columnName:"+columnName+",values="+values);
            });

        }
        scanner.close();
        table.close();
    }

    @Override
    public void close() throws Exception {

    }
}
