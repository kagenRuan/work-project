package com.ruan.yuanyuan;

import com.ruan.yuanyuan.entry.A;
import com.ruan.yuanyuan.entry.C;
import com.ruan.yuanyuan.configuration.MyConfiguration;
import com.ruan.yuanyuan.entry.D;
import com.ruan.yuanyuan.entry.E;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringApplicationTests {

	@Test
	public void contextLoads() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
		A  a = context.getBean(A.class);
		C  c = context.getBean(C.class);
		D  d = context.getBean(D.class);
		E  e = context.getBean(E.class);
		System.out.println(a);
		System.out.println(c);
		System.out.println(e);

	}

}
