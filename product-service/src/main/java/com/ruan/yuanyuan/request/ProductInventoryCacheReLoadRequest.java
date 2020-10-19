package com.ruan.yuanyuan.request;

import com.ruan.yuanyuan.entity.Product;
import com.ruan.yuanyuan.service.IProductInventoryService;
import com.ruan.yuanyuan.vo.ProductVo;

import java.util.Optional;

/**
 * @ClassName ProductInventoryCacheLoadRequest
 * @Author ruanyuanyuan
 * @Date 2020/10/15-15:16
 * @Version 1.0
 * @Description TODO 重新加载商品的库存
 *                   1、从数据库查询最新的商品库存
 *                   2、将最新的商品库存更新到缓存中
 **/
public class ProductInventoryCacheReLoadRequest implements Request{

    //商品ID
    private String productId;

    private IProductInventoryService iProductInventoryService;
    //是否强制刷新
    private boolean forceRefresh;

    public ProductInventoryCacheReLoadRequest() {
    }

    public ProductInventoryCacheReLoadRequest(String productId, IProductInventoryService iProductInventoryService,boolean forceRefresh) {
        this.productId = productId;
        this.iProductInventoryService = iProductInventoryService;
        this.forceRefresh=forceRefresh;
    }

    @Override
    public void process() {
        Product product1 = new Product();
        product1.setId(productId);
        //1、从数据库查询最新的商品库存
        Product product = Optional.ofNullable(iProductInventoryService.getById(productId)).orElse(product1);
        //2、将最新的商品库存更新到缓存中
        ProductVo productVo = new ProductVo();
        productVo.setId(productId);
        productVo.setInventory(product.getInventory());
        iProductInventoryService.updateInventoryConCache(productVo);
        System.out.println("数据查询请求-》商品ID="+productVo.getId()+"，先查询数据库在更新缓存");
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }
}
