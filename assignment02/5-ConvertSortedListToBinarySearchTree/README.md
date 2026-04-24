# Problem: Convert Sorted List to Binary Search Tree

## Description

Given the `head` of a singly linked list where elements are sorted in ascending order, convert it to a height-balanced binary search tree.

### Constraints:
- The number of nodes in the linked list is in the range `[0, 2 * 10^4]`.
- Each node's value will be in the range `[-10^5, 10^5]`.

### Example 1:
**Input**: `head = [-10, -3, 0, 5, 9]`
**Output**: `[0,-10,5,null,-3,null,9]`

```
      0
     / \
   -10  5
     \   \
     -3   9
```

Explanation: The array represents the level order traversal of a BST with root at index 0. The null values indicates no left child for the nodes with value -10 and 5.
Note that another valid output would be `[0,-10,5,null,-3,null,9]`. See the following image.

![Image Explanation](https://assets.leetcode.com/uploads/2020/08/17/linked.jpg)


### Example 2:

**Input**: `head = []`
**Output**: `[]`

### Example 3:

**Input**: `head = [0]`
**Output**: `[0]`
