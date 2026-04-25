# Solution: FindAllDuplicatesInArray

## Bug Found and Fix

After tracing through all three examples from the README manually, the algorithm produces the correct output in every case. No bug was found.

## Task 2: Contracts

### Pre-conditions

Based on the README constraints:

- nums must not be null
- nums.length must be in [1, 100000]
- Every element nums[i] must be in [1, nums.length]

### Post-conditions

- The returned list must not be null
- Every element in the result must be in [1, n]
- The result must not have repeated values (each duplicate is reported exactly once)
- The result size must be <= floor(n / 2) (at most half the array can be duplicates)

### Invariants

During the iteration, index = abs(nums[i]) - 1 must always be a valid array position (i.e., in [0, n-1]). This is guaranteed by the pre-condition that all values are in [1, n], but we check it explicitly as a defensive measure.

All contracts are implemented in FindAllDuplicatesInArray.java.

## Task 3: Testing the Contracts

**Pre-condition violations**: we verify that IllegalArgumentException is thrown for:
- null input
- empty array (length 0, below the minimum of 1)
- an element equal to 0 (below the minimum of 1)
- a negative element
- an element greater than n (e.g, value 4 in an array of length 3)

**Post-condition checks**: we verify that for valid inputs:
- the result is not null
- every element in the result is in [1, n]
- the result doesn't contain repeated values
- the result size does not exceed floor(n / 2)

**Normal operation**: all three README examples, arrays with no duplicates, arrays where all elements are duplicates, and minimal two-element arrays all pass.

## Task 4 Property-Based Testing

We used jqwik with a custom @Provide method that generates valid inputs (each value in [1, n], appearing once or twice) and identified four properties:

1. **Correctness of reported duplicates**: every value in the result appeared exactly twice in the original input. 

2. **No false positives**: elements that appear only once in the input must not appear in the result. This is the complementary check to property 1.

3. **No repeated values in result**: each duplicate is reported exactly once. Even if a value appears twice, it should show up only once in the output list.

4. **Size bound**: the result size can never exceed floor(n / 2). Since each duplicate requires two slots in the array, at most half the array can consist of duplicate pairs.

We used a custom validInputs provider instead of raw random arrays because generating random arrays that satisfy the constraint "each value in [1, n] appears at most twice" is not straightforward with built-in jqwik annotations. The provider builds a pool of values and randomly adds duplicates with a 40% probability before shuffling.


## Coverage

JaCoCo results after running `mvn test`:

| Metric | Coverage |
|---|---|
| Instructions | 65% (121/184) |
| Branches | 84% (22/26) |
| Lines | 70% (19/27) |
| Methods | 50% (2/4) |

The overall numbers are pulled down by two factors unrelated to the actual algorithm logic:

**Main.java**: the main() method is never called by tests. It contributes uncovered lines and methods to the report but contains no logic worth testing.

**Internal safety guards**: the two throw new IllegalStateException(...) lines are unreachable with valid inputs by design. The pre-condition already ensures that all values are in [1, n], so index is always valid and the result values always in range.

All reachable lines in FindAllDuplicatesInArray.java are covered by the test suite.