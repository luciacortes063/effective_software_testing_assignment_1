# Solution for Move Zeroes

## 1. Specification-Based Testing

### Step 1 – Understanding the requirements

The method receives an integer array and moves all zero values to the end while preserving the relative order of non-zero elements. The operation is in-place (no additional arrays). The array may contain positive numbers, negative numbers, and zeroes. The Javadoc contract specifies that a null array throws an IllegalArgumentException.

### Step 2 – Exploring the program

We ran the README example ([0, 1, 0, 3, 12]) and expected [1, 3, 12, 0, 0] but got [1, 3, 0, 0, 0]. The last non-zero element (12) was not moved forward, indicating a bug in how the array is traversed.

### Step 3 – Identifying the partitions

Partitions according to the array content:
- Only non-zero elements (nothing to move)
- Only zeroes (already correct)
- Mix of zeroes and non-zeroes

Partitions according to array size:
- Empty array
- Single element (zero or non-zero)
- Multiple elements

Partitions according to zero positions:
- Zeroes at the start
- Zeroes at the end
- Zeroes in the middle
- Consecutive zeroes
- Alternating zeroes and non-zeroes

Special cases:
- null array (from Javadoc contract)
- Negative numbers mixed with zeroes

### Step 4 – Boundaries

The main boundary is the last element of the array. Any bug in the loop's termination condition would show up when the last element is non-zero. The simplest case is a single non-zero element [5], which should remain [5].

Another boundary is an array where the only non-zero element is at the very end: [0, 0, 5] should become [5, 0, 0].

### Step 5 – Test cases

| ID  | Input | Expected | Why |
|-----|-------|----------|-----|
| T1  | null  | exception | null input |
| T2  | [0,1,0,3,12] | [1,3,12,0,0] | README example, reveals bug |
| T3  | [1,2,3] | [1,2,3] | no zeroes |
| T4  | [0,0,0] | [0,0,0] | all zeroes |
| T5  | [5] | [5] | single non-zero |
| T6  | [0] | [0] | single zero |
| T7  | [0,1] | [1,0] | zero at start |
| T8  | [1,0] | [1,0] | zero at end |
| T9  | [0,0,1,2] | [1,2,0,0] | consecutive zeroes |
| T10 | [0,-1,0,3] | [-1,3,0,0] | negative numbers |
| T11 | [] | [] | empty array |
| T12 | [0,0,5] | [5,0,0] | last element is non-zero, also reveals bug |

### Step 6 – Automating the tests

See MoveZeroesTest.java.

### Step 7 – Extra checks

One structural test was added: "alternatingZeroNonZero" with [0,1,0,2,0,3] to force the first pass to alternate between skipping (zero) and copying (non-zero) on every iteration.

### Bug found

The first pass loop on line 20 had the condition `i < numbers.length-1` instead of `i < numbers.length`. The `-1` causes the loop to skip the last element of the array entirely. For any array where the last element is non-zero, that element is never moved forward, and it gets overwritten with 0 in the second pass.

The test that caught this bug was "readmeExample": `moveZeroes(new int[]{0, 1, 0, 3, 12})` produced `[1, 3, 0, 0, 0]` instead of `[1, 3, 12, 0, 0]`. The element `12` at index 4 was skipped because the loop only went up to index 3.

To fix it, we changed the loop condition:

```java
// before
for (int i = 0; i < numbers.length-1; i++) {

// after
for (int i = 0; i < numbers.length; i++) {
```

After the fix, all 13 tests pass.

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric | Result |
|--------|--------|
| Instruction coverage | 93% (3 instructions missed) |
| **Branch coverage** | **100% (8/8)** |
| Line coverage | 11/12 |
| Method coverage | 1/2 missed |

Branch coverage is 100%. The missed line and method is the default constructor.

The conditions covered are:

| Condition | Covered by |
|-----------|-----------|
| `numbers == null` (true) | T1 |
| `numbers == null` (false) | T2-T12, alternatingZeroNonZero |
| `i < numbers.length` in first loop (true, enters) | T2-T10, T12 |
| `i < numbers.length` in first loop (false, empty array) | T11 |
| `numbers[i] != 0` (true, copies element) | T2, T3, T5, T7, T10 |
| `numbers[i] != 0` (false, skips) | T2, T4, T6, T7, T9 |
| `insertPosition < numbers.length` in second loop (true) | T2, T4, T7, T9 |
| `insertPosition < numbers.length` in second loop (false) | T3, T5 |

## 3. Mutation Testing

Running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") gave:

```
Generated 7 mutations — Killed 7 (100%)
Test strength 100%
```

No mutants survived, so no additional tests were needed.
