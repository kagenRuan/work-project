package com.ruan.yuanyuan.cache;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @ClassName RedisDao
 * @Author ruanyuanyuan
 * @Date 2020/10/15-14:26
 * @Version 1.0
 * @Description TODO
 **/
@Component
public class RedisDao {

    private static Jedis jedis = null;

    static {
        jedis = new Jedis("localhost",6379);
        jedis.auth("123456");
    }

    public Jedis getInstance(){
        return jedis;
    }

}
