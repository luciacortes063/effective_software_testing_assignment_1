# Solution for Valid Perfect Square

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The isPerfectSquare method determines if a given positive integer $n$ is a perfect square (i.e., there exists an integer $x$ such that $x^2 = n$). The implementation must handle 32-bit signed integers.

### Step 2 – Exploring the program

We tested small perfect squares like 4, 9, and 16, which passed. However, we noted that the binary search range and the calculation of mid \* mid are prone to integer overflow if not handled with a long. We also identified that the smallest valid input (1) and the largest possible 32-bit integer (2,147,483,647) are critical edge cases.

### Step 3 – Identifying the partitions

Input num Partitions:

- Invalid: $n < 0$ (negative) and $n = 0$
- Valid Perfect Squares: 1, 4, 9, ...
- Valid Non-Perfect Squares: 2, 3, 5, ...

### Step 4 – Boundaries

- Lower Bound: $1$ (The smallest perfect square)
- Upper Bound: $46340^2$ (The largest perfect square fitting in a 32-bit signed int)
- The zero Boundary: $0$ and $-1$ to test the exception logic
- The Integer.MAX_VALUE Boundary to ensure the search terminates correctly when no square root is found

### Step 5 – Test cases

We've designed a test suite that consists of 3 tests and 2 properties one that states how the perfect square is built and the other how it is not built.

| ID  | Input      | Expected                 | Why                                      |
| --- | ---------- | ------------------------ | ---------------------------------------- |
| T1  | -1         | IllegalArgumentException | negative input boundary                  |
| T2  | 0          | IllegalArgumentException | zero input boundary                      |
| T3  |  1         | true                     | smallest valid perfect square            |
| T4  | x\*x       | true                     |  property: All squares up to $46340^2$   |
| T5  | x \* x + 1 | false                    | property: Non-squares in the valid range |

### Step 6 – Automating the tests

See `PerfectSquarePropertyTest.java`.

### Bug found

The binary search wasn't completing the last step as the condition inside the while loop was `left < right`. I've replaced the < symbol for a <= just in the case the perfect square is when they both are equal.

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric               | Result                     |
| -------------------- | -------------------------- |
| Instruction coverage | 95% (3 instruction missed) |
| **Branch coverage**  | **100% (10/10)**           |
| Line coverage        | 16/17                      |
| Method coverage      | 0/2 missed                 |

Branch coverage is 100%

The conditions covered are:

| Condition                      | Covered by                                        |
| ------------------------------ | ------------------------------------------------- |
| `num <= 0` (true: negative)    | negativeNumberThrowsException                     |
| `num <= 0` (true: zero)        | zeroNumberThrowsException, mixedArray             |
| `num <= 0` (false)             | oneIsPerfectSquare, validPerfectSquaresReturnTrue |
| `mid * mid == num` (true)      | validPerfectSquaresReturnTrue                     |
| `mid * mid < num` (true/false) | nonPerfectSquaresReturnFalse                      |
| `left <= right` (exits loop)   | nonPerfectSquaresReturnFalse                      |

## 3. Mutation Testing

Running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave:

```
Generated 17 mutations Killed 16 (94%)
Mutations with no coverage 0. Test strength 94%
```

There's 1 mutant that survives. While the mutant is in the bianry search algorithm, in the following line `} else if (square < num) {`, the boundary condition changes and the mutant survives.
