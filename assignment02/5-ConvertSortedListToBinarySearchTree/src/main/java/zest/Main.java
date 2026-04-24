package zest;

import java.util.*;

public class Main {
    public static void printBFSTraversal(TreeNode root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }

        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        java.util.List<String> result = new java.util.ArrayList<>();

        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node == null) {
                result.add("null");
            } else {
                result.add(String.valueOf(node.val));
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        // Trim trailing nulls
        int i = result.size() - 1;
        while (i >= 0 && result.get(i).equals("null")) {
            result.remove(i--);
        }

        System.out.println("[" + String.join(",", result) + "]");
    }

    public static void main(String[] args) {
        ConvertSortedListToBinarySearchTree solution = new ConvertSortedListToBinarySearchTree();
        ListNode head = new ListNode(-10);
        head.next = new ListNode(-3);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(5);
        head.next.next.next.next = new ListNode(9);
        TreeNode root = solution.sortedListToBST(head);

        printBFSTraversal(root);
    }
}
