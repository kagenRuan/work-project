package com.ruan.yuanyuan.demo.suanfa;

/**
 * @ClassName Dan_LianBiao
 * @Author ruanyuanyuan
 * @Date 2020/8/15-14:14
 * @Version 1.0
 * @Description TODO 单链表
 **/
public class LianBiao {

    public static void main(String[] args) {
        Node node = LianBiao.dan_lianBiao();
        LianBiao.dan_LianBiao_FanZhuan(node);
        System.out.println("反转后的Node单链表："+node.toString());
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/8/15 14:43
     * @Description: TODO 单链表反正：其思路就是每次讲当前节点设置为head节点的next,并记录上一个循环的节点，然后将当前节点的next设置为记录的上一个节点
     * @param node: Node节点
     * @return: void
     **/
    public static void dan_LianBiao_FanZhuan(Node node){
        Node head = new Node();
        Node temp = null;//记录当前节点
        Node cou = null;//记录当前节点的下一个节点
        Node next = node.next;
        while (next != null){
            cou = next.next;//记录下一个节点，一遍下次循环
            if(temp != null){//如果temp不为空，则说明值为上一个节点的值
                head.next = next; //将当前节点赋值给头节点的next
                next.next=temp;//当前节点的next为上一个节点
                temp = next;//同时将当前节点赋值给temp，作为下一次使用
            }else{ //否则temp为第一次循环
                temp = next;
                temp.next=null;//设置当前节点的next为null
                head.next=temp;//将其复制给头节点的next
            }
            temp = next;
            next = cou;
        }
        node.next=temp;
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/8/15 14:41
     * @Description: 构造单链表
     * @return: Node
     **/
    public static Node dan_lianBiao(){
        Node  head = new Node("head",0);
        Node next = new Node("yy",30);
        head.next = next;
        Node last = new Node("yy1",31);
        next.next=last;
        Node last2 = new Node("yy2",32);
        last.next=last2;
        Node last3 = new Node("yy3",33);
        last2.next=last3;
        System.out.println("单链表："+head.toString());
        return head;
    }

    
}

/**
 * @ClassName Dan_LianBiao
 * @Author ruanyuanyuan
 * @Date 2020/8/15-14:14
 * @Version 1.0
 * @Description TODO Node节点
 **/
class Node{

    public Node() {
    }

    public Node(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String name;
    int age;
    Node next;


    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", next=" + next +
                '}';
    }
}
