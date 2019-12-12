package com.ruan.yuanyuan.单例;

/**
 * 单利模式
 */
public class SigetonTest {

    /**
     * 这里为什么要使用volatile修饰
     * 是因为在对象实例化的时候会有三个步骤：
     * 1.分配对象内存空间
     * 2.初始化对象
     * 3.设置对象引用指向内存地址
     * 而2，3两个步骤是没有依赖关系的所以编译器会有可能对其进行指令重排
     * 从而造成先将对象引用指向内存地址然后在初始化对象就造成其他线程在获取实例的时候拿到空的对象
     */
    private static volatile SigetonTest sigetonTest = null;

    private SigetonTest() {
        System.out.printf("测试单利模式 \n");
    }

    /**
     * 第一种方式，但是这种只能在单线程下使用
     * @return
     */
//    private static SigetonTest getInstance(){
//        if(null == sigetonTest){
//            sigetonTest = new SigetonTest();
//        }
//        return sigetonTest;
//    }

    /**
     * 这种可以使用在多线程情况下不出问题
     * 其实就是使用synchronized同步代码块的时候在其前后都进行判断也就是俗称doouble check双端检测
     *
     * @return
     */
    private static SigetonTest getInstance() {
        if (null == sigetonTest) {
            synchronized (SigetonTest.class) {
                if (null == sigetonTest) {
                    sigetonTest = new SigetonTest();
                }
            }
        }
        return sigetonTest;
    }


    public static void main(String[] args) {
        /**
         * 单线程的情况下
         */
//        System.out.println(SigetonTest.getInstance() == SigetonTest.getInstance());
//        System.out.println(SigetonTest.getInstance() == SigetonTest.getInstance());
//        System.out.println(SigetonTest.getInstance() == SigetonTest.getInstance());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SigetonTest.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
