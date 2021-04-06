package com.ruan.yuanyuan.netty.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NIOChannel_FileChannelExample
 * @Author ruanyuanyuan
 * @Date 2020/9/26-20:56
 * @Version 1.0
 * @Description TODO 通过NIO的方式练习Buffer+Channel写数据到本地文件
 **/
public class NIOChannel_FileChannelExample {

    public static void main(String[] args) throws Exception{
        String fileName="/Users/ruanyuanyuan/IdeaProjects/work-project/demo/src/main/java/com/ruan/yuanyuan/netty/nio/fileChannel.txt";
        String targetFileName="/Users/ruanyuanyuan/IdeaProjects/work-project/demo/src/main/java/com/ruan/yuanyuan/netty/nio/fileChannel1.txt";
        String data="hell channel";
        //写数据到文件
        write(data,fileName);
        //读取文件中的数据
        read(fileName);
        //将源文件中的内容复制到另外一个文件中
        copy(fileName,targetFileName);
        //copy
        copyImage(fileName);
        //通过对外内存映射方式修改数据
        mappedByteBufferTest(fileName);


    }

    public static void mappedByteBufferTest(String fileName)throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName,"rw");
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 将fileName文件中的第一个字符到第五个字符将其映射到内存中进行修改
         * FileChannel.MapMode.READ_WRITE 表示使用的是读写模式
         * 0：表示修改的其实位置
         * 5：表示最该的最大位置
         **/
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE,0,5);
        mappedByteBuffer.put(0,(byte) 'H');
        mappedByteBuffer.put(4,(byte) 'H');
        channel.close();
    }

    //将图片内容复制到另外一个图片中
    public static void copyImage(String sourcesFile)throws Exception{
        FileOutputStream outputStream = new FileOutputStream("/Users/ruanyuanyuan/IdeaProjects/work-project/demo/src/main/java/com/ruan/yuanyuan/netty/nio/1.png");
        FileInputStream inputStream = new FileInputStream("/Users/ruanyuanyuan/IdeaProjects/work-project/demo/src/main/java/com/ruan/yuanyuan/netty/nio/2.png");

        FileChannel outputStreamChannel = outputStream.getChannel();
        FileChannel inputStreamChannel = inputStream.getChannel();

        outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());
        outputStreamChannel.close();
        inputStreamChannel.close();
        inputStream.close();
        outputStream.close();
    }

    //复制
    public static void copy(String sourcesFileName,String targetFileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(sourcesFileName);
        FileChannel channel = inputStream.getChannel();


        FileOutputStream outputStream = new FileOutputStream(targetFileName);
        FileChannel channel1 = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true){
            byteBuffer.clear();//这一步很重要，用于重置Buffer的索引位置
            int red = channel.read(byteBuffer);
            if(red == -1){
                break;
            }
            byteBuffer.flip();
            channel1.write(byteBuffer);
        }

        inputStream.close();
        outputStream.getChannel();
    }

    //从文件中读取数据
    public static void read(String fileName) throws Exception {
        File file = new File(fileName);
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }

    public static void write(String data,String fileName) throws Exception{
        //获取文件输出流
        FileOutputStream outputStream = new FileOutputStream(fileName);
        //获取文件流的Channel通道
        FileChannel fileChannel = outputStream.getChannel();
        //创建一个大小为1024的ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将数据写入到ByteBuffer中
        byteBuffer.put(data.getBytes());
        //由于下面要将Buffer中的数据写入到Channel中，所以需要将ByteBuffer进行反转
        byteBuffer.flip();
        //将ByteBuffer中的数据写入到Channel
        fileChannel.write(byteBuffer);
        //关闭输出流
        outputStream.close();
    }
}
