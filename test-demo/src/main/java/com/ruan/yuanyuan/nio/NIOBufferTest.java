package com.ruan.yuanyuan.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @ClassName NIOBufferTest
 * @Author ruanyuanyuan
 * @Date 2021/7/10-10:18
 * @Version 1.0
 * @Description TODO
 **/
public class NIOBufferTest {

    public static void main(String[] args) {
        ByteBuffer wrap = ByteBuffer.wrap(new byte[]{1, 2, 3, 4, 5, 6, 7});
        System.out.println(wrap.position());

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        byteBuffer.put("2".getBytes());
        byteBuffer.put("3".getBytes());
        byteBuffer.put("4".getBytes());
        byteBuffer.put("5".getBytes());
        byteBuffer.put("6".getBytes());
        byteBuffer.put("7".getBytes());
        System.out.println(byteBuffer.position());
        byteBuffer.put("8".getBytes());
        System.out.println(byteBuffer.position());

    }
}

