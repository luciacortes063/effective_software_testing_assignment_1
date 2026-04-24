# Problem: Merge Intervals

## Description

Given an array of intervals where each interval is represented as `[start, end]`, merge all overlapping intervals and return an array of the non-overlapping intervals that cover all the intervals in the input.

### Constraints:
- The number of intervals will be in the range `[0, 10^4]`.
- Each interval will be in the form `[start, end]` where `start <= end`.
- `intervals[i].length == 2`

### Example 1:

**Input**: `intervals = [[1,3],[2,6],[8,10],[15,18]]`
**Output**: `[[1,6],[8,10],[15,18]]`

Explanation: The intervals `[1,3]` and `[2,6]` overlap, so they are merged into `[1,6]`.

### Example 2:

**Input**: `intervals = [[1,4],[4,5]]`
**Output**: `[[1,5]]`

Explanation: The intervals `[1,4]` and `[4,5]` are merged into `[1,5]`.
