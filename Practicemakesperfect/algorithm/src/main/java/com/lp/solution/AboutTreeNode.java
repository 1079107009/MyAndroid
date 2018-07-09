package com.lp.solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 树的遍历 前序遍历 中序遍历 后序遍历 层次遍历 最大深度 对称二叉树
 * F
 * /  \
 * B     G
 * /  \     \
 * A    D     I
 * /  \   /
 * C    E H
 * 前序遍历首先访问根节点，然后遍历左子树，最后遍历右子树。FBADCEGIH
 * 中序遍历是先遍历左子树，然后访问根节点，然后遍历右子树。ABCDEFGHI
 * 后序遍历是先遍历左子树，然后遍历右子树，最后访问树的根节点。ACEDBHIGF
 * 层序遍历->FBGADICEH
 *
 * @author someone
 * @date 2018/4/27
 */
public class AboutTreeNode {

    /**
     * 递归前序遍历二叉树
     *
     * @param root
     * @return
     */
    public static List<Integer> preorderTraversal(TreeNode1 root) {
        List<Integer> list = new ArrayList<>();
        preoderTree(list, root);
        return list;
    }

    /**
     * 递归前序遍历二叉树
     */
    private static void preoderTree(List<Integer> list, TreeNode1 root) {
        if (root == null) {
            return;
        }
        //先访问根节点
        list.add(root.val);
        //然后遍历左子树
        preoderTree(list, root.left);
        //最后遍历右子树
        preoderTree(list, root.right);
    }

    /**
     * 递归中序遍历二叉树
     */
    public static List<Integer> inorderTraversal(TreeNode1 root) {
        List<Integer> list = new ArrayList<>();
        inorderTree(list, root);
        return list;
    }

    /**
     * 递归中序遍历二叉树
     */
    private static void inorderTree(List<Integer> list, TreeNode1 root) {
        if (root == null) {
            return;
        }
        //先遍历左子树
        inorderTree(list, root.left);
        //然后访问根节点
        list.add(root.val);
        //最后遍历右子树
        inorderTree(list, root.right);
    }

    /**
     * 递归后序遍历二叉树
     */
    public static List<Integer> postorderTraversal(TreeNode1 root) {
        List<Integer> list = new ArrayList<>();
        postorderTree(list, root);
        return list;
    }

    /**
     * 递归后序遍历二叉树
     */
    private static void postorderTree(List<Integer> list, TreeNode1 root) {
        if (root == null) {
            return;
        }
        //先遍历左子树
        postorderTree(list, root.left);
        //然后遍历右子树
        postorderTree(list, root.right);
        //最后访问根节点
        list.add(root.val);
    }

    /**
     * 二叉树的层次遍历
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrderTree(TreeNode1 root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode1> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode1 head = queue.poll();
                level.add(head.val);
                if (head.left != null) {
                    queue.offer(head.left);
                }
                if (head.right != null) {
                    queue.offer(head.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    public static int maxDepth(TreeNode1 root) {
        if (root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public static boolean isSymmetric(TreeNode root) {
        return false;
    }

    public static void main(String[] args) {
        TreeNode1 root = new TreeNode1(3);
        TreeNode1 root1 = new TreeNode1(9);
        TreeNode1 root2 = new TreeNode1(20);
        TreeNode1 root3 = new TreeNode1(15);
        TreeNode1 root4 = new TreeNode1(7);
        TreeNode1 root5 = new TreeNode1(6);
        TreeNode1 root6 = new TreeNode1(8);
        root.left = root1;
        root.right = root2;
        root1.left = root3;
        root1.right = root4;
        root2.left = root5;
        root2.right = root6;
        System.out.print(preorderTraversal(root));
        System.out.print(inorderTraversal(root));
        System.out.print(postorderTraversal(root));
        System.out.print(levelOrderTree(root));
        System.out.print(maxDepth(root));
    }
}
