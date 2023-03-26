package com.example.demo.leetcode;

/**
 * 977.有序数组的平方
 *
 * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
 *
 * ps:
 * 输入：nums = [-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 * 解释：平方后，数组变为 [16,1,0,9,100]
 * 排序后，数组变为 [0,1,9,16,100]
 *
 * 注意点：
 *       1.需要区分正负数
 *       2.需要平方绝对值
 *       3.需要递增排序
 *       4.没说不存在负数平方和整数平方一样的数（狗日的存在这种场景）
 *       5.0还有可能不存在妈的,妈的还有可能0出现不止一次草
 * 可以先简单实现，再考虑优化，
 * 进阶：实现时间复杂度为：O(n)的算法
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/squares-of-a-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author xyh
 * @date 2022-08-09 1:51 下午
 **/
public class SortedSquares {


    public static void main(String[] args) {
        int[] nums = {-7, -3, 2, 3, 11};
        SortedSquares sortedSquares = new SortedSquares();
        int[] result = sortedSquares.sortedSquares(nums);
        for (int i : result) {
            System.out.print(i+",");
        }
    }

    public int[] sortedSquares(int[] nums) {

        // 负数数组，数值有实际意义数字的最长角标
        int secMaxPosition = 0;
        // 最终数组的角标
        int f = 0;
        // 初始数组的数组长度
        int length = nums.length;
        // 负数数组
        int[] secNums = new int[length];
        // 最终数组
        int[] resultNums = new int[length];

        // 开启一个循环，判断与0的关系，记录0的角标，声明一个数组
        for (int i = 0; i < length; i++) {
            int mom = nums[i];
            if (mom < 0) {
                // 这是一个负数，需要位移后，存放到secNums数组中
                int th = mom * mom;
                secNums[i] = th;

            } else if (mom >= 0) {
                if (mom == 0) {
                    // 当前是数字0角标,
                    secMaxPosition = i - 1;
                    resultNums[0] = nums[i];
                    f = f + 1;
                    continue;
                }
                // 这是一个正数，需要位移以后，和负数数组的倒叙角标中对比存放i对应的值。这部分最麻烦
                int a = mom * mom;
                // 当secMaxPosition == 0 的时候意味着，负数数组里面已经没有值了，只需要做顺序向最终数组插入a
                if (secMaxPosition >= 0) {
                    for (;;) {
                        int b = secNums[secMaxPosition];
                        if (b < a) {
                            // 负数平方小，b存入最终数组，secMaxPosition-1,
                            resultNums[f] = b;
                            f++;
                            secMaxPosition = secMaxPosition - 1;
                            if (secMaxPosition < 0) {
                                // 说明负数数组已经没有值了，直接存入a值，break跳出循环,需要外层获取新的a
                                resultNums[f] = a;
                                f++;
                                break;
                            }
                        } else if (b > a) {
                            // 正数平方小，a存入最终数组，break跳出当前循环，需要外层获取新的a
                            resultNums[f] = a;
                            if (f != (length - 1)) {
                                f++;
                            }
                            break;
                        } else if (b == a) {
                            // 存入b，break，f+1，sec-1
                            resultNums[f] = b;
                            f++;
                            secMaxPosition = secMaxPosition - 1;
                            break;
                        }
                    }
                }
            }
        }
        return resultNums;
    }

}
