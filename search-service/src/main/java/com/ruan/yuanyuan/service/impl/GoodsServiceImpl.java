package com.ruan.yuanyuan.service.impl;

import com.ruan.yuanyuan.dao.GoodsRepository;
import com.ruan.yuanyuan.entity.Goods;
import com.ruan.yuanyuan.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品接口服务
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    //添加商品
    @Override
    public Iterable<Goods> add(List<Goods> goods) {
        Iterable<Goods> goodsIterable = goodsRepository.saveAll(goods);
        return goodsIterable;
    }

    //查询商品
    @Override
    public Iterable<Goods> queryAll() {
        Iterable<Goods> all = goodsRepository.findAll();
        return all;
    }
}
