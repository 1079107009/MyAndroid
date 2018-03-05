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