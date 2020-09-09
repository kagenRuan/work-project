package com.ruan.yuanyuan.suanfa;

/**
 * @ClassName ErChaShu
 * @Author ruanyuanyuan
 * @Date 2020/9/8-16:30
 * @Version 1.0
 * @Description TODO 二叉树遍历
 *                   前序遍历：先输出父节点，再遍历左子树和右子树
 *                   中序遍历：先遍历左子树，再输出父节点，再遍历右子树
 *                   后序遍历：先遍历左子树，再遍历右子树，再输出父节点
 **/
public class ErChaShu {

    private TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode preNodeOrder(String id){
        if(root !=null){
          return root.preNodeOrder(id);
        }else {
            return null;
        }
    }

    public TreeNode midNodeOrder(String id){
        if(root !=null){
            return root.midNodeOrder(id);
        }else {
            return null;
        }
    }

    public TreeNode houNodeOrder(String id){
        if(root !=null){
            return root.houNodeOrder(id);
        }else {
            return null;
        }
    }

    public void preNode(){
        if(root != null){
            root.preNode();
        }
    }

    public void midNode(){
        if(root != null){
            root.midNode();
        }
    }

    public void houNode(){
        if(root != null){
            root.houNode();
        }
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/9/8 17:34
     * @Description: 测试
     **/
    public static void main(String[] args) {
        //先构建二叉树
        ErChaShu shu = new ErChaShu();
        TreeNode treeNode1 = new TreeNode("1","宋江");
        TreeNode treeNode2 = new TreeNode("2","吴用");
        TreeNode treeNode3 = new TreeNode("3","卢俊义");
        TreeNode treeNode4 = new TreeNode("4","林冲");
        TreeNode treeNode5 = new TreeNode("5","关胜");

        shu.setRoot(treeNode1);
        treeNode1.setLeft(treeNode2);
        treeNode1.setRight(treeNode3);
        treeNode3.setRight(treeNode4);
        treeNode3.setLeft(treeNode5);

        System.out.println("前序查找节点");
        TreeNode preNode = shu.preNodeOrder("3");
        System.out.println(preNode);

        System.out.println("中序查找节点");
        TreeNode midNode = shu.midNodeOrder("4");
        System.out.println(midNode);

        System.out.println("后序查找节点");
        TreeNode houNode = shu.houNodeOrder("4");
        System.out.println(houNode);



        //前序遍历 [1,2,3,5,4]
        System.out.println("前序遍历");
        shu.preNode();

        //中序遍历 [2,1,5,3,4]
        System.out.println("中序遍历");
        shu.midNode();

        //后续遍历 [2,5,4,3,1]
        System.out.println("后序遍历");
        shu.houNode();
    }
}


class TreeNode{

    private String id;
    private String name;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //通过前序遍历查找指定节点
    public TreeNode preNodeOrder(String id){

        if(this != null && this.id.equals(id)){
            return this;
        }
        TreeNode treeNode = null;
        if(this.left != null){
            treeNode = this.left.preNodeOrder(id);
        }
        if (treeNode != null){
            return treeNode;
        }

        if(this.right != null){
            treeNode = this.right.preNodeOrder(id);
        }
        return treeNode;
    }

    //通过中序遍历查找指定节点
    public TreeNode midNodeOrder(String id){
        TreeNode treeNode = null;
        //先遍历左子树
        if(this.left != null){
            treeNode = this.left.midNodeOrder(id);
        }
        if(treeNode != null){
            return treeNode;
        }
        //在判断父节点
        if(this.id.equals(id)){
            return this;
        }
        //再遍历右子节点
        if(this.right != null){
            treeNode = this.right.midNodeOrder(id);
        }
        return treeNode;
    }

    //通过后序遍历查找指定节点
    public TreeNode houNodeOrder(String id){
        TreeNode treeNode = null;
        if(this.left != null){
            treeNode = this.left.houNodeOrder(id);
        }
        if(treeNode != null){
            return treeNode;
        }

        if(this.right != null){
            treeNode = this.right.houNodeOrder(id);
        }
        if(treeNode != null){
            return treeNode;
        }

        if(this.id.equals(id)){
            treeNode = this;
        }
        return treeNode;
    }



    //前序遍历：先输出付节点，再遍历输出左子树和右子树
    public void preNode(){
        System.out.println(this);//输出当前节点(父节点)
        if(this.left != null){
            this.left.preNode();
        }
        if(this.right != null){
            this.right.preNode();
        }
    }

    //中序遍历：先遍历左子树，再输出父节点，再遍历右子树
    public void midNode(){
        if(this.left != null){
            this.left.midNode();
        }
        System.out.println(this);//输出当前节点(父节点)
        if(this.right != null){
            this.right.midNode();
        }
    }
    //后序遍历：先遍历左子树，再遍历右子树，再输出父节点
    public void houNode(){
        if(this.left != null){
            this.left.houNode();
        }
        if(this.right != null){
            this.right.houNode();
        }
        System.out.println(this);//输出当前节点(父节点)
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
