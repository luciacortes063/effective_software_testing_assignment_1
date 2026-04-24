# Problem: Min Cost Climbing Stairs

## Description

You are given an integer array `cost` where `cost[i]` is the cost of the ith step on a staircase. Once you pay the cost, you can either climb one or two steps. You can start from the step with index 0, or the step with index 1. Your goal is to return the minimum cost to reach the top of the floor.

### Constraints:
- The length of the `cost` array will be in the range `[2, 1000]`.
- Each element in the `cost` array will be in the range `[0, 999]`.

### Example 1:

**Input**: `cost = [10, 15, 20]`
**Output**: `15`

Explanation: You will start at step 1 (cost 15), then jump to the top. Total cost = 15.

### Example 2:

**Input**: `cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]`
**Output**: `6`

Explanation: You will start at step 0 (cost 1), then step 2 (cost 1), then step 4 (cost 1), then step 6 (cost 1), then step 7 (cost 1), and finally step 9 (cost 1), after which you reach the top. Total cost = 6.
