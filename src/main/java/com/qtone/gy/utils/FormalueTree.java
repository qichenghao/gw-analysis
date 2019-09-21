package com.qtone.gy.utils;/**
 * @ClassName FormalueTree.java
 * @author qichenghao
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年08月28日 14:03:00
 */

import com.qtone.gy.dto.Node;

import java.util.ArrayList;

/**
 * 　　* @className: FormalueTree
 * 　　* @author qichenghao
 * 　　* @date 2019/8/28 14:03
 * <p>
 *
 */
public class FormalueTree {

    private String s = "";
    private Node root;     //根节点

    /**
     * 创建表达式二叉树
     *
     * @param str 表达式
     */
    public void creatTree(String str) {
        //声明一个数组列表，存放的操作符，加减乘除
        ArrayList<String> operList = new ArrayList<String>();
        //声明一个数组列表，存放节点的数据
        ArrayList<Node> numList = new ArrayList<Node>();
        //第一，辨析出操作符与数据，存放在相应的列表中
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);          //取出字符串的各个字符
            if (ch >= '0' && ch <= '9') {
                s += ch;
            } else {
                numList.add(new Node(s));
                s = "";
                operList.add(ch + "");

            }

        }
        //把最后的数字加入到数字节点中
        numList.add(new Node(s));
//        for(Node node:numList){
//            System.out.println(node.getData());
//        }
//        for(String c:operList){
//            System.out.println(c);
//        }
        while (operList.size() > 0) {    //第三步，重复第二步，直到操作符取完为止
            //第二，取出前两个数字和一个操作符，组成一个新的数字节点
            Node left = numList.remove(0);
            System.out.println(left.getData());
            Node right = numList.remove(0);
            System.out.println(right);
            String oper = operList.remove(0);
            System.out.println(oper);
            Node node = new Node(oper, left, right);
            numList.add(0, node);       //将新生的节点作为第一个节点，同时以前index=0的节点变为index=1

        }
        //第四步，让根节点等于最后一个节点
        root = numList.get(0);
    }

    /**
     * 输出结点数据
     */
    public void output() {
        output(root);      //从根节点开始遍历
    }

    /**
     * 输出结点数据
     *
     * @param node
     */
    public void output(Node node) {
        if (node.getLchild() != null) {       //如果是叶子节点就会终止
            output(node.getLchild());
        }
        System.out.print(node.getData()+"   ");     //遍历包括先序遍历（根左右）、中序遍历（左根右）、后序遍历（左右根）
        if (node.getRchild() != null) {
            output(node.getRchild());
        }

    }


    /**
     * @title checkTree
     * @description  校验计算树是否合法
     * @author qichenghao
     * @param: str
     * @updateTime 2019/8/28 14:43
     * @return java.lang.Boolean
     * @throws
     */
    public Boolean checkTree(String str) {
        //声明一个数组列表，存放的操作符，加减乘除
        ArrayList<String> operList = new ArrayList<String>();
        //声明一个数组列表，存放节点的数据
        ArrayList<Node> numList = new ArrayList<Node>();
        //第一，辨析出操作符与数据，存放在相应的列表中
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);          //取出字符串的各个字符
            if (ch != '+' && ch != '-' && ch != '*' && ch != '/') {
                s += ch;
            } else {
                numList.add(new Node(s));
                s = "";
                operList.add(ch + "");
            }
        }
        //把最后的数字加入到数字节点中
        numList.add(new Node(s));
        int i = 0;
        for(Node node:numList){
            System.out.println(node.getData());
           if(StringUtils.isNotBlank(StringUtils.objToStr(node.getData()))){
               i++;
           }
        }
        for(String c:operList){
            System.out.println(c);
        }
        //System.out.println(i);
       return (i - 1) == operList.size();
    }

    public static void main(String[] args) {
        FormalueTree tree = new FormalueTree();
        //System.out.println(tree.checkTree("属性 * 属性3 / 属性4"));
        tree.creatTree("45+23*56/2--");//创建表达式的二叉树
        tree.output();//输出验证
    }
}
