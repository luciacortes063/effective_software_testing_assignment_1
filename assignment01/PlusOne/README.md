## Problem: Plus One

# Description
The Plus One problem requires incrementing a large integer represented as an array of digits. Each element in the array corresponds to a single digit of the integer, ordered from most significant to least significant (left to right). The integer does not contain any leading zeroes.

The task is to increment the represented integer by one and return the resulting array of digits.

The solution must correctly handle carry propagation across digits.

Inputs:

**An integer array digits representing a non-negative integer.

**1 ≤ digits.length ≤ 100

**0 ≤ digits[i] ≤ 9

**The array does not contain leading zeroes.

Output:

**An integer array representing the incremented value of the original number.

**The result may have a different length if a carry creates an additional digit (e.g., 999 + 1 = 1000).

Behavior:

**The method should simulate addition by one, starting from the least significant digit and handling carry as needed.

**The method should correctly handle edge cases such as no carry, carry affecting only the last digit, carry propagating through multiple digits, and all digits being 9.

#Example

**Input: digits = [1,2,3]

**Output:  [1,2,4]