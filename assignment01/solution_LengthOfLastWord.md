# Solution for Length of Last Word

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The method receives a string consisting of English letters and spaces and returns the length of the last word. A word is a maximal substring of non-space characters. The string has at least one word. The Javadoc contract specifies that null input throws an IllegalArgumentException.

### Step 2 – Exploring the program

We ran the README example ("Hello World") and got 5, which is correct. We also tried a string with trailing spaces ("Hello World   ") and got 5, confirming that trailing spaces are handled.

### Step 3 – Identifying the partitions

Partitions according to the structure of the string:
- Single word, no spaces
- Multiple words separated by single spaces
- Trailing spaces after the last word
- Leading spaces before the first word
- Multiple consecutive spaces between words

Partitions according to word length:
- Single-character last word
- Multi-character last word

Special cases:
- null input (from Javadoc contract)
- Word at the very start of the string followed only by spaces

### Step 4 – Boundaries

The boundary is between the trailing spaces and the last word. A string like "word    " tests the transition from spaces to the word when scanning from right to left. Another boundary is when the last word starts at index 0 of the string, so the backward scan hits the beginning of the string.

### Step 5 – Test cases

| ID  | Input | Expected | Why |
|-----|-------|----------|-----|
| T1  | null  | exception | null input |
| T2  | "Hello" | 5 | single word |
| T3  | "Hello World" | 5 | README example |
| T4  | "Hello World   " | 5 | trailing spaces |
| T5  | "   Hello" | 5 | leading spaces |
| T6  | "Hello   World" | 5 | multiple spaces between words |
| T7  | "a" | 1 | single character |
| T8  | "  a  " | 1 | single char with spaces |
| T9  | "fly me to the moon" | 4 | many words |
| T10 | "word    " | 4 | word then trailing spaces |
| T11 | "a verylongword" | 12 | long last word |

### Step 6 – Automating the tests

See LengthOfLastWordTest.java.

### Step 7 – Extra checks

One structural test was added: "wordAtVeryStart" with "abc     " to ensure the backward scan covers the case where the word starts at the very beginning of the string (index 0), forcing the second while loop to exit because i becomes negative.

### Bug found

No bug was found. All tests passed on the first run.

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric | Result |
|--------|--------|
| Instruction coverage | 92% (3 instructions missed) |
| **Branch coverage** | **90% (9/10)** |
| Line coverage | 10/11 |
| Method coverage | 1/2 missed |

The 1 missed branch is on line 22: the `i >= 0` condition being false in the trailing-space skip loop. This would only happen with a string consisting entirely of spaces, which violates the specification constraint "There is at least one word in the string." Since this is outside the valid input domain, there is no point writing a test for it.

The missed line and method is the default constructor.

The conditions covered are:

| Condition | Covered by |
|-----------|-----------|
| `s == null` (true) | T1 |
| `s == null` (false) | T2-T11 |
| `i >= 0` in trailing-space loop (true) | T4, T8, T10, wordAtVeryStart |
| `s.charAt(i) == ' '` (true) | T4, T8, T10 |
| `s.charAt(i) == ' '` (false, exits loop) | T2, T3, T7, T9 |
| `i >= 0` in word-count loop (true → false) | T2, T7, wordAtVeryStart |
| `s.charAt(i) != ' '` (true) | T2, T3, T7, T9 |
| `s.charAt(i) != ' '` (false, exits loop) | T3, T4, T5, T6 |

## 3. Mutation Testing

Running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave:

```
Generated 10 mutations — Killed 9 (90%)
Test strength 90%
```

### Surviving mutant

The surviving mutant is on line 22 (ConditionalsBoundaryMutator): `i >= 0` changed to `i > 0` in the trailing-space skip loop.

This is an equivalent mutant. When `i` reaches 0 in the trailing-space loop, the character at index 0 must be a space (otherwise the `charAt(i) == ' '` condition would have already caused the loop to exit). With the original code (`i >= 0`), `i` decrements to -1, then the word-count loop does not enter because `i >= 0` is false. With the mutant (`i > 0`), the loop exits with `i = 0`, then the word-count loop checks `charAt(0) != ' '` which is false (it's a space), so it also does not enter. Both produce `length = 0`. Since no input can distinguish the original from the mutant, this is an equivalent mutant and not worth writing a test for.
