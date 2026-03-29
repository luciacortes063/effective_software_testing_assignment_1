# Solution for Plus One

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The method takes an array of integers representing a single large non-negative integer. It increments this integer by one and returns the resulting array. The input array cannot be null or empty. The logic must handle specific carry scenarios, where when a digit is 9, it becomes 0 and the carry propagates to the left.

### Step 2 – Exploring the program

We tested the basic case [1, 2, 3] and got [1, 2, 4]. However, when testing [9, 9], the program threw and Exception. This revealed that the original code didn't handle the case where a carry propagated beyond the most significant bit.

### Step 3 – Identifying the partitions

Partitions according to the input digits:

- Null must throw an exception
- Empty array must throw an exception
- Single digit different from nine implies a simple increment
- Single digit 9 implies a new array size

Partitions according to carry behavior:

- No carry if last digit is < 9
- Carry in the middle (i.e, [1,9,2]) implies no propagation. However [1,9,9], the carry propagates.
- Carry propagates through the entire array [9,9,9]

### Step 4 – Boundaries

The main boundary is the digit 9. It is the only value that triggers a carry. Another critical boundary is the array length.

### Step 5 – Test cases

| ID  | Input     | Expected    | Why                                 |
| --- | --------- | ----------- | ----------------------------------- |
| T1  | null      | exception   | null input                          |
| T2  | []        | [exception] | empty input                         |
| T3  | [1]       | [2]         | smallest single digit               |
| T4  | [9]       | [1, 0]      | single digit adds carry             |
| T5  | [1, 2, 3] | [1, 2, 4]   | multiple digits increments non-zero |
| T6  | [9, 9]    | [1, 0, 0]   | multiple carries                    |

### Step 6 – Automating the tests

See PlusOneTest.java.

### Step 7 – Extra checks

One structural test was added: "alternatingZeroNonZero" with [0,1,0,2,0,3] to force the first pass to alternate between skipping (zero) and copying (non-zero) on every iteration.

Bug found
The original implementation failed to handle the "all-nines" case. It would complete the loop, setting all digits to 0, but it lacked the logic to create a new array with a leading 1.

Fix: We added the following logic after the loop:

```java
int[] result = new int[digits.length + 1];
result[0] = 1;
return result;
```

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric               | Result                     |
| -------------------- | -------------------------- |
| Instruction coverage | 94% (3 instruction missed) |
| **Branch coverage**  | **100% (8/8)**             |
| Line coverage        | 10/11                      |
| Method coverage      | 1/2 missed                 |

Branch coverage is 100%. The missed line and method is the default constructor.

The conditions covered are:

| Condition                                    | Covered by                                           |
| -------------------------------------------- | ---------------------------------------------------- |
| `digits == null` (true)                      | nullInputArrayThrowsException                        |
| `digits == null` (false)                     | singleDigitIncrement, multipleCarries, etc.          |
| `digits.length == 0` (true)                  | emptyInputArrayThrowsException                       |
| `digits.length == 0` (false)                 | singleDigitIncrement, multipleCarries                |
| `i >= 0` in loop (true, enters/continues)    | singleDigitIncrement, multipleCarries                |
| `i >= 0` (false, loop terminates)            | multipleCarries (terminates after exhausting all 9s) |
| `digits[i] < 9` (true, increment and return) | singleDigitIncrement, multipleDigitsIncrement        |
| `digits[i] < 9` (false, set to 0 and carry)  | singleDigitNineIncrement, multipleCarries            |

## 3. Mutation Testing

Running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave:

```
Generated 11 mutations — Killed 11 (100%)
Test strength 100%
```

No mutants survived, so no additional tests were needed.
