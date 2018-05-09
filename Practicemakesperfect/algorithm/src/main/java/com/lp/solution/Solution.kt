package com.lp.solution

/**
 *
 * @author someone
 * @date 2017/11/20
 *
 */

/**
 * 669. Trim a Binary Search Tree
 */
fun trimBST(root: TreeNode?, L: Int, R: Int): TreeNode? {
    if (root == null) {
        return null
    }
    if (root.value < L) {
        return trimBST(root.right, L, R)
    }
    if (root.value > R) {
        return trimBST(root.left, L, R)
    }
    root.left = trimBST(root.left, L, R)
    root.right = trimBST(root.right, L, R)
    return root
}

/**
 * 翻转二叉树
 */
fun reverseTree(root: TreeNode?): TreeNode? {
    if (root == null) {
        return null
    }
    val left = reverseTree(root.left)
    val right = reverseTree(root.right)
    root.left = right
    root.right = left
    return root
}

/**
 * 铺平二叉树
 */
fun flattenTree(root: TreeNode?): TreeNode? {
    if (root == null) {
        return null
    }
    //先把左右子树铺平
    val left = flattenTree(root.left)
    val right = flattenTree(root.right)
    //再把左右节点置空
    root.left = null
    root.right = null
    //假如左子树生成的链表为空，那么忽略它，把右子树生成的链表指向根节点的右指针
    if (left == null) {
        root.right = right
        return root
    }
    //如果左子树生成链表不为空，那么用while循环获取最后一个节点，并且它的右指针要指向右子树生成的链表的头节点
    var lastLeft = left
    while (lastLeft?.right != null) {
        lastLeft = lastLeft.right
    }
    lastLeft?.right = right
    return root
}

/**
 * 463. Island Perimeter
 */
fun islandPerimeter(grid: Array<IntArray>): Int {
    var islands = 0
    var neighbours = 0
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == 1) {
                islands++ // count islands
                if (i < grid.size - 1 && grid[i + 1][j] == 1) neighbours++ // count down neighbours
                if (j < grid[i].size - 1 && grid[i][j + 1] == 1) neighbours++ // count right neighbours
            }
        }
    }

    return islands * 4 - neighbours * 2
}

/**
 * 657. Judge Route Circle
 */
fun judgeCircle(moves: String): Boolean {
    var v = 0
    var h = 0
    moves.toCharArray().forEach {
        when (it) {
            'U' -> v++
            'D' -> v--
            'L' -> h--
            'R' -> h++
        }
    }
    return v == 0 && h == 0
}

/**
 * 500. Keyboard Row
 */
fun findWords(words: Array<String>): Array<String> {
    return words.filter {
        it.toLowerCase().matches(Regex("[qwertyuiop]*|[asdfghjkl]*|[zxcvbnm]*"))
    }.toTypedArray()
}

/**
 * 766. Toeplitz Matrix
 */
fun isToeplitzMatrix(matrix: Array<IntArray>): Boolean {
    var res = 0
    val h = matrix.size - 1
    val w = matrix[0].size - 1
    for (i in matrix.size - 1 downTo 1) {
        (matrix[i].size - 1 downTo 1)
                .filter { matrix[i][it] == matrix[i - 1][it - 1] }
                .forEach { res++ }
    }
    return res == w * h
}

/**
 * 496. Next Greater Element I
 */
fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray {
    val res = IntArray(nums1.size)
    for (i in nums1.indices) {
        nums2.sliceArray(i + 1 until nums2.size).forEach {
            if (it > nums1[i]) {
                res[i] = it
            } else {
                res[i] = -1
            }
        }
    }
    return res
}

fun main(args: Array<String>) {
    val nums1 = arrayOf(4, 1, 2)
    val nums2 = arrayOf(1, 3, 4, 2)
    nextGreaterElement(nums1.toIntArray(), nums2.toIntArray())
}