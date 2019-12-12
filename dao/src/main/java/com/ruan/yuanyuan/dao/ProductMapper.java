package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.entity.Product;
import com.ruan.yuanyuan.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-22
 * Time: 14:43
 * version:1.0
 * Description:商品DAO
 */
public interface ProductMapper extends BaseMapper<Product> {


}
