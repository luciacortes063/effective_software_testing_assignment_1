# Solution for Sort Colors

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The `sortColors` method takes an array of integers representing objects of three different colors. The goal is to sort the array in-place so that objects of the same color are adjacent, in the order red, white, and then blue.

### Step 2 – Exploring the program

The algorithm uses a three-pointer approach (low, mid, and high) to partition the array in a single pass. We noticed that if the input contains an invalid number (like 3), the original algorithm might either ignore it or produce an incorrect sort unless an explicit validation step is added.

### Step 3 – Identifying the partitions

Input nums array:

- null —> Throws IllegalArgumentException.
- Empty [] —> No action needed, remains empty.
- Single element [0], [1], or [2] —> Already sorted.
- Invalid elements (e.g., -1 or 3) —> Throws IllegalArgumentException.

Array state:

- Already sorted: [0, 1, 2].
- Reverse sorted: [2, 1, 0].
- Mixed colors: [2, 0, 2, 1, 1, 0].

### Step 4 – Boundaries

The boundaries are the specific integer values allowed in the set $\{0, 1, 2\}$.

- Values strictly less than 0 are invalid.
- Values strictly greater than 2 are invalid.
- The transition points where the pointers (low, mid, high) meet or cross.

### Step 5 – Test cases

We have defined a total of 10 tests with the following input and expected output:

| ID  | Input               | Expected                 | Why                          |
| --- | ------------------- | ------------------------ | ---------------------------- |
| T1  | null                | IllegalArgumentException | null check                   |
| T2  | []                  | []                       | empty array                  |
| T3  |  [0, 1, 3]          | IllegalArgumentException | Invalid positive value (3)   |
| T4  | [0, -1, 1]          | IllegalArgumentException |  Invalid negative value (-1) |
| T5  | [1, 0]              | [0, 1]                   | Right swap                   |
| T6  | [2, 1]              | [1, 2]                   | Left swap                    |
| T7  |  [0, 0, 1, 1, 2, 2] | [0, 0, 1, 1, 2, 2]       | Already sorted               |
| T8  | [2, 2, 1, 1, 0, 0]  |  [0, 0, 1, 1, 2, 2]      | Reverse sorted               |
| T9  | [2, 0, 2, 1, 1, 0]  | [0, 0, 1, 1, 2, 2]       | Mixed array                  |
| T10 |  [1]                | [1]                      | Single element               |

### Step 6 – Automating the tests

See `SortColorsTest.java`.

### Step 7 – Extra checks

We used a helper `ValidColor.java` validation (implied by ValidColor logic) to ensure that the algorithm is robust against dirty data.

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric               | Result                     |
| -------------------- | -------------------------- |
| Instruction coverage | 94% (6 instruction missed) |
| **Branch coverage**  | **100% (18/18)**           |
| Line coverage        | 23/25                      |
| Method coverage      | 0/5 missed                 |

Branch coverage is 100%

The conditions covered are:

| Condition                            | Covered by                                                                         |
| ------------------------------------ | ---------------------------------------------------------------------------------- |
| `nums == null` (true)                | nullArrayThrowsException                                                           |
| `nums == null` (false)               | emptyInputArray, mixedArray                                                        |
| `nums[i] < 0 OR nums[i] > 2` (true)  | invalidPositiveInputArrayThrowsException, invalidNegativeInputArrayThrowsException |
| `nums[i] < 0 OR nums[i] > 2` (false) | mixedArray, alreadySortedArray                                                     |
| `mid <= right` (true, enters)        | mixedArray (via Assume handling)                                                   |
| `mid <= right` (false, exits)        | emptyInputArray (never enters), mixedArray (on completion)                         |

## 3. Mutation Testing

Running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave:

```
Generated 10 mutations Killed 10 (100%)
Mutations with no coverage 0. Test strength 100%
```

No mutants survived, so no additional tests were needed.
