package com.ruan.yuanyuan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjectApplicationTests {

    private Set<String> set = new HashSet<>();

    /**
     * 并发工具countDownLatch使用,多线程处理集合数据
     *
     * @throws InterruptedException
     */
    @Test
    public void countDownLatch() throws InterruptedException {
        final int pageSize = 300;
        List<String> list = new CopyOnWriteArrayList();
        for (int i = 0; i < 1000; i++) {
            list.add(i + "");
        }

        int threadNum = list.size() % pageSize == 0 ? list.size() / pageSize : (list.size() / pageSize) + 1;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            int startIndex = i;
            int endIndex = (i + 1) * pageSize >= list.size() ? list.size() : (i + 1) * pageSize;
            new Thread(() -> {
                List<String> result = this.getList(list, startIndex * pageSize, endIndex);
                countDownLatch.countDown();
                set.addAll(result);
            }, i + "").start();
        }
        try {
            countDownLatch.await();
            System.out.println("处理后的数据长度：" + set.size());
            System.out.println(set);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getList(List<String> sources, int startIndex, int endIndex) {
        System.out.println("第" + Thread.currentThread().getName() + "个线程：startIndex:" + startIndex + "endIndex:" + endIndex);
        List<String> list = new ArrayList<>();
        if (!ObjectUtils.isEmpty(sources)) {
            for (int i = startIndex; i < endIndex; i++) {
                list.add(sources.get(i));
            }
            set.addAll(list);
            return list;
        }
        return null;
    }


    @Test
    public void testHash() throws InterruptedException {
//        BlockingQueue queue = new ArrayBlockingQueue(10);
//        queue.offer(1,1000,TimeUnit.SECONDS);
//        Map<String,String> map = new HashMap<>();
//        List list = new ArrayList();

        System.out.println(1 * 0.76);
        System.out.println(32 * 0.75);
    }

    @Test
    public void testBit() {
        /**
         * TODO 二进制最简单计算
         * 举例：求 int a = 12的二进制，求二进制也就是要除2取余
         * 12/2=6  余0  12除2等于6  余0 反推2*6+0=12
         * 6/2=3   余0  6除2等于3   余0 反推2*3+0=6
         * 3/2=1   余1  3除2等于1   余1 反推2*1+1=3
         * 1/2=0   余1  1除2等于0   余1 反推2*0+1=1
         *                   二级制=1100   余数反写
         */
        int a = 12;
        //12/2=6 0
        //6/2=3 0
        //3/2=1 1
        //1/2=0 1
//        System.out.println(Integer.parseInt("1010",2));

    }


}
