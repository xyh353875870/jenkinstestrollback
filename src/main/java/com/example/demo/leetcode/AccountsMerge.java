package com.example.demo.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * leetcode 721
 *
 * 给定一个列表 accounts，每个元素 accounts[i] 是一个字符串列表，其中第一个元素 accounts[i][0] 是 名称 (name)，其余元素是 emails 表示该账户的邮箱地址。
 *
 * 现在，我们想合并这些账户。如果两个账户都有一些共同的邮箱地址，则两个账户必定属于同一个人。请注意，即使两个账户具有相同的名称，它们也可能属于不同的人，因为人们可能具有相同的名称。一个人最初可以拥有任意数量的账户，但其所有账户都具有相同的名称。
 *
 * 合并账户后，按以下格式返回账户：每个账户的第一个元素是名称，其余元素是 按字符 ASCII 顺序排列 的邮箱地址。账户本身可以以 任意顺序 返回。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/accounts-merge
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 注意：
 *  1.相同name名称，并不一定是同一个人，这个条件不能用
 *  2.只能用email是否相同判断，这里可以使用集合操作的并，交，补
 *  3.发现是同一个人后，合并，name无顺序要求，同一个人下的email要求按照ASCII码排序（集合数据结构自动按照ASCII排序）
 *
 *  4.这个狗日的题难点在于删除集合元素过程中不影响循环！！！！！！！！！！
 *
 * 解：老样子先简单实现，思路如下
 *  1.创建一个新的list用于存放最终结果
 *  2.for整个入参大集合，取出第一个元素和所有的后面的集合做交集，判断是否有至少一个交集对象
 *  3.如果有合并，并且继续向下合并，直到当前的元素与集合其他所有元素都做过交集判断，生成的对象存入list最终结果集合
 *
 * @author xyh
 * @date 2022-08-17 3:04 下午
 **/
public class AccountsMerge {

    public static void main(String[] args) {


        List l1 = new ArrayList();

        List l2 = new ArrayList();
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        List<List<String>> result = null;

        int size = accounts.size();

        for (int i = 0; i < size; i++) {
            //  ps:{"John", "johnsmith@mail.com", "john00@mail.com"}
            List<String> l1 = accounts.get(i);
            String name1 = l1.get(0);
            l1.remove(0);
            Set set1 = l1.stream().collect(Collectors.toSet());

            if ((i + 1) < size) {
                // 还有下一个list
                for (int j = i + 1; j < size; j++) {
                    List<String> l2 = accounts.get(j);
                    l2.remove(0);
                    Set set2 = l2.stream().collect(Collectors.toSet());
                    boolean tag = set1.retainAll(set2);
                    if (tag) {
                        // 存在一样的email，做合并
                        set1.addAll(set2);
                        l1 = (List<String>) set1.stream().map(String::valueOf).collect(Collectors.toList());
                    }
                }

            } else {
                List<String> ne = new ArrayList<>();
                ne.addAll(l1);
                result.add(ne);
            }
        }

        return result;
    }

}
