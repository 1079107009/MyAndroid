package com.lp.solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 树的遍历 前序遍历 中序遍历 后序遍历
 *          F
 *        /  \
 *      B     G
 *    /  \     \
 *   A    D     I
 *      /  \   /
 *     C    E H
 * 前序遍历首先访问根节点，然后遍历左子树，最后遍历右子树。FBADCEGIH
 * 中序遍历是先遍历左子树，然后访问根节点，然后遍历右子树。ABCDEFGHI
 * 后序遍历是先遍历左子树，然后遍历右子树，最后访问树的根节点。ACEDBHIGF
 * @author someone
 * @date 2018/4/27
 */
public class TreeTraversal {
    List<Integer> list = new ArrayList<>();

    /**
     * 递归前序遍历二叉树
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode1 root) {
        preoderTree(root);
        return list;
    }

    /**
     * 递归前序遍历二叉树
     */
    private void preoderTree(TreeNode1 root) {
        //先访问根节点
        list.add(root.val);
        //然后遍历左子树
        preoderTree(root.left);
        //最后遍历右子树
        preoderTree(root.right);
    }

    /**
     * 递归中序遍历二叉树
     */
    public List<Integer> inorderTraversal(TreeNode1 root) {
        inorderTree(root);
        return list;
    }

    /**
     * 递归中序遍历二叉树
     */
    private void inorderTree(TreeNode1 root) {
        if (root == null) {
            return;
        }
        //先遍历左子树
        inorderTree(root.left);
        //然后访问根节点
        list.add(root.val);
        //最后遍历右子树
        inorderTree(root.right);
    }

    /**
     * 递归后序遍历二叉树
     */
    public List<Integer> postorderTraversal(TreeNode1 root) {
        postorderTree(root);
        return list;
    }

    /**
     * 递归后序遍历二叉树
     */
    private void postorderTree(TreeNode1 root) {
        if (root == null) {
            return;
        }
        //先遍历左子树
        postorderTree(root.left);
        //然后遍历右子树
        postorderTree(root.right);
        //最后访问根节点
        list.add(root.val);
    }

    public static void main(String[] args) {

    }
}
