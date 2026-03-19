## Problem: Sort Colors

# Description
The Sort Colors problem requires sorting an array containing three distinct values representing colors. The values are:

0 → Red

1 → White

2 → Blue

The task is to sort the array in-place so that all elements of the same color are adjacent, and the colors appear in the order: red (0), white (1), blue (2).

You must solve this problem without using the library's built-in sorting function.

Inputs:

**An integer array nums.

Constraints:

**n == nums.length

**1 ≤ n ≤ 300

**nums[i] is either 0, 1, or 2.

Output:

**The input array should be modified in-place so that it is sorted in the order 0s, then 1s, then 2s.

**The method does not need to return anything, but the array must be correctly rearranged.

Behavior:

**The method should rearrange elements in-place without using extra arrays for sorting.

**The method should handle edge cases such as an already sorted array, a reverse sorted array, an array containing only one color, an array containing only two of the three colors, a single-element array, arrays with alternating color patterns

#Example

**Input: nums = [2,0,2,1,1,0]

**Output:  [0,0,1,1,2,2]