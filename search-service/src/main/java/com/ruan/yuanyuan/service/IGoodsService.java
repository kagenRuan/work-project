package com.ruan.yuanyuan.service;

import com.ruan.yuanyuan.entity.Goods;

import java.util.List;

/**
 * 商品查询服务接口
 */
public interface IGoodsService {

    //添加商品
    Iterable<Goods> add(List<Goods> goods);

    //查询所有商品
    Iterable<Goods> queryAll();

}
