package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.Product;
import com.ruan.yuanyuan.vo.ProductVo;

/**
 * @ClassName IInventoryService
 * @Author ruanyuanyuan
 * @Date 2020/10/15-14:53
 * @Version 1.0
 * @Description TODO 库存服务
 **/
public interface IProductInventoryService extends IService<Product> {

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:00
     * @Description:  更新商品库存
     * @param productVo:
     * @return: void
     **/
    void updateInventoryCon(ProductVo productVo);


    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:00
     * @Description:  更新商品缓存
     * @param productVo:
     * @return: void
     **/
    void updateInventoryConCache(ProductVo productVo);

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:00
     * @Description:  删除商品缓存
     * @param productId: 商品ID
     **/
    void removeInventoryConCache(String productId);

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:00
     * @Description:  尝试获取商品的库存
     * @param productId: 商品ID
     **/
    ProductVo getProductInventory(String productId);
}
