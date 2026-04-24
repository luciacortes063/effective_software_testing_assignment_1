# Problem: Number of Longest Increasing Subsequence

## Description

Given an integer array `nums`, return the number of longest increasing subsequences. A subsequence is a sequence that can be derived from the array by deleting some or no elements without changing the order of the remaining elements. The subsequence must be strictly increasing.

### Constraints:
- The length of the array `nums` will be in the range `[1, 2000]`.
- Each element in `nums` will be in the range `[-10^6, 10^6]`.

### Example 1:

**Input**: `nums = [1,3,5,4,7]`
**Output**: `2`

Explanation: The two longest increasing subsequences are `[1, 3, 4, 7]` and `[1, 3, 5, 7]`.

### Example 2:

**Input**: `nums = [2,2,2,2,2]`
**Output**: `5`

Explanation: The length of the longest increasing subsequence is `1`, and there are `5` increasing subsequences of length `1`, so output `5`.
