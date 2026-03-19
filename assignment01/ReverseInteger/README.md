## Problem: Reverse Integer

# Description
The Reverse Integer problem requires reversing the digits of a signed 32-bit integer. Given an integer x, return the integer obtained by reversing its digits.

If reversing x causes the result to fall outside the signed 32-bit integer range [-2^31, 2^31 - 1], the method must return 0.

The solution must not use 64-bit integers (e.g., long) to handle overflow detection.

Inputs:

**A signed 32-bit integer x.

Constraints:

**-2^31 ≤ x ≤ 2^31 - 1

Output:

**An integer representing the reversed value of x.  If reversing the digits causes overflow beyond the 32-bit signed integer range, return 0.

Behavior:

**The method should reverse the digits of the integer while preserving the sign.

**If the reversed integer exceeds the valid 32-bit signed integer range, the method must detect this condition and return 0.

**The method should handle edge cases such as Positive integers, negative integers, integers ending in zero, single-digit integers, and the maximum and minimum 32-bit integer values.

#Example

**Input: x = 123

**Output:  321