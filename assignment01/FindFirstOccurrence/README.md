## Problem: Find First Occurrence

# Description
The Find First Occurrence problem requires determining whether a given substring appears within another string. The task is to return the index of the first occurrence of the string needle in the string haystack.

If needle does not occur in haystack, the method should return -1.

The solution should manually search for the substring without relying on built-in search functions.

Inputs:

**haystack — the string in which the search is performed.

**needle — the substring to search for.

Constraints:

**1 ≤ haystack.length ≤ 10^4

**1 ≤ needle.length ≤ 10^4

**Both strings consist only of lowercase English letters.

Output:

**An integer representing the index of the first occurrence of needle in haystack.

**If needle is found, return the starting index.

**If needle is not found, return -1.

Behavior:

**The method should iterate through the haystack string and compare characters to determine whether needle appears starting at each possible index.

**The method should handle edge cases such as needle appearing at the beginning of haystack, at the end of haystack, multiple occurrences of needle, needle being longer than haystack, needle equal to haystack, and single-character strings.


#Example

**Input: haystack = "sadbutsad"
**Output: needle = "sad"