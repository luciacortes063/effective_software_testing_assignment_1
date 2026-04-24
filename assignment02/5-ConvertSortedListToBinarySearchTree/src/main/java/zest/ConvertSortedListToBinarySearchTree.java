package zest;

import java.util.ArrayList;
import java.util.List;

public class ConvertSortedListToBinarySearchTree {
    public TreeNode sortedListToBST(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return sortedArrayToBST(list, 0, list.size() - 1);
    }

    private TreeNode sortedArrayToBST(List<Integer> list, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(list.get(mid));
        root.left = sortedArrayToBST(list, start, mid - 1);
        root.right = sortedArrayToBST(list, mid + 1, end);
        return root;
    }
}
