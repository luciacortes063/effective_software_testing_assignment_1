# Solution for Add Binary

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The method takes two binary strings a and b and returns their sum as a binary string. Both inputs must be non-null (otherwise an IllegalArgumentException is thrown), and the result should not have leading zeros unless it is "0". The addition works right to left, just like manual arithmetic, propagating a carry when two bits sum to 2 or more.

### Step 2 – Exploring the program

First we ran the example from the README ("11" + "1") and got "00" instead of "100". We also tried "1" + "1" and got "0" instead of "10". This showed that something was wrong with how the carry was being handled at the end of the addition.

### Step 3 – Identifying the partitions

Partitions according to the input "a" (same applies to "b"):
- null —> must throw an exception
- "0" —> edge case, adding zero
- Single "1" -> the smallest positive value
- Multi-character string

Partitions according to the relationship between "a" and "b":
- Same length
- "a" longer than "b"
- "b" longer than "a"

Partitions according to the Carry behavior:
- No carry
- Carry only on the last bit (which produced a new extra digit)
- Carry in the middle of the addition
- Many carries in a row

### Step 4 – Boundaries

An important boundary is when the sum of the most significant bits produces a carry, so the result ends up with one more digit than either input. The simplest case is "1" + "1" = "10".
The extreme case is something like "1111" + "1" = "10000", where the carry propagates through every single column.

### Step 5 – Test cases

| ID  | a | b | Expected | Why                                                       |
|-----|---|--|----------|-----------------------------------------------------------|
| T1  | null | 1| exception | null a                                                    |
| T2  | 1 | null| exception | null b                                                    |
| T3  | 0 | 0 | 0        | both zero                                                 |
| T4  | 0 | 1 | "1"      | adding zero should return the value of the other variable |
| T5  | "1" | "0" | "1"      | adding zero should return the value of the other variable |
| T6  | "1" | "1" | "10"     | boundary: simplest carry to a new digit —>reveals bug     |
| T7  | "11" | "1" | "100"    | README example —> reveals bug                             |
| T8  | "10" | "11" | "101"    | equal length with carry —> reveals bug                    |
| T9  | "1010" | "11" | "1101"   | a longer than b                                           |
| T10 | "11" | "1010" | "1101"   | b longer than a                                           |
| T11 | "1111" | "1" | "10000"  | carry is spread through every column                      |
| T12 | "111" | "111" | "1110"   | carry in the middle                                       |

### Step 6 – Automating the tests

See AddBinaryTest.java.

### Step 7 – Extra checks

No additional cases were needed. The partitions already cover the input space, including the tricky carry cases.


### Bug found

The "while" loop exits once there are no more digits in either string. But if the last addition produced a carry of 1, that carry was being ignored. For example, 1 + 1 processes one column: 1 + 1 = 2, writes 0, sets carry = 1, and then the loop ends. Then the "1" is never written, so the result is "0" instead of "10".

Test that caught this bug was "singleBitCarry", where addBinary("1", "1") returned "0" instead of "10".

To fix it, we added the leftover carry after the loop, before reversing the result:

```java
if (carry > 0) {
        result.append(carry);
}
```

## 2. Structural Testing

After running JaCoCo (through "mvn test jacoco:report"), the results were:

| Metric | Result |
|--------|--------|
| Instruction coverage | 96% (3 instructions missed) |
| **Branch coverage** | **100%** |
| Line coverage | 20/21 |
| Method coverage | 1/2 missed |

Branch coverage is 100%, which was the main goal. The one missed line and method is the default constructor "public AddBinary()", which is never called because all methods are "static". So there is no point writing a test for it since it contains no logic.

To make the branch coverage more explicit, two extra tests were added to clearly cover the cases where only one index is active for several iterations:

- "onlyBContributesForSeveralBits", with "1" + "1010" so that "a" runs out of digits quickly, and the loop continues processing only "b"
- "onlyAContributesForSeveralBits", with "1010" + "1" so that "b" runs out of digits quickly, and the loop continues processing only "a"

These ensure the "if (i >= 0)" and "if (j >= 0)" branches inside the loop have their "false" case covered explicitly, not just by accident.


## 3. Mutation Testing

Running PIT (through "mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave the following results:

```
Generated 23 mutations — Killed 23 (100%)
Test strength 100%
```

Breakdown by mutator:

| Mutator | What it does | Result |
|---------|-------------|--------|
| ConditionalsBoundaryMutator | Changes `>` to `>=`, etc. | 5/5 killed |
| IncrementsMutator | Changes `i--` to `i++` | 2/2 killed |
| MathMutator | Changes `%` to `/`, `+` to `-`, etc. | 8/8 killed |
| EmptyObjectReturnValsMutator | Makes the method return `""` | 1/1 killed |
| NegateConditionalsMutator | Negates conditions like `i >= 0` | 7/7 killed |

It's important to note that some mutants show as "TIMED_OUT" rather than "KILLED". This also counts as killed, because it means the mutation caused an infinite loop (for example, changing "i--" to "i++" makes the index grow forever), and PIT killed the process after a timeout.
As no mutants survived, no additional tests were needed.