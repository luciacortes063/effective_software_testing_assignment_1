# Solution for Find First Occurrence

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The method receives two strings, haystack and needle, and returns the index of the first occurrence of needle in haystack. If needle is not found, it returns -1. Both strings consist of lowercase English letters, with lengths between 1 and 10^4. The Javadoc contract also specifies that null inputs must throw an IllegalArgumentException.

### Step 2 – Exploring the program

We ran the README example ("sadbutsad", "sad") and got 0, which is correct. We also tried a case where needle is not present ("hello", "xyz") and got -1. The program seems to work correctly for basic cases.

### Step 3 – Identifying the partitions

Partitions according to the position of needle in haystack:
- needle at the start
- needle in the middle
- needle at the end
- needle not present at all

Partitions according to the size relationship:
- needle longer than haystack
- needle equals haystack (same string)
- needle shorter than haystack

Partitions according to string length:
- Single-character strings
- Multi-character strings

Special cases:
- null haystack or null needle (from Javadoc contract)
- Multiple occurrences of needle (must return first)
- Partial match at one position followed by a full match later

### Step 4 – Boundaries

The main boundary is between "found" and "not found." The trickiest case is a partial match: "aab" contains "ab" starting at index 1, not 0, because the first 'a' starts a match that fails at the second character.

Another boundary is when needle is exactly as long as haystack: the only possible match is at index 0.

### Step 5 – Test cases

| ID  | haystack | needle | Expected | Why |
|-----|----------|--------|----------|-----|
| T1  | null     | "abc"  | exception | null haystack |
| T2  | "abc"    | null   | exception | null needle |
| T3  | "sadbutsad" | "sad" | 0 | README example, needle at start |
| T4  | "butsad" | "sad"  | 3 | needle at end |
| T5  | "abcdef" | "cde"  | 2 | needle in middle |
| T6  | "hello"  | "xyz"  | -1 | needle not found |
| T7  | "ab"     | "abcd" | -1 | needle longer than haystack |
| T8  | "abc"    | "abc"  | 0 | needle equals haystack |
| T9  | "a"      | "a"    | 0 | single char, found |
| T10 | "a"      | "b"    | -1 | single char, not found |
| T11 | "abcabc" | "abc"  | 0 | multiple occurrences, returns first |
| T12 | "aab"    | "ab"   | 1 | partial match then full match |

### Step 6 – Automating the tests

See FindFirstOccurrenceTest.java.

### Step 7 – Extra checks

One structural test was added: "mismatchInMiddleOfNeedle" with ("abcxyz", "abcz") to explicitly cover the case where the inner while loop breaks due to a character mismatch partway through the needle. This ensures both exit conditions of the inner while loop are covered.

### Bug found

No bug was found. All tests passed on the first run.

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric | Result |
|--------|--------|
| Instruction coverage | 95% (3 instructions missed) |
| **Branch coverage** | **100% (14/14)** |
| Line coverage | 13/14 |
| Method coverage | 1/2 missed |

Branch coverage is 100%. The missed line and method is the default constructor, which is never called because all methods are static.

The conditions covered are:

| Condition | Covered by |
|-----------|-----------|
| `haystack == null \|\| needle == null` (true, first operand) | T1 |
| `haystack == null \|\| needle == null` (true, second operand) | T2 |
| `haystack == null \|\| needle == null` (false) | T3-T12 |
| `nLen > hLen` (true) | T7 |
| `nLen > hLen` (false) | T3-T6, T8-T12 |
| `j < nLen && chars match` (true, loop continues) | T3, T4, T5, T12 |
| `j < nLen && chars match` (false, char mismatch) | T6, T10, mismatchInMiddleOfNeedle |
| `j == nLen` (true) | T3, T4, T5, T8, T9, T11 |
| `j == nLen` (false) | T6, T10 |

No additional tests were needed beyond the one structural test added.

## 3. Mutation Testing

Running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave:

```
Generated 15 mutations — Killed 15 (100%)
Test strength 100%
```

No mutants survived, so no additional tests were needed.
