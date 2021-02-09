package com.ruan.yuanyuan;

import com.ruan.yuanyuan.cache.RedisDao;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceApplicationTests {

    @Autowired
    private RedisDao redisDao;

    @Test
    public void testRedis(){
        redisDao.getInstance().set("1","1");
    }

    @Test
    public void contextLoads() {

//        String getvalue = redisDao.getInstance().get("yyyyyyy");
//        System.out.println(getvalue);
        LocalDate today = LocalDate.now();
        long continueSignCount = getContinueSignCount(1, today);
        System.out.println("连续签到次数："+continueSignCount);

    }


    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/11/12 17:21
     * @Description: 连续签到功能
     * @param userId:
     * @param date:
     * @return: long
     **/
    public long getContinueSignCount(int userId, LocalDate date) {
        int signCount = 0;
        String type = String.format("u%d", date.getDayOfMonth());
        List<Long> list = redisDao.getInstance().bitfield("sign:1:202011", "GET", type, "0");
        if (CollectionUtils.isNotEmpty(list)) {
            // 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
            long value = list.get(0) == null ? 0 : list.get(0);
            int bound = date.getDayOfMonth();
            //连续签到判定算法
            for (int i = 0; i < bound; i++) {
                if (value >> 1 << 1 == value) {
                    // 低位为0且非当天说明连续签到中断了
                    if (i > 0) {
                        break;
                    }
                } else {
                    signCount += 1;
                }
                value >>= 1;
            }
        }
        return signCount;
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/11/12 17:21
     * @Description: 点赞功能
     * @return: void
     **/
    @Test
    public void  dianZan(){
        String key ="1111";
        for (int i = 0; i <100000 ; i++) {
            String userId= i+"";
            int h;
            int hash = (userId== null) ? 0 : (h = userId.hashCode()) ^ (h >>> 16);
            int index = (6-1) & hash;
            String hashKey = key+""+index;
            String filed=userId;//用户ID
            String value="2020-11-12 17:27";//点赞事件
            redisDao.getInstance().hset(hashKey,filed,value);
        }
    }
    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/11/12 17:21
     * @Description: 取消点赞功能
     * @return: void
     **/
    @Test
    public void cancel(){
        String key="1111";//文章ID
        redisDao.getInstance().hdel(key,"0");//删除点赞数据
    }

}
