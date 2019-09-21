package com.qtone.gy.dto;
/**
 * @ClassName Node.java
 * @author qichenghao
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年08月28日 14:04:00
 */

public class Node {
    // 数据
    private  String data;
    // 左子树
    private Node lchild;
    // 右子树
    private Node rchild;

    public Node() {
    }

    public Node(String data) {
        this.data = data;
    }

    public Node(String data, Node lchild, Node rchild) {
        super();
        this.data = data;
        this.lchild = lchild;
        this.rchild = rchild;

    }
    public String getData() {
        return data;
    }
    public Node getLchild() {
        return lchild;
    }
    public Node getRchild() {
        return rchild;
    }

}
