package com.ruan.yuanyuan.service;

import com.ruan.yuanyuan.entity.Msg;

public interface QueryDataService {

    void  findData(String name,String date,String roeKey) throws Exception;

    void close() throws Exception;
}
