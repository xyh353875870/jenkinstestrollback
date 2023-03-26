package com.example.demo.leetcode;

/**
 * 704.二分查找
 *
 *  1.判断数组长度是否大于0长度，如果数组长度=0直接返回结果（不存在）
 *  2.  如果判断奇数还是偶数，如果是偶数，获取偶数长度数组中间的两个值a,b
 *      如果长度是奇数，获取这个中位数mid的值，存在以下情况：
 *      mid<target,执行4
 *      mid=target,返回结果（存在）
 *      mid>target,执行4
 *  3.如果是a,b和target值比较，存在以下情况：
 *      target<a,执行4
 *      a=tartget,返回结果（存在）
 *      a<target<b,返回结果（不存在）
 *      b=target,返回结果（存在）
 *      b<target,执行4
 *  4.知道target范围，需要根据分割点，分割数组，重新执行逻辑1
 *  一个问题：如果数组切割，目标最后存在，仍然需要返回的是整个数组长度下的角标
 * @author xyh
 * @date 2022-08-01 5:38 下午
 **/
public class BinarySearch {
    public int search(int[] nums, int target) {

        int size = nums.length;
        if (size == 0) {
            return -1;
        }
        if ((size & 1) == 0) {
            // 偶数
            // startLocation，endLocation是当前中间数的数组角标
            int endLocation = size / 2;
            int startLocation = endLocation - 1;
            int a = nums[startLocation];
            int b = nums[endLocation];

            if (a == target) {
                return startLocation;
            } else if (a > target) {
                // 走切割数组逻辑
                //循环次数或者叫，以中间数分割后，后一部分数组里面的元素个数
                int times = startLocation;
                // 创建新数组备用
                int[] newNums = new int[times];
                for (int i = 0; i < times; i++) {
                    newNums[i] = nums[i];
                }
                return search(newNums, target);
            } else if (a < target && target < b) {
                return -1;
            } else if (b == target) {
                return endLocation;
            } else if (b < target) {
                // 走切割数组逻辑
                // location是当前中间数的数组角标
                int location = endLocation ;
                //循环次数或者叫，以中间数分割后，后一部分数组里面的元素个数
                int times = size - location - 1;
                // 创建新数组
                int[] newNums = new int[times];
                for (int i = 0; i < times; i++) {
                    newNums[i] = nums[location + i + 1];
                }
                int cao = search(newNums, target);
                if (cao == -1) {
                    return -1;
                }else {
                    return cao + 1 + location;
                }
            }
        }else {
            // 奇数
            // location是当前中间数的数组角标
            int location = (size + 1) / 2 - 1 ;
            //循环次数或者叫，以中间数分割后，后一部分数组里面的元素个数
            int times = size - location - 1;
            // 创建新数组
            int[] newNums = new int[times];
            int mid = nums[location];
            if (mid == target) {
                return location;
            } else if (mid < target) {
                for (int i = 0; i < times; i++) {
                    newNums[i] = nums[location + i + 1];
                }
                int cao = search(newNums, target);
                if (cao == -1) {
                    return -1;
                }else {
                    return cao + 1 + location;
                }
            } else if (mid > target) {
                for (int i = 0; i < times; i++) {
                    newNums[i] = nums[i];
                }
                int cao = search(newNums, target);
                if (cao == -1) {
                    return -1;
                }else {
                    return cao;
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {

        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;

        BinarySearch binarySearch = new BinarySearch();
        int post  = binarySearch.search(nums, target);
        System.out.printf(post + "");

    }

}
