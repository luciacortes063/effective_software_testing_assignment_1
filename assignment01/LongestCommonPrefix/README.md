## Problem: Longest Common Prefix

# Description
The Longest Common Prefix problem requires determining the longest shared starting substring among a group of strings. The task is to examine all strings in the input array and identify the longest prefix that is common to every string.

If no common prefix exists, the method should return an empty string "".

The solution should handle all valid edge cases and respect the given constraints.

Inputs:

**An array of strings strs.

**The array contains between 1 and 200 strings.

**Each string may have a length between 0 and 200 characters.

**Strings consist only of lowercase English letters if they are non-empty.

Output:

**A string representing the longest common prefix shared among all input strings.

**If no common prefix exists, return "".

Behavior:

**The method should compare the strings and progressively determine the longest prefix common to all elements in the array.

**The method should correctly handle edge cases such as an array containing only one string, empty strings, strings of different lengths, and strings that are identical.


#Example

**Input: numbers = ["flower","flow","flight"]

**Output:  "fl"