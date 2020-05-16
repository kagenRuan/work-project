package com.ruan.yuanyuan.jvm;

/**
 * @ClassName: Demo
 * @author: ruanyuanyuan
 * @date: 2020/5/14 13:59
 * @version: 1.0
 * @description: 用于模拟 Yong Gc
 * VM参数：-XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * -XX:NewSize=5242880 设置新生代大小
 * -XX:MaxNewSize=5242880 设置新生代最大大小
 * -XX:InitialHeapSize=10485760 设置堆的初始大小
 * -XX:MaxHeapSize=10485760 设置堆的最大大小
 * -XX:SurvivorRatio=8 设置新生代Eden区占新生代的百分之八十
 * -XX:PretenureSizeThreshold=10485760 设置大对象大小
 * -XX:+UseParNewGC 设置使用ParNewGC
 * -XX:+UseConcMarkSweepGC 设置老年代使用并行手机
 * -XX:+PrintGCDetails 设置打印GC详情
 * -XX:+PrintGCTimeStamps 设置打印每次GC的时间
 * -Xloggc:gc.log 设置生成GC文件
 * ===========================================================================下面是一个真实的GC文件====================================================================
 * Java HotSpot(TM) 64-Bit Server VM (25.231-b11) for bsd-amd64 JRE (1.8.0_231-b11), built on Oct  5 2019 03:15:25 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
 * Memory: 4k page, physical 8388608k(269360k free)
 *
 * /proc/meminfo:本行以及上面不用管
 *
 * CommandLine flags: -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=5242880 -XX:NewSize=5242880 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
 * 0.212: [GC (Allocation Failure) 0.212: [ParNew: 3162K->512K(4608K), 0.0017540 secs] 3162K->1617K(9728K), 0.0018840 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
 * 0.215: [GC (Allocation Failure) 0.215: [ParNew: 2623K->0K(4608K), 0.0023740 secs] 3729K->1583K(9728K), 0.0024633 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * Heap
 *  par new generation   total 4608K, used 2174K [0x00000007bf600000, 0x00000007bfb00000, 0x00000007bfb00000)
 *   eden space 4096K,  53% used [0x00000007bf600000, 0x00000007bf81f858, 0x00000007bfa00000)
 *   from space 512K,   0% used [0x00000007bfa00000, 0x00000007bfa00000, 0x00000007bfa80000)
 *   to   space 512K,   0% used [0x00000007bfa80000, 0x00000007bfa80000, 0x00000007bfb00000)
 *  concurrent mark-sweep generation total 5120K, used 1583K [0x00000007bfb00000, 0x00000007c0000000, 0x00000007c0000000)
 *  Metaspace       used 3093K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 337K, capacity 388K, committed 512K, reserved 1048576K
 *==========上面GC文件的分析==============================================================================================================================================
 *CommandLine flags: -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=5242880 -XX:NewSize=5242880 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
 *CommandLine flags这行表示我们设置的VM参数
 *0.212: [GC (Allocation Failure) 0.212: [ParNew: 3162K->512K(4608K), 0.0017540 secs] 3162K->1617K(9728K), 0.0018840 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
 *0.212: [GC (Allocation Failure) 表示系统运行0.212秒后发生分配失败，也就是发生GC
 *ParNew: 3162K->512K(4608K) 0.0017540 secs 新生代使用ParNewGC垃圾回收器，新生代可用内存为4608K也就是4.5MB，对新生代执行了一次GC，GC之前3162KB，GC之后只用512KB的对象存活。此次GC耗时0.0017540
 *3162K->1617K(9728K) 整个堆的内存情况，堆内存总共9728KB(9.5MB) GC之前堆使用内存为3162KB，GC之后堆内存使用1617KB
 *[Times: user=0.01 sys=0.00, real=0.00 secs] 这些都是本次GC的耗时，几乎都为0毫秒
 *
 * Heap(堆)
 *par new generation   total 4608K, used 2174K [0x00000007bf600000, 0x00000007bfb00000, 0x00000007bfb00000) parNew垃圾回收器负责的内存大小为4608K 使用了2174K
 *eden space 4096K,  53% used [0x00000007bf600000, 0x00000007bf81f858, 0x00000007bfa00000) 新生代Eden大小4096K，已使用53%
 *from space 512K,   58% used [0x00000007bfa00000, 0x00000007bfa00000, 0x00000007bfa80000)  Surviver from区512K，已使用58%
 *to   space 512K,   0% used [0x00000007bfa80000, 0x00000007bfa80000, 0x00000007bfb00000)  Surviver to区512K，0使用
 *concurrent mark-sweep generation total 5120K, used 1583K [0x00000007bfb00000, 0x00000007c0000000, 0x00000007c0000000) CMS垃圾回收器负责内存大小5120KB，使用了1583K
 *Metaspace       used 3093K, capacity 4496K, committed 4864K, reserved 1056768K 永久代(方法区)使用了3093K
 *class space    used 337K, capacity 388K, committed 512K, reserved 1048576K
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 **/
public class Demo {


    public static void main(String[] args) {
        byte[] array1 = new byte[1024 * 1024];
        array1 =  new byte[1024 * 1024];
        array1 =  new byte[1024 * 1024];
        array1 = null;

        byte[] array2 = new byte[2 * 1024 * 1024];


    }
}
