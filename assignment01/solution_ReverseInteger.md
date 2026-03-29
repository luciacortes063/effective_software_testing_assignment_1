# Solution for Reverse Integer

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The goal is to reverse the digits of a 32-bit signed integer. If reversing the integer causes it to exceed the 32-bit signed integer range ($[-2^{31}, 2^{31} - 1]$), the method must return 0. Negative numbers should remain negative after reversal (e.g., -123 becomes -321).

### Step 2 – Exploring the program

We manually tested 123 (result: 321) and -123 (result: -321). We then tested a potential overflow case like 1,534,236,469. Mathematically, the reverse is 9,646,324,351, which is larger than Integer.MAX_VALUE. The program correctly returned 0, confirming the overflow guards were active.

### Step 3 – Identifying the partitions

Partitions according to the input x:

- Zero: 0 (Smallest non-negative).
- Positive numbers: Small (single digit), Medium (no overflow), Large (potential overflow).
- Negative numbers: Small (single digit), Medium (no overflow), Large (potential overflow).
- Trailing zeros: e.g., 120. Reversing results in 21, which is valid but not symmetrical.

### Step 4 – Boundaries

- Integer.MAX_VALUE: Reversing this should trigger overflow.
- Integer.MIN_VALUE ($-2,147,483,648$): Reversing this should trigger underflow.
- Numbers that are just below the overflow threshold when reversed.

### Step 5 – Test cases

We defined a Property-Based Test using the jqwik framework. This allows us to verify the property. The property name is called `reversingTwiceReturnsOriginal`and it has the following characteristics:

| Input Domain (@ForAll int x)                             | Invariants (Assertions)  | Constraints (Assume)                                                                            |
| -------------------------------------------------------- | ------------------------ | ----------------------------------------------------------------------------------------------- |
| All valid 32-bit signed integers ($[-2^{31}, 2^{31}-1]$) | reverse(reverse(x)) == x | 1. Skip $x$ ending in 0 (non-symmetrical).2. Skip cases where reverse(x) overflows (returns 0). |

We have combined it with a test called `testNearOverflowBoundaries` that tests an integer and it expects the following behavior:

- Input: a number > INT_MAX -> expects 0
- Input: a number in the range -> expects the reversed number
- Input: a negative number in the range -> expects the reversed negative number

### Step 6 – Automating the tests

See `ReverseIntegerPropertyTest.java`. We used jqwik to ensure that for any integer x not ending in zero and not causing overflow, reversing it twice returns the original value.

### Step 7 – Extra checks

We used `java Assume.that(x % 10 != 0)` because numbers with trailing zeros, it becomes a non-significant leading zero. It makes the symmetry property mathematically invalid for those specific cases.

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric               | Result                     |
| -------------------- | -------------------------- |
| Instruction coverage | 91% (3 instruction missed) |
| **Branch coverage**  | **100% (6/6)**             |
| Line coverage        | 11/12                      |
| Method coverage      | 0/2 missed                 |

Branch coverage is 100%

The conditions covered are:

| Condition                                             | Covered by                                                 |
| ----------------------------------------------------- | ---------------------------------------------------------- |
| `x != 0` (true, enters loop)                          | reversingTwiceReturnsOriginal (any non-zero input)         |
| `x != 0` (false, skips loop)                          | reversingTwiceReturnsOriginal (when 0 is generated)        |
| `reversed > Integer.MAX_VALUE / 10` (true, overflow)  | reversingTwiceReturnsOriginal (via Assume handling)        |
| `reversed > Integer.MAX_VALUE / 10` (false)           | reversingTwiceReturnsOriginal (standard positive integers) |
| `reversed < Integer.MIN_VALUE / 10` (true, underflow) | reversingTwiceReturnsOriginal (via Assume handling)        |
| `reversed < Integer.MIN_VALUE / 10` (false)           | reversingTwiceReturnsOriginal (standard negative integers) |

## 3. Mutation Testing

Running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave:

```
Generated 10 mutations Killed 10 (100%)
Mutations with no coverage 0. Test strength 100%
```

No mutants survived, so no additional tests were needed.
