package com.ruan.yuanyuan;

import com.ruan.yuanyuan.lock.LockTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() throws InterruptedException {
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		condition.await();
		Thread a = new Thread(() ->{
			new LockTest().lock1(lock);
		},"ThreadA");
		a.start();
		new Thread(() ->{new LockTest().lock1(lock);},"ThreadB").start();
		new Thread(() ->{new LockTest().lock1(lock);},"ThreadC").start();
		new Thread(() ->{new LockTest().lock1(lock);},"ThreadD").start();
		new Thread(() ->{new LockTest().lock1(lock);},"ThreadE").start();
	}

}
