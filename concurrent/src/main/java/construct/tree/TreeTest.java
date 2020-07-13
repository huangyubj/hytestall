package construct.tree;

import java.util.Arrays;
import java.util.LinkedList;

public class TreeTest {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(new Integer[]{3,2,9,null,null,10,null,null,8,null,4}));
        TreeNode treeNode = create(list);
        preOrdertraveral(treeNode);
    }

    private static void preOrdertraveral(TreeNode node){
        if(node == null)
            return;
        System.out.println(node.data);
        preOrdertraveral(node.left);
        preOrdertraveral(node.right);
    }


    public static TreeNode create(LinkedList<Integer> list){
        if(list == null || list.size() == 0)
            return null;
        TreeNode node = null;
        Integer data = list.removeFirst();
        if(data != null){
            node = new TreeNode(data);
            node.left = create(list);
            node.right = create(list);
        }
        return node;
    }

    private static class TreeNode{
        int data;
        TreeNode left;
        TreeNode right;

        public TreeNode(int data) {
            this.data = data;
        }
    }
}
