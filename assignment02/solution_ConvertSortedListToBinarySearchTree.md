# Solution: ConvertSortedListToBinarySearchTree

## Bug Found and Fix

After manually checking the README examples and additional edge cases, no functional bug was found in the current implementation.

The algorithm:

- copies list values into an array-like structure
- recursively picks the middle element as root
- builds left and right subtrees from left/right slices

This produces a height-balanced BST and preserves sorted order in an in-order traversal.

## Task 2: Contracts

### Pre-conditions

Based on the README constraints:

- `head` is either `null` or a valid finite singly linked list
- list length is in `[0, 2 * 10^4]`
- node values are in `[-10^5, 10^5]`
- input list is sorted in non-decreasing order

### Post-conditions

- returned value is `null` iff input list is empty
- in-order traversal of the returned tree equals the input sequence
- returned tree contains exactly as many nodes as the input list
- returned tree is height-balanced (`|height(left) - height(right)| <= 1` for every node)

### Frame Conditions

- input linked list is not modified by `sortedListToBST`

## Task 3: Testing the Contracts

The test suite checks:

- **Example behavior**:
  - README example tree shape
  - empty input
  - single-element input
  - two-element midpoint behavior

- **Post-condition checks**:
  - in-order traversal equals original sorted input
  - node count is preserved
  - resulting tree is height-balanced
  - input list remains unchanged after conversion

No pre-condition exception tests are added here because this implementation follows the LeetCode-style contract and does not enforce argument validation with explicit exceptions.

## Task 4: Property-Based Testing

Using jqwik with a custom `@Provide` generator:

- generate integer lists in `[-100000, 100000]`
- size range `[0, 100]`
- sort generated lists to satisfy the method pre-condition

Checked property (100 tries):

1. **Contract preservation on random valid inputs**
   - in-order traversal equals input list
   - node count equals input size
   - resulting tree is height-balanced

## Coverage

JaCoCo results after running `mvn test` in `5-ConvertSortedListToBinarySearchTree`:

| Metric       | Coverage        |
| ------------ | --------------- |
| Instructions | 35.04% (82/234) |
| Branches     | 28.57% (4/14)   |
| Lines        | 28.85% (15/52)  |
| Methods      | 41.67% (5/12)   |

These module-level percentages are lowered by scaffolding classes (`Main`, constructors in `ListNode` and `TreeNode`) that are not fully exercised by tests.

For the algorithm class `ConvertSortedListToBinarySearchTree` itself, coverage is complete:

- Instructions: 100% (70/70)
- Branches: 100% (4/4)
- Lines: 100% (13/13)
- Methods: 100% (3/3)
