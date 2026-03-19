## Problem: Compare Version Numbers

# Description
The Compare Version Numbers problem requires comparing two software version strings. A version string consists of numeric revisions separated by dots (.). Each revision should be interpreted as an integer, ignoring any leading zeros.

To compare two version strings, compare their corresponding revision numbers from left to right.

If a revision in one version is greater than the corresponding revision in the other, that version is considered greater.

If one version has fewer revisions, the missing revisions should be treated as 0.

Inputs:

**Two strings version1 and version2.

Constraints:

**1 ≤ version1.length, version2.length ≤ 500

**Both strings contain only digits (0–9) and dots (.).

**The version strings are valid.

**Each revision value fits within a 32-bit signed integer.

Output:

**An integer indicating the comparison result:

**Return -1 if version1 < version2

**Return 1 if version1 > version2

**Return 0 if both versions are equal

Behavior:

**The method should Split each version string into revisions separated by dots. Convert each revision to an integer, ignoring leading zeros. Compare corresponding revisions from left to right. Treat missing revisions in shorter versions as 0.   
**The method should handle edge cases such as Leading zeros in revisions (e.g., "1.01" vs "1.001"), Different number of revisions (e.g., "1.0" vs "1.0.0.0"), Versions differing at later revisions, Single-revision version strings and Large revision numbers within constraints

#Example

**Input: version1 = "1.01" version2 = "1.001"
**Output: 0