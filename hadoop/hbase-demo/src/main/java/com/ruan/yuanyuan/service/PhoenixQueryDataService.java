package com.ruan.yuanyuan.service;

import java.sql.SQLException;

public interface PhoenixQueryDataService {

    void  query() throws SQLException;

    void close();
}

