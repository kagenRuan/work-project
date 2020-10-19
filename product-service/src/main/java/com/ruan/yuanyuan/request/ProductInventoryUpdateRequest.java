package com.ruan.yuanyuan.request;

import com.ruan.yuanyuan.service.IProductInventoryService;
import com.ruan.yuanyuan.vo.ProductVo;

/**
 * @ClassName DataUpdateRequest
 * @Author ruanyuanyuan
 * @Date 2020/10/15-09:47
 * @Version 1.0
 * @Description TODO 如果商品发生交易，那么此时需要修改库存，就会发送数据更新
 *                   1、先删除缓存
 *                   2、再更新数据库
 **/
public class ProductInventoryUpdateRequest implements Request {

    private ProductVo productVo;

    private IProductInventoryService iProductInventoryService;

    public ProductInventoryUpdateRequest(ProductVo productVo, IProductInventoryService iProductInventoryService) {
        this.productVo = productVo;
        this.iProductInventoryService = iProductInventoryService;
    }

    @Override
    public void process() {
        //TODO 1、先删除缓存
        String key = "product:inventory:"+productVo.getId();
        iProductInventoryService.removeInventoryConCache(key);
        //TODO 2、再更新数据库
        iProductInventoryService.updateInventoryCon(productVo);
        System.out.println("数据修改请求-》商品ID="+productVo.getId()+"，先删除缓存，在更新数据库");
    }

    @Override
    public String getProductId() {
        return productVo.getId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }
}
