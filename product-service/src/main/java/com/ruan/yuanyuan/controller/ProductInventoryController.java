package com.ruan.yuanyuan.controller;

import com.alibaba.fastjson.JSON;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.request.ProductInventoryCacheReLoadRequest;
import com.ruan.yuanyuan.request.ProductInventoryUpdateRequest;
import com.ruan.yuanyuan.request.Request;
import com.ruan.yuanyuan.service.IProductInventoryService;
import com.ruan.yuanyuan.service.IRequestAsyncProcessorService;
import com.ruan.yuanyuan.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @ClassName ProductInventoryController
 * @Author ruanyuanyuan
 * @Date 2020/10/15-15:48
 * @Version 1.0
 * @Description TODO 商品库存控制器
 **/
@RestController
@RequestMapping("/product/inventory")
public class ProductInventoryController {

    @Autowired
    private IProductInventoryService productInventoryService;
    @Autowired
    private IRequestAsyncProcessorService requestAsyncProcessorService;


    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:50
     * @Description: 更新商品库存
     * @return: com.ruan.yuanyuan.entity.ResultObject
     **/
    @RequestMapping("/updateInventoryCon")
    public ResultObject updateInventoryCon(ProductVo productVo){
        ResultObject resultObject = new ResultObject();
        try{
            Request request = new ProductInventoryUpdateRequest(productVo,productInventoryService);
            requestAsyncProcessorService.processor(request);
        }catch (Exception e){
            e.printStackTrace();
            resultObject.setCode(-999999);
            resultObject.setMsg("库存更新失败");
        }
        return resultObject;
    }



    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/10/15 15:50
     * @Description: 获取商品库存
     * @return: com.ruan.yuanyuan.entity.ResultObject
     **/
    @RequestMapping("/getInventoryConCache")
    public ResultObject updateInventoryConCache(String productId){
        ResultObject resultObject = new ResultObject();
        try{
            //读取缓存数据请求处理
            Request request = new ProductInventoryCacheReLoadRequest(productId,productInventoryService,false);
            requestAsyncProcessorService.processor(request);
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            while (true){
                //如果等待时间大于200那么直接终止while循环，表示redis设置失败或者没有数据
                if(waitTime > 200){
                    break;
                }
                //如果从缓存中读取到数据就直接返回
                ProductVo productVo = productInventoryService.getProductInventory(productId);
                if(null != productVo){
                    resultObject.setData(productVo);
                    return resultObject;
                }
                //如果没有从缓存中读取到结果，就等待一段20毫秒的时间
                Thread.sleep(40);
                endTime = System.currentTimeMillis();
                waitTime = endTime - startTime;

                //尝试从数据库读取数据
                ProductVo dataBaseProductVo = productInventoryService.getProductInventory(productId);
                if(null != dataBaseProductVo){
                    request = new ProductInventoryCacheReLoadRequest(productId,productInventoryService,true);
                    requestAsyncProcessorService.processor(request);
                    System.out.println("第N次读请求，进行阻塞获取到库存数据="+dataBaseProductVo.getInventory());
                    return resultObject;
                }
                System.out.println("第N次读请求，进行阻塞未获取到库存数据");

            }
        }catch (Exception e){
            e.printStackTrace();
            resultObject.setCode(-999999);
            resultObject.setMsg("库存更新缓存失败");
        }
        resultObject.setData(0);
        resultObject.setMsg("读取缓存数据为空");
        return resultObject;
    }
}
