package zest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Group;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

public class ConvertSortedListToBinarySearchTreeTest {
    private final ConvertSortedListToBinarySearchTree solution = new ConvertSortedListToBinarySearchTree();

    // Task 1 Coverage: basic examples and edge cases to exercise core recursion paths.

    @Nested
    class ExampleBasedTests {

        @Test
        void readmeExampleProducesExpectedShape() {
            // README example input
            ListNode head = listOf(-10, -3, 0, 5, 9);

            // Deterministic shape for this implementation (lower-mid split)
            TreeNode root = solution.sortedListToBST(head);
            assertEquals(0, root.val);
            assertEquals(-10, root.left.val);
            assertEquals(5, root.right.val);
            assertNull(root.left.left);
            assertEquals(-3, root.left.right.val);
            assertNull(root.right.left);
            assertEquals(9, root.right.right.val);
        }

        @Test
        void emptyInputReturnsNull() {
            // Empty list should map to an empty tree
            assertNull(solution.sortedListToBST(null));
        }

        @Test
        void singleElementInputReturnsSingleNodeTree() {
            // Single element becomes a single-node tree
            TreeNode root = solution.sortedListToBST(listOf(42));
            assertEquals(42, root.val);
            assertNull(root.left);
            assertNull(root.right);
        }

        @Test
        void twoElementsUseLowerMiddleAsRoot() {
            // For two elements, midpoint formula picks index 0 as root
            TreeNode root = solution.sortedListToBST(listOf(1, 2));

            assertEquals(1, root.val);
            assertNull(root.left);
            assertEquals(2, root.right.val);
            assertNull(root.right.left);
            assertNull(root.right.right);
        }
    }

    // Task 3 Contract tests: post-conditions and frame-condition checks.

    @Nested
    class ContractTests {

        @Test
        void inOrderTraversalMatchesInput() {
            // In-order traversal of BST must reproduce sorted input sequence
            List<Integer> sortedValues = List.of(-5, -1, 0, 2, 8, 11);
            TreeNode root = solution.sortedListToBST(listOf(sortedValues));

            assertEquals(sortedValues, inOrder(root));
        }

        @Test
        void preservesNodeCount() {
            // Conversion should neither lose nor duplicate nodes
            List<Integer> sortedValues = List.of(-3, -3, -1, 0, 0, 7, 12);
            TreeNode root = solution.sortedListToBST(listOf(sortedValues));

            assertEquals(sortedValues.size(), inOrder(root).size());
        }

        @Test
        void resultTreeIsHeightBalanced() {
            // Problem contract requires a height-balanced tree
            List<Integer> sortedValues = new ArrayList<>();
            for (int i = 1; i <= 31; i++) {
                sortedValues.add(i);
            }

            TreeNode root = solution.sortedListToBST(listOf(sortedValues));
            assertTrue(isHeightBalanced(root));
        }

        @Test
        void doesNotModifyInputList() {
            // Input list should be treated as read-only
            ListNode head = listOf(-2, -1, 0, 1, 2);
            List<Integer> before = new ArrayList<>();
            ListNode current = head;
            while (current != null) {
                before.add(current.val);
                current = current.next;
            }

            solution.sortedListToBST(head);

            List<Integer> after = new ArrayList<>();
            current = head;
            while (current != null) {
                after.add(current.val);
                current = current.next;
            }

            assertEquals(before, after);
        }
    }

    // Task 4: Property-based tests with jqwik for broader randomized coverage.

    @Nested
    @Group
    class PropertyBasedTests {

        @Property(tries = 100)
        void inOrderMatchesInputAndTreeRemainsBalanced(@ForAll("sortedIntLists") List<Integer> sortedValues) {
            // For any valid sorted list, key contracts must always hold
            TreeNode root = solution.sortedListToBST(listOf(sortedValues));
            List<Integer> actualInOrder = inOrder(root);

            assertEquals(sortedValues, actualInOrder);
            assertEquals(sortedValues.size(), actualInOrder.size());
            assertTrue(isHeightBalanced(root));
        }

        @Provide
        Arbitrary<List<Integer>> sortedIntLists() {
            // Generate arbitrary integer lists, then sort to satisfy method precondition
            return net.jqwik.api.Arbitraries.integers()
                    .between(-100_000, 100_000)
                    .list()
                    .ofMinSize(0)
                    .ofMaxSize(100)
                    .map(values -> {
                        List<Integer> copy = new ArrayList<>(values);
                        copy.sort(Integer::compareTo);
                        return copy;
                    });
        }
    }

    // Helpers

    private static ListNode listOf(int... values) {
        if (values.length == 0) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int value : values) {
            current.next = new ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }

    private static ListNode listOf(List<Integer> values) {
        return listOf(values.stream().mapToInt(Integer::intValue).toArray());
    }

    private static List<Integer> inOrder(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            TreeNode node = stack.pop();
            out.add(node.val);
            current = node.right;
        }
        return out;
    }

    private static boolean isHeightBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        return Math.abs(leftHeight - rightHeight) <= 1
                && isHeightBalanced(root.left)
                && isHeightBalanced(root.right);
    }

    private static int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
