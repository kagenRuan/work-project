package com.ruan.yuanyuan.mybatis.typehander;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//测试typeHandler
public class TestTypeHandler<T> implements TypeHandler<T> {

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("设置参数类型");
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        System.out.println("根据columnName设置返回结果的类型");
        return null;
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        System.out.println("根据columnIndex设置返回结果的类型");
        return null;
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        System.out.println("根据columnIndex设置返回结果的类型");
        return null;
    }
}
