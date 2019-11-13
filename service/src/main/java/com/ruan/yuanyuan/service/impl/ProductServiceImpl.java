package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.ProductMapper;
import com.ruan.yuanyuan.entity.Product;
import com.ruan.yuanyuan.service.IProductService;
import org.springframework.stereotype.Service;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-29
 * Time: 09:07
 * version: 1.0
 * Description: 商品服务接口
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
