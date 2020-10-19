package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.cache.RedisDao;
import com.ruan.yuanyuan.dao.ProductMapper;
import com.ruan.yuanyuan.entity.Product;
import com.ruan.yuanyuan.service.IProductInventoryService;
import com.ruan.yuanyuan.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName IInventoryServiceImpl
 * @Author ruanyuanyuan
 * @Date 2020/10/15-14:54
 * @Version 1.0
 * @Description TODO 库存服务实现
 **/
@Service
public class IProductInventoryServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductInventoryService {

    @Autowired
    private RedisDao redisDao;

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:01
     * @Description: 更新商品库存
     * @param productVo:
     **/
    @Override
    public void updateInventoryCon(ProductVo productVo) {
        Product product = new Product();
        product.setId(productVo.getId());
        product.setInventory(productVo.getInventory());
        boolean result = super.updateById(product);
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:00
     * @Description:  更新商品缓存
     * @param productVo:
     **/
    @Override
    public void updateInventoryConCache(ProductVo productVo) {
        String key = "product:inventory:"+productVo.getId();
        redisDao.getInstance().set(key,String.valueOf(productVo.getInventory()));
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:00
     * @Description:  删除商品缓存
     * @param productId: 商品ID
     **/
    @Override
    public void removeInventoryConCache(String productId) {
        String key = "product:inventory:"+productId;
        redisDao.getInstance().del(key);
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:00
     * @Description:  尝试获取商品的缓存
     * @param productId: 商品ID
     **/
    @Override
    public ProductVo getProductInventory(String productId) {

        String key = "product:inventory:"+productId;
        String result = redisDao.getInstance().get(key);
        if(StringUtils.isNotBlank(result)){
            try{
                ProductVo productVo = new ProductVo();
                productVo.setId(productId);
                Integer inventory = Integer.valueOf(result);
                productVo.setInventory(inventory);
                return productVo;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
