package com.ruan.yuanyuan.mybatis.executor.impl;

import com.ruan.yuanyuan.mybatis.executor.MyExecutor;

import java.sql.*;

/**
 * @ClassName: MySimplExecutor
 * @author: ruanyuanyuan
 * @date: 2019/12/22 15:59
 * @version: 1.0
 * @description:
 **/
public class MySimpleExecutor implements MyExecutor {

    @Override
    public <E> E excute(String statement, String parameter) {

        try {
            Connection connection = getConnection();
            System.out.println(String.format(statement, parameter));
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(statement, parameter));
            ResultSet resultSet = preparedStatement.executeQuery();
            return (E) resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        String Url = "jdbc:mysql://localhost:3306/cloudproject?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
        ;
        String User = "ryy";
        String Password = "123456";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(Url, User, Password);
        return con;
    }
}
