# Solution for Excel Sheet Column Number

## 1. Specification-Based Testing

### Step 1: Understanding the requirements

- The method converts an Excel column title (like "A", "AA", "ZY") into its corresponding column number. 
- The mapping is base-26 where 'A' = 1, 'B' = 2, ..., 'Z' = 26, "AA" = 27, and so on. 
- Null, empty, or non-uppercase inputs should throw an "IllegalArgumentException". 
- The maximum valid input is "FXSHRXW", which equals "Integer.MAX_VALUE" (2147483647).

### Step 2: Exploring the program

We tried "A" and got 0 instead of 1. Then "Z" gave 25 instead of 26, and "AA" gave 26 instead of 27. Every result was off by exactly 1 per digit, which showed that the `c - 'A'` expression had a bug. It starts counting from 0 but should start from 1.

### Step 3: Identifying the partitions

Partitions according to the Input string:
- null -> must throw exception
- Empty string -> must throw exception
- Contains invalid characters (not A-Z) -> must throw exception
- Single letter
- Multiple letters

Partitions according to single letter values:
- "A" -> boundary. First valid input that should map to 1
- "Z" -> boundary. Last single-letter that should map to 26
- Any letter in between

Partitions according to multi-letter values:
- Two letters (for example "AA", "ZZ")
- Three or more letters
- Maximum input "FXSHRXW"

Boundaries:
- "A" (first) and "Z" (last) single-letter columns
- "AA" -> the transition from single to double letters
- "ZZ" -> last column of two letters 
- Maximum allowed value

### Step 4: Analyzing the boundaries

The main boundary is between "Z" (26) and "AA" (27), where with a single letter end and columns with a double letter begin. It's also worth testing "A" explicitly because it directly reveals the bug.

### Step 5 : Test cases

| Test Case | Input | Expected   | Why                                                           |
|-----------|-----|------------|---------------------------------------------------------------|
| T1        | null | exception  | null input                                                    |
| T2        | ""  | exception  | empty input                                                   |
| T3        | "A1" | exception  | invalid character (digit)                                     |
| T4        | "a" | exception  | lowercase not allowed                                         |
| T5        | "A" | 1          | first column, directly exposes the bug                        |
| T6        | "Z" | 26         | last single-letter column. Also reveals the bug               |
| T7        | "AA" | 27         | boundary between single and double letters. Also reveals the bug |
| T8        | "AB" | 28         | second two-letter column                                      |
| T9        | "ZZ" | 702        | last two-letter column                                        |
| T10       | "ZY" | 701        | README example                                                |
| T11       | "B" | 2          | Sinlge letter in mid-range                         |
| T12       | "AAA" | 703        | three-letter column, checks accumulation                      |
| T13       | "FXSHRXW" | 2147483647 | maximum allowed input                                         |

### Step 6: Automating the tests

See "ExcelSheetColumnNumberTest.java"

### Step 7 : Extra checks

Two structural tests were added for the invalid character boundary. One with `'@'` (just below 'A' in ASCII format) and one with '[' (just above 'Z' in ASCII format), to explicitly cover both sides of the character validation check.

### Bug found

The expression "c - 'A'" gave 0 for 'A', 1 for 'B', etc. But in Excel, 'A' maps to 1, not 0. So every result was shifted down by one for each letter processed.

The test that caught it is "singleA" as "titleToNumber("A")" returned 0 instead of 1.

To fix it, we added "+ 1" to the character conversion:

```java
// before
result = result * 26 + (c - 'A');

// after
result = result * 26 + (c - 'A' + 1);
```

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric | Result |
|--------|--------|
| Instruction coverage | 94% (3 instructions missed) |
| **Branch coverage** | **100%** |
| Line coverage | 9/10 |
| Method coverage | 1/2 missed |

Branch coverage is 100%. The missed line and method is the default constructor, never called because all methods are static, so not worth testing.
The conditions and how they are covered:

| Condition | Covered by |
|-----------|-----------|
| "columnTitle == null \|\| columnTitle.isEmpty()" (true) | T1, T2 |
| "columnTitle == null \|\| columnTitle.isEmpty()" (false) | all other tests |
| "c < 'A' \|\| c > 'Z'" (true, c < 'A') | characterJustBelowA |
| "c < 'A' \|\| c > 'Z'" (true, c > 'Z') | characterJustAboveZ |
| "c < 'A' \|\| c > 'Z'" (false) | T5 through T13 |
| loop runs once | T5, T6, T11 |
| loop runs multiple times | T7, T8, T9, T10, T12, T13 |

Two structural tests were added to cover both sides of the character validation:
- "characterJustBelowA": input "@" —> ASCII 64, one below 'A'
- "characterJustAboveZ": input "[" —> ASCII 91, one above 'Z'

## 3. Mutation Testing

After running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") we obtained:
- Generated 13 mutations —> Killed 13 (100%)
- Test strength 100%

| Mutator | Generated | Killed | Survived |
|---------|-----------|--------|----------|
| ConditionalsBoundaryMutator | 3 | 3 | 0 |
| NegateConditionalsMutator | 5 | 5 | 0 |
| MathMutator | 4 | 4 | 0 |
| PrimitiveReturnsMutator | 1 | 1 | 0 |

All 13 mutants were killed, so there were no survivors. The test suite is therefore strong enough to catch every mutation PIT could generate.