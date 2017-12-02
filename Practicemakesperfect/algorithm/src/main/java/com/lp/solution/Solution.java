
package com.lp.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Solution {

    /**
     * 461. Hamming Distance
     */
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    /**
     * 617. Merge Two Binary Trees
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }

        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
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
     * @param n 输入一个数
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
}
