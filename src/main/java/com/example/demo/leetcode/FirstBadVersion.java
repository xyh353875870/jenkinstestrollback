package com.example.demo.leetcode;

/**
 * 278。第一个错误的版本
 *
 * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。
 * 由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
 *
 * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
 *
 * 你可以通过调用bool isBadVersion(version)接口来判断版本号 version 是否在单元测试中出错。
 * 实现一个函数来查找第一个错误的版本。
 * 你应该尽量减少对调用 API 的次数。
 *
 * 1.入口方法只有一个入参int n。n是总数
 * 2.如果采用二分法，需要判断边界条件，先罗列但不知道是否都是必须（0，奇数，偶数，是否有必要判断当前奇偶数是否为1，3）
 * 3.看起来是需要一个可以递归的复用方法去处理这件事
 * 4.这个题的真坑在于超大自然数时，int类型的超出范围变成负数
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/first-bad-version
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author xyh
 * @date 2022-08-05 4:25 下午
 **/
public class FirstBadVersion {

    public int firstBadVersion(int n) {

        int mid = 0;
        if ((n & 1) == 1) {
            // 奇数(1,2,3,4,5),target=3
            mid = (n - 1) / 2;
        } else {
            // 偶数
            mid = n / 2;
        }
        boolean result = isBadVersion(mid);
        if (result == true) {
            // 当前结果是错误版本，但不确定是否为第一个错误版本
            if (mid == 1) {
                return 1;
            }
            return search(1,mid);
        } else {
            // 当前版本不是错误版本，错误保本范围是当前版本+1的后续版本范围
            return search(mid + 1, n);
        }
    }

    public int search(int start,int end){
        // (4,5)
        if (start == end) {
            return start;
        }
        if ((start + 1) == end) {
            boolean startResult = isBadVersion(start);
            if (startResult == true) {
                return start;
            } else {
                return end;
            }
        }
        // 或许完全没必要区分是否是奇数偶数,ps:{6,7,8,9},{6,7,8,9,10}
        int mid = 0;
        int ab = (end - start) + 1;
        if ((ab & 1) == 1) {
            // 奇数
            mid = end - ((ab + 1) / 2);
        } else {
            // 偶数
            mid = end -(ab / 2);
        }
        boolean midResult = isBadVersion(mid);
        if (midResult == true) {
            // 中间数是错误版本，但不一定是最新的错误版本，错误版本范围是mid为最后一个版本的向前范围
            return search(start, mid);
        } else {
            return search(mid + 1, end);
        }
    }

    public boolean isBadVersion(int x){
        // 补全结构
        if (x == 2147483647) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

        int n = 2147483647;
        FirstBadVersion firstBadVersion = new FirstBadVersion();

        int result = firstBadVersion.firstBadVersion(n);

        System.out.println("输入：" + n + "输出：" + result);
    }

}
