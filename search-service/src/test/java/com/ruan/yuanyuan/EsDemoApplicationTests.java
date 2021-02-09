package com.ruan.yuanyuan;

import com.ruan.yuanyuan.dao.GoodsRepository;
import com.ruan.yuanyuan.entity.Goods;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EsDemoApplicationTests {

    @Autowired
    private GoodsRepository goodsRepository;
    @Test
    void contextLoads() {
        List<Goods> list = new ArrayList<>();
        list.add(new Goods(1L,"OPPOFindX2","手机","OPPO",4999f,"http://image.leyou.com/13123.jpg"));
        list.add(new Goods(2L,"OPPOFindX","手机","OPPO",3999f,"http://image.leyou.com/13123.jpg"));
        list.add(new Goods(3L,"OPPORENO","手机","OPPO",2999f,"http://image.leyou.com/13123.jpg"));
        list.add(new Goods(4L, "小米手机7", "手机", "小米", 3299.00f, "http://image.leyou.com/13123.jpg"));
        list.add(new Goods(5L, "坚果手机R1", "手机", "锤子", 3699.00f, "http://image.leyou.com/13123.jpg"));
        list.add(new Goods(6L, "华为META10", "手机", "华为", 4499.00f, "http://image.leyou.com/13123.jpg"));
        list.add(new Goods(7L, "小米Mix2S", "手机", "小米", 4299.00f, "http://image.leyou.com/13123.jpg"));
        list.add(new Goods(8L, "荣耀V10", "手机", "华为", 2799.00f, "http://image.leyou.com/13123.jpg"));
        Iterable<Goods> items = goodsRepository.saveAll(list);
        while (items.iterator().hasNext()){
            Goods item = items.iterator().next();
            System.out.println(item);
        }
    }


    @Test
    public void findAll(){
        Iterable<Goods> all = goodsRepository.findAll();
        all.forEach(System.out::println);
    }

}
