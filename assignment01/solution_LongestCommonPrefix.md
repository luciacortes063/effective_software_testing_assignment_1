# Solution for Longest Common Prefix

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The method receives an array of strings and returns the longest common prefix shared by all strings. If no common prefix exists, it returns "". The array contains 1 to 200 strings, each 0 to 200 characters, consisting of lowercase English letters. The Javadoc contract specifies that a null array throws an IllegalArgumentException.

### Step 2 – Exploring the program

We ran the README example (["flower","flow","flight"]) and got "fl", which is correct. We also tried strings with no common prefix (["dog","racecar","car"]) and got "", which is also correct.

### Step 3 – Identifying the partitions

Partitions according to prefix length:
- No common prefix at all (first characters differ)
- Prefix is a single character
- Prefix is a full string (one string is a prefix of all others)
- All strings are identical (prefix is the whole string)

Partitions according to array structure:
- null array (from Javadoc contract)
- Empty array
- Single string
- Two strings
- Multiple strings

Special cases:
- One of the strings is empty (""), forcing the prefix to be ""
- First string is the shortest vs the longest

### Step 4 – Boundaries

The main boundary is between "some common prefix" and "no common prefix." The simplest boundary case is when the first characters of two strings differ, forcing the prefix to shrink to "".

Another boundary is when one string in the array is empty: since `startsWith("")` always returns true, the prefix shrinks to "" when processing that string.

### Step 5 – Test cases

| ID  | Input | Expected | Why |
|-----|-------|----------|-----|
| T1  | null  | exception | null input |
| T2  | []    | "" | empty array |
| T3  | ["abc"] | "abc" | single string |
| T4  | ["flower","flow","flight"] | "fl" | README example |
| T5  | ["dog","racecar","car"] | "" | no common prefix |
| T6  | ["abc","abc","abc"] | "abc" | all identical |
| T7  | ["","abc","ab"] | "" | one empty string |
| T8  | ["inter","interview"] | "inter" | one is prefix of other |
| T9  | ["a","ab","abc"] | "a" | prefix is one char |
| T10 | ["ab","abc","abcd"] | "ab" | first string is shortest |
| T11 | ["abcd","abc","ab"] | "ab" | first string is longest |

### Step 6 – Automating the tests

See LongestCommonPrefixTest.java.

### Step 7 – Extra checks

One structural test was added: "emptyStringInMiddle" with ["abc","","ab"] to ensure the prefix shrinks to "" when the empty string is not at the beginning of the array but in the middle.

### Bug found

No bug was found. All tests passed on the first run.

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric | Result |
|--------|--------|
| Instruction coverage | 90% (5 instructions missed) |
| **Branch coverage** | **90% (9/10)** |
| Line coverage | 10/12 |
| Method coverage | 1/2 missed |

The 1 missed branch is on line 26: the `prefix.isEmpty()` condition being true. This code is actually unreachable dead code. Here is why: the `prefix.isEmpty()` check is inside the while loop with condition `!strs[i].startsWith(prefix)`. When the prefix becomes empty (""), `strs[i].startsWith("")` always returns true, so the while loop exits before the `prefix.isEmpty()` check is reached. Therefore, the true branch of `prefix.isEmpty()` can never execute.

The missed lines are line 27 (`return ""` inside the dead code block) and the default constructor.

The conditions covered are:

| Condition | Covered by |
|-----------|-----------|
| `strs == null` (true) | T1 |
| `strs == null` (false) | T2-T11 |
| `strs.length == 0` (true) | T2 |
| `strs.length == 0` (false) | T3-T11 |
| `!strs[i].startsWith(prefix)` (true, enters loop) | T4, T5, T7, T9, T10, T11 |
| `!strs[i].startsWith(prefix)` (false, skips loop) | T3, T6, T8 |
| `prefix.isEmpty()` (false) | T4, T5, T7, T9, T10, T11, emptyStringInMiddle |

Since the missed branch is unreachable dead code, no additional tests can cover it.

## 3. Mutation Testing

Running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave:

```
Generated 8 mutations — Killed 8 (100%)
Test strength 100%
```

No mutants survived, so no additional tests were needed.
