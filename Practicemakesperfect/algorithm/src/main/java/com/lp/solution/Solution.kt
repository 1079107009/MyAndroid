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
 * 463. Island Perimeter
 */
fun islandPerimeter(grid: Array<IntArray>): Int {
    val h = grid.size
    val w = grid[0].size
    for (i in grid) {

    }
}