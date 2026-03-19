## Problem: Best Time to Buy and Sell Stock

# Description
The Excel Sheet Column Number problem requires converting a column title, as used in Microsoft Excel, into its corresponding numerical column index.

In Excel, columns are labeled using uppercase letters in a base-26 format:

"A" → 1

"B" → 2

...

"Z" → 26

"AA" → 27

"AB" → 28

...

Given a string columnTitle representing an Excel column title, return its corresponding column number.

Inputs:

**A string columnTitle representing a valid Excel column title.

Constraints:

**1 ≤ columnTitle.length ≤ 7

**columnTitle consists only of uppercase English letters ('A' to 'Z').

**columnTitle is within the valid range ["A", "FXSHRXW"].

Output:

**An integer representing the corresponding column number.

Behavior:

**The method should interpret the column title as a base-26 number, where:

'A' corresponds to 1

'B' corresponds to 2

...

'Z' corresponds to 26

**The method should handle edge cases such as single-character inputs, multiple-character inputs, maximum allowed column titles, boundary values such as "A", "Z", "AA", and "ZZ", and very large valid column titles within constraints.

#Example

**Input: columnTitle = "ZY"
**Output: 701