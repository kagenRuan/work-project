package com.ruan.yuanyuan;

import com.ruan.yuanyuan.dao.GoodsRepository;
import com.ruan.yuanyuan.entity.Goods;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EsDemoApplicationTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    void testSave() {
        List<Goods> list = new ArrayList<>();
        list.add(new Goods(1L,"OPPOFindX2","手机","OPPO",4999f));
        list.add(new Goods(2L,"OPPOFindX","手机","OPPO",3999f));
        list.add(new Goods(3L,"OPPORENO","手机","OPPO",2999f));
        list.add(new Goods(4L, "小米手机7", "手机", "小米", 3299.00f));
        list.add(new Goods(5L, "坚果手机R1", "手机", "锤子", 3699.00f));
        list.add(new Goods(6L, "华为META10", "手机", "华为", 4499.00f));
        list.add(new Goods(7L, "小米Mix2S", "手机", "小米", 4299.00f));
        list.add(new Goods(8L, "荣耀V10", "手机", "华为", 2799.00f));
        Iterable<Goods> items = goodsRepository.saveAll(list);
        while (items.iterator().hasNext()){
            Goods item = items.iterator().next();
            System.out.println(item);
        }
    }


    @Test
    public void testFindAll(){
        Iterable<Goods> all = goodsRepository.findAll();
        all.forEach(System.out::println);
    }

}
