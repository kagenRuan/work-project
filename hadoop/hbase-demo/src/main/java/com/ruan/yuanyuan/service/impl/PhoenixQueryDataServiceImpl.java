package com.ruan.yuanyuan.service.impl;

import com.ruan.yuanyuan.service.PhoenixQueryDataService;
import org.apache.phoenix.jdbc.PhoenixDriver;

import java.sql.*;

public class PhoenixQueryDataServiceImpl implements PhoenixQueryDataService {
    Connection connection = null;
    public PhoenixQueryDataServiceImpl() throws ClassNotFoundException, SQLException {
        Class.forName(PhoenixDriver.class.getName());
        connection = DriverManager.getConnection("jdbc:phoenix:192.168.0.104:2181");//连接到zookeeper
    }

    @Override
    public void query() throws SQLException {
        String sql ="select * from \"MOMO_CHAT\".\"MSG\" where \"name\" = ? and \"date\" = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"rry1");
        preparedStatement.setString(2,"2020-01-01");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String age = resultSet.getString("age");
            String date = resultSet.getString("date");
            System.out.println("id="+id+",name="+name+",age="+age+",date="+date);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
