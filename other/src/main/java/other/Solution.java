package other;

import java.math.BigDecimal;
import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {

    public static void main(String[] args) {
//        int[] list1 = new int[]{9};
//        ListNode node = new ListNode(list1[0]);
//        ListNode tempNode = node;
//        for (int i = 1; i < list1.length; i++) {
//            tempNode.next = new ListNode(list1[i]);
//            tempNode = tempNode.next;
//        }
//        int[] list2 = new int[]{1,9,9,9,9,9,9,9,9,9};
//        ListNode node2 = new ListNode(list2[0]);
//        tempNode = node2;
//        for (int i = 1; i < list2.length; i++) {
//            tempNode.next = new ListNode(list2[i]);
//            tempNode = tempNode.next;
//        }
//        ListNode s = new Solution().addTwoNumbers(node, node2);


        int[] list3= new int[]{1,2,3,4,5,7};
        ListNode node3 = new ListNode(list3[0]);
        ListNode tempNode = node3;
        for (int i = 1; i < list3.length; i++) {
            tempNode.next = new ListNode(list3[i]);
            tempNode = tempNode.next;
        }
        ListNode l = new Solution().reverseKGroup(node3, 3);
        while (l != null){
            System.out.println(l.val);
            l = l.next;
        }
    }

    /**
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     * k 是一个正整数，它的值小于或等于链表的长度。
     * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序
     *示例 :
     * 给定这个链表：1->2->3->4->5
     * 当 k = 2 时，应当返回: 2->1->4->3->5
     * 当 k = 3 时，应当返回: 3->2->1->4->5
     * 说明 :
     * 你的算法只能使用常数的额外空间。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dump=new ListNode(0);
        dump.next = head;
        //用来标记当前反转的起始节点和结束节点
        ListNode pre = dump;
        ListNode end = dump;
        while (end.next != null){
            //遍历剩下长度是不是有k个
            int tempK = k;
            for (; tempK>0 && end.next != null; tempK--) {
                end = end.next;
            }
            //剩下的节点不够反转长度中断
            if(tempK > 0)
                break;
            ListNode over = end.next;
            end.next = null;

            /**
             * 节点反转
             *  当前节点cur为1
             *  临时最前面的节点为1
             *  1-2-3-4-5-6-7
             *  第一次，1和2交换
             *  当前节点cur为1,next指向3
             *  临时最前面的节点为2
             *  2-1-3-4-5-6-7
             *  第二次，1和3交换
             *  3-2-1-4-5-6-7
             *  当前节点cur为1,next指向4
             *  临时最前面的节点为3
             *  N次到
             *  6-5-4-3-2-1-7
             *  此时，1和7交换，7的next是null，
             *  所以交换后终止，即本次交换终结
             */
            ListNode tempre = pre.next;
            ListNode cur = pre.next;
            while (cur.next != null){
                    ListNode n = cur.next;
                    ListNode m = n.next;
                    n.next = tempre;
                    cur.next = m;
                    tempre = n;
            }
            //重置起始的指向反转后的头结点
            pre.next = tempre;
            //重置反转后的尾节点指向反转的节点
            cur.next =over;
            //本次k个节点反转完成，初始化下一阶段的节点
            pre = cur;
            end = cur;
        }
        return dump.next;
    }
    /**
     * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
     * 输入:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 输出: 1->1->2->3->4->4->5->6
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        for (int i = 0; i < lists.length; i++) {

        }
        return null;
    }
    /**
     * 两个单向链表相加
     * 1->2->3
     * 3->9
     * 4->1>4
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode node = new ListNode(0);
        ListNode temp0 = node,temp1 = l1,temp2 = l2;
        int sum = 0;
        for (;temp1 != null || temp2 != null;){
            int val1 = temp1 == null ? 0 : temp1.val;
            int val2 = temp2 == null ? 0 : temp2.val;
            sum += (val1 + val2);
            temp0.next = new ListNode(sum%10);
            temp0 = temp0.next;
            sum = sum/10;
            temp1 = temp1 == null ? null :temp1.next;
            temp2 = temp2 == null ? null :temp2.next;
        }
        if(sum != 0){
            temp0.next = new ListNode(sum);
        }
        return node.next;
    }

    public ListNode reserveNode(ListNode node){

        return null;
    }

    static class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}