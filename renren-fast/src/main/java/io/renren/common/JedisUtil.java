package io.renren.common;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @ClassName JedisUtil
 * @Author ruanyuanyuan
 * @Date 2021/1/30-12:55
 * @Version 1.0
 * @Description TODO
 **/
@Component
public class JedisUtil {

    private static Jedis jedis=null;

    static {
        jedis = new Jedis("localhost");
        jedis.auth("123456");
    }


    public void set(String k,int expire,String v){
        if(null != jedis){
            jedis.psetex(k,expire,v);
        }
    }

    public String  get(String k){
        if(null != jedis){
            return jedis.get(k);
        }
        return null;
    }
}
