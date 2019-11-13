package com.ruan.yuanyuan.mybatis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.util.function.Supplier;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-10-02
 * @Time: 23:04
 * @version:1.0
 * @Description:
 */
public class RClassPathMapperScanner extends ClassPathScanningCandidateComponentProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(RClassPathMapperScanner.class);

    private static void  warn(Supplier<?> supplier){
        LOGGER.warn(JSON.toJSONString(supplier.get()));
    }


    public static void main(String[] args) {
//        City city = new RClassPathMapperScanner.City("1");
//        RClassPathMapperScanner.warn(() -> city);
//        RClassPathMapperScanner.warn(() -> DefaultFunction);
//        byte a = 32;
//        byte b = (byte)(100+a);
//        System.out.println(b);
//        if(a < b){
//            b++;
//        }else{
//            b--;
//        }
//        System.out.println(b);
        testBasic();
    }


    public static  int testBasic(){
        int i=1;
        try{
            i++;
            System.out.println("try i="+i);
            return i;
        }catch (Exception e){
            i ++ ;
            System.out.println("catch i="+i);
            return i;
        }finally {
            i =10;
            System.out.println("finaly i = "+i);
        }

    }


     static class City{

        public City(String id) {
            this.id = id;
        }

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }


}

@FunctionalInterface
interface DefaultFunction{

    void get();

}


