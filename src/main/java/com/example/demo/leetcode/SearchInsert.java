package com.example.demo.leetcode;

/**
 * 35.搜索插入位置
 *
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。
 * 如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *
 * 请必须使用时间复杂度为 O(log n) 的算法。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/search-insert-position
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xyh
 * @date 2022-08-08 3:32 下午
 **/
public class SearchInsert {

    public int searchInsert(int[] nums, int target) {
        // 中间角标
        int mid = 0;
        int length = nums.length;

        if (length == 1) {
            int tet = nums[0];
             if (tet >= target) {
                return 0;
             } else if (tet < target) {
                 return 1;
             }
        } else if ((length & 1) == 1) {
            // 奇数
            mid = (length + 1) / 2;
        } else {
            // 偶数
            mid = length / 2;
        }

        int value = nums[mid];
        if (value == target) {
            return mid;
        } else if (value > target) {
            int[] newNums = new int[mid];
            // target落在数组左侧范围内
            for (int i = 0; i < mid; i++) {
                newNums[i] = nums[i];
            }
            return searchInsert(newNums, target);
        } else if (value < target) {
            int[] newNums = new int[length - mid - 1];
            // target落在右半部分数组
            int times = length - mid - 1;
            for (int i = 0; i < times; i++) {
                newNums[i] = nums[mid + i + 1];
            }
            if (newNums.length==0) {
                return nums.length;
            }
            return mid + searchInsert(newNums, target) + 1;
        }
        return 0;
    }


    public static void main(String[] args) {
        SearchInsert searchInsert = new SearchInsert();

        int[] nums = {1,3};
        int target = 4;
        int pos = searchInsert.searchInsert(nums, target);

        System.out.println("输出：" + pos);
    }
}
