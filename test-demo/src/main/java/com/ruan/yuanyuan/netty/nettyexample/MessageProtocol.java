package com.ruan.yuanyuan.netty.nettyexample;

/**
 * @ClassName MessageProto
 * @Author ruanyuanyuan
 * @Date 2020/10/2-20:32
 * @Version 1.0
 * @Description TODO 自定义协议包
 **/
public class MessageProtocol {

    private int length;
    private byte[] content;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
