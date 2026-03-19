## Problem: Add Binary

# Description
The Add Binary problem requires computing the sum of two binary numbers represented as strings. Given two binary strings a and b, return their sum as a binary string.

The solution must simulate binary addition and handle carry propagation correctly.

Inputs:

**Two binary strings a and b.

Constraints:

**1 ≤ a.length, b.length ≤ 10^4

**Each string consists only of characters '0' or '1'.

**Each string does not contain leading zeros, except for the number "0" itself.

Output:

**A binary string representing the sum of a and b.

The result must not contain leading zeros unless the result is "0".

Behavior:

**The method should perform binary addition from right to left, similar to manual addition, handling carry values appropriately.

**The method should handle edge cases such as inputs of equal length, inputs of different lengths, carry generated at the most significant bit, no carry generated, multiple consecutive carry propagations, one or both inputs equal to "0", and very long binary strings.

#Example

**Input: a = "11", b = "1"
**Output: "100"