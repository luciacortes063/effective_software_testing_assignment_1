## Problem: Length of Last Word

# Description
The Length of Last Word problem requires determining the length of the final word in a given string. The input string consists of words separated by spaces. A word is defined as a maximal substring consisting only of non-space characters.

The task is to return the length of the last word in the string.

The solution should correctly handle leading, trailing, and multiple intermediate spaces.

Inputs:

**A string s consisting of English letters and spaces ' '.

Constraints:

**1 ≤ s.length ≤ 10^4

**The string contains only English letters and spaces.

**There is at least one word in the string.

Output:

**An integer representing the length of the last word in the string.


Behavior:

**The method should examine the string and determine the length of the last word without modifying the original string.

**The method should handle edge cases such as Strings with trailing spaces, leading spaces, multiple consecutive spaces, and Strings containing only one word.


#Example

**Input: s = "Hello World"
**Output: 5