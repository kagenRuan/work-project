package com.ruan.yuanyuan.suanfa;

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
//        Node node1 = LianBiao.fanZhuan_danLianBiao(node,2);
//        System.out.println(node1);
    }


    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/8/16 19:45
     * @Description: 求链表中第K个节点
     * @param node: 单链表
     * @param k: 表示求第开个节点
     * @return: void
     * 举例：这就是单链表=》 | 1 | 2 | 3 | 4 | 5 | 求倒数第3个Node节点
     * 1、第一次循环根据3循环得到节点4
     * 2、第二次循环只要节点4的next不为null,那么就继续循环，这里就会继续从4开始循环，而循环值是从Node节点开始的，所以得到3
     **/
    public static Node fanZhuan_danLianBiao(Node node,int k){
        Node first = node;
        Node last  = node;

        /**
         * @Description: 循环【k】次
         **/
        for (int i = 0; i < k; i++) {
            if(first == null){
                return null;
            }
            first = first.next;
        }
        /**
         * @Description: 如果first不为null，那么就继续循环，返回值就等于Node节点开始到first位null
         **/
        while (first != null){
            first = first.next;
            last = last.next;
        }
        return last;
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/8/15 14:43
     * @Description: TODO 单链表反转：其思路就是每次讲当前节点设置为head节点的next,并记录上一个循环的节点，然后将当前节点的next设置为记录的上一个节点
     * @param node: Node节点
     * @return: void
     **/
    public static void dan_LianBiao_FanZhuan(Node node){

            Node head = new Node();
            Node temp = null;//用于记录当前node
            Node cou = null;//记录下一个node

            Node next = node.next;
            while (next != null){
                cou = next.next;
                if(temp !=null){
                    head.next = next;
                    next.next = temp;
                    temp = next;
                }else{
                    temp = next;
                    temp.next =null;
                    head.next=temp;
                }
                next = cou;
            }
            System.out.println(head);
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
                '}';
    }
}
