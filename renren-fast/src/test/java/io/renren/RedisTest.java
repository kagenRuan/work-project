package io.renren;

import io.renren.common.JedisUtil;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.common.utils.RedisUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private JedisUtil jedisUtil;

	@Test
	public void contextLoads() {
//		jedisUtil.set("1","222");
		String v = jedisUtil.get("1");
		System.out.println(v);
//		SysUserEntity user = new SysUserEntity();
//		user.setEmail("qqq@qq.com");
//		redisUtils.set("user", user);
//
//		System.out.println(ToStringBuilder.reflectionToString(redisUtils.get("user", SysUserEntity.class)));
	}

}
