## Problem: Valid Perfect Square

# Description
The Valid Perfect Square problem requires determining whether a given positive integer is a perfect square.

A perfect square is an integer that can be expressed as the product of an integer multiplied by itself. In other words, a number num is a perfect square if there exists an integer x such that: x * x = num

Inputs:

**A positive integer num.
**Constraints: 1 ≤ num ≤ 2^31 - 1

Output:

**Return true if num is a perfect square. Otherwise, return false.

Behavior:

**The method should determine whether there exists an integer whose square equals num.

**The method must correctly handle boundary values, be efficient enough to handle large inputs within constraints.

**The method should handle edge cases such as num equals to 1, Small perfect squares (e.g., 4, 9, 16), Large perfect squares within integer range, Large non-perfect squares, Numbers just below or above perfect squares

#Example

**Input: num = 16

**Output: true