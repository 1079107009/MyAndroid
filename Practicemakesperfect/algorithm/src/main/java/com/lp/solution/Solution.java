package com.lp.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Solution {

    /**
     * 旋转字符串
     * 给定一个字符串，要求把字符串前面的若干个字符移动到字符串的尾部，
     * 如把字符串“abcdef”前面的2个字符'a'和'b'移动到字符串的尾部，使得原字符串变成字符串“cdefab”。
     * 请写一个函数完成此功能，要求对长度为n的字符串操作的时间复杂度为 O(n)，空间复杂度为 O(1)。
     */
    private static String reverseString(String str, int from, int to) {
        char[] array = str.toCharArray();
        while (from < to) {
            char c = array[from];
            array[from++] = array[to];
            array[to--] = c;
        }
        return new String(array);
    }

    public static void leftRotateString(String str, int m, int n) {
        m %= n;
        String s1 = reverseString(str, 0, m - 1);
        String s2 = reverseString(s1, m, n - 1);
        String s3 = reverseString(s2, 0, n - 1);
    }

    public static void main(String[] args) {
        int[] a = new int[]{2, 7, 4, 5, 10, 1, 9, 3, 8, 6};

        quickSort(a, 0, a.length - 1);
        binarySearch(a, 0, a.length - 1, 7);

        System.out.println("排序后的结果：");
        for (int x : a) {
            System.out.print(x + " ");
        }
    }

    private static int binarySearch(int[] a, int start, int end, int key) {
        int result = -1;
        if (start > end) {
            return result;
        }
        int mid;
        while (start <= end) {
            mid = (start + end) / 2;
            if (a[mid] < key) {
                start = mid + 1;
            } else if (a[mid] > key) {
                end = mid - 1;
            } else {
                result = mid;
                break;
            }
        }
        return result;
    }

    private static void quickSort(int[] a, int start, int end) {
        if (start > end) {
            //元素为空的情况
            return;
        }
        int partition = divide(a, start, end);
        quickSort(a, start, partition - 1);
        quickSort(a, partition + 1, end);
    }

    private static int divide(int[] a, int start, int end) {
        //以最右边为基准
        int base = a[end];
        //当start等于end时结束循环
        while (start < end) {
            while (start < end && a[start] <= base) {
                //从左边开始遍历，如果小于等于基准值就往右走
                start++;
            }
            //上面的while循环结束时，就说明当前的a[start]的值比基准值大，应与基准值进行交换
            if (start < end) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值右边)，因此右边也要同时向前移动一位
                end--;
            }
            while (start < end && a[end] >= base) {
                //从右边开始遍历，如果大于等于基准值就往左走
                end--;
            }
            //上面的while循环结束时，就说明当前的a[end]的值比基准值小，应与基准值进行交换
            if (start < end) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值左边)，因此左边也要同时向后移动一位
                start++;
            }
        }
        //返回start或者end都可以，因为他俩相等
        return start;
    }

    /**
     * 461. Hamming Distance
     */
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    /**
     * 561. Array Partition I
     * Input: [1,4,3,2]
     * <p>
     * Output: 4
     * Explanation: n is 2, and the maximum sum of pairs is 4 = min(1, 2) + min(3, 4).
     */
    public int arrayPairSum(int[] nums) {
        sortArray(nums);
        int result = 0;
        for (int i = 0; i < nums.length; i += 2) {
            result += nums[i];
        }
        return result;
    }

    private void sortArray(int[] a) {
        for (int i = 0, j = i; i < a.length - 1; j = ++i) {
            int ai = a[i + 1];
            while (ai < a[j]) {
                a[j + 1] = a[j];
                if (j-- == 0) {
                    break;
                }
            }
            a[j + 1] = ai;
        }
    }

    /**
     * 476. Number Complement
     * <p>
     * Given a positive integer, output its complement number.
     * The complement strategy is to flip the bits of its binary representation.
     */
    public int findComplement(int num) {
        return ~num & (Integer.highestOneBit(num) - 1);
    }

    /**
     * 557. Reverse Words in a String III
     * <p>
     * Input: "Let's take LeetCode contest"
     * Output: "s'teL ekat edoCteeL tsetnoc"
     */
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        String[] split = s.split(" ");
        for (String s1 : split) {
            StringBuilder temp = new StringBuilder(s1);
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(" ");
            }
            sb.append(temp.reverse());
        }
        return sb.toString();
    }

    /**
     * 344. Reverse String
     * <p>
     * Given s = "hello", return "olleh".
     */
    public String reverseString(String s) {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    /**
     * 682. Baseball Game
     */
    public int calPoints(String[] ops) {
        int sum = 0;
        LinkedList<Integer> list = new LinkedList<>();
        for (String op : ops) {
            if (op.equals("C")) {
                sum -= list.removeLast();
            } else if (op.equals("D")) {
                list.add(list.peekLast() * 2);
                sum += list.peekLast();
            } else if (op.equals("+")) {
                list.add(list.peekLast() + list.get(list.size() - 2));
                sum += list.peekLast();
            } else {
                list.add(Integer.parseInt(op));
                sum += list.peekLast();
            }
        }
        return sum;
    }

    /**
     * 575. Distribute Candies
     * <p>
     * Input: candies = [1,1,2,3]
     * Output: 2
     * Explanation: For example, the sister has candies [2,3] and the brother has candies [1,1].
     * The sister has two different kinds of candies, the brother has only one kind of candies.
     */
    public int distributeCandies(int[] candies) {
        Set<Integer> kinds = new HashSet<>();
        for (int candy : candies) {
            kinds.add(candy);
        }
        return kinds.size() >= candies.length / 2 ? candies.length / 2 : kinds.size();
    }

    /**
     * 728. Self Dividing Numbers
     */
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> list = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (isSelfDividingNumber(i)) {
                list.add(i);
            }
        }
        return list;
    }

    private boolean isSelfDividingNumber(int number) {
        int temp = number;
        while (number > 0) {
            int i = number % 10;
            //不能包含数字0
            if (i == 0) {
                return false;
            }
            if (temp % i != 0) {
                return false;
            }
            number /= 10;
        }
        return true;
    }

    /**
     * 412. Fizz Buzz
     *
     * @param n
     * @return
     */
    public List<String> fizzBuzz1(int n) {
        List<String> list = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                list.add("FizzBuzz");
                continue;
            }
            if (i % 3 == 0) {
                list.add("Fizz");
                continue;
            }
            if (i % 5 == 0) {
                list.add("Buzz");
                continue;
            }
            list.add(String.valueOf(i));
        }
        return list;
    }

    public List<String> fizzBuzz2(int n) {
        List<String> ret = new ArrayList<>(n);
        for (int i = 1, fizz = 0, buzz = 0; i <= n; i++) {
            fizz++;
            buzz++;
            if (fizz == 3 && buzz == 5) {
                ret.add("FizzBuzz");
                fizz = 0;
                buzz = 0;
            } else if (fizz == 3) {
                ret.add("Fizz");
                fizz = 0;
            } else if (buzz == 5) {
                ret.add("Buzz");
                buzz = 0;
            } else {
                ret.add(String.valueOf(i));
            }
        }
        return ret;
    }

    /**
     * 566. Reshape the Matrix
     *
     * @param nums
     * @param r
     * @param c
     * @return
     */
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int n = nums.length;
        int m = nums[0].length;
        if (n * m != r * c) {
            return nums;
        }
        int[][] res = new int[r][c];
        for (int i = 0; i < r * c; i++) {
            res[i / c][i % c] = nums[i / m][i % m];
        }
        return res;
    }

    /**
     * 771. Jewels and Stones
     *
     * @param J
     * @param S
     * @return
     */
    public int numJewelsInStones(String J, String S) {
        int res = 0;
        Set setJ = new HashSet();
        for (char j : J.toCharArray()) setJ.add(j);
        for (char s : S.toCharArray()) if (setJ.contains(s)) res++;
        return res;
    }

    private void printTree(TreeNode1 root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode1> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode1 node = queue.poll();
            System.out.println(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }


}
