# Solution: NumberOfLongestIncreasingSubsequence

## Bug Found and Fix

Both README examples failed with the original code. Example 1 (`[1,3,5,4,7]`, expected `2`) returned `0`, and Example 2 (`[2,2,2,2,2]`, expected `5`) also returned `0`. In fact, the algorithm returned `0` for every input.

The bug was in the initialization of the two DP arrays:

```java
Arrays.fill(lengths, 0);
Arrays.fill(counts, 0);
```

In the standard LIS counting DP, `lengths[i]` is the length of the longest increasing subsequence ending at index `i`, and `counts[i]` is the number of such subsequences. Each element by itself forms a length-1 subsequence with count 1, so both arrays must be initialized to `1`. With the original `0` initialization, every later update propagates `0`s and the final sum is always `0`.

The fix:

```java
Arrays.fill(lengths, 1);
Arrays.fill(counts, 1);
```

After the fix, both README examples and all additional test cases pass.

## Task 2: Contracts

### Pre-conditions

Based on the README constraints:

- `nums` must not be null
- `nums.length` must be in `[1, 2000]`
- Every element `nums[i]` must be in `[-10^6, 10^6]`

### Post-conditions

- The result must be `>= 1`. For any non-empty input the array itself contains at least one element, which is always a valid LIS of length 1.

### Invariants

After processing each index `i` in the outer loop, both `lengths[i]` and `counts[i]` must remain `>= 1`. This is guaranteed by the corrected initialization (the values can only grow, never shrink) and is checked explicitly so any future regression in the update logic would surface immediately rather than silently producing a wrong answer.

All contracts are implemented directly in `NumberOfLongestIncreasingSubsequence.java` using guard clauses and explicit checks.

## Task 3: Testing the Contracts

The test file covers all three categories:

**Pre-condition violations**: we verify that `IllegalArgumentException` is thrown for:
- null input
- empty array (length 0, below the minimum of 1)
- length 2001 (above the maximum of 2000)
- a value below `-10^6`
- a value above `10^6`

**Post-condition checks**: we verify that for valid inputs:
- the result is always `>= 1`
- for an all-equal array of length `n`, the result is exactly `n` (the upper bound on the count of length-1 LIS)

**Normal operation**: both README examples plus single-element, strictly increasing, strictly decreasing, two-element variants (increasing / equal / decreasing), negative values, multiple equal-length chains, and boundary values at `-10^6` / `10^6` all pass.

## Task 4: Property-Based Testing

We used jqwik and identified five properties:

1. **Non-emptiness of result**: for any valid input, the result is always `>= 1`. A non-empty input always contains at least one length-1 LIS.

2. **Single-element base case**: when the array has exactly one element, the result is `1`. There is exactly one LIS (the element itself).

3. **All-equal arrays**: when all `n` elements are equal, the result is exactly `n`. No element is strictly greater than another, so every element is its own length-1 LIS.

4. **Strictly increasing arrays**: when the input is strictly increasing (distinct values sorted ascending), the result is `1`. There is exactly one LIS — the whole array itself.

5. **Strictly decreasing arrays**: when the input is strictly decreasing (distinct values sorted descending), the result is `n`. No two elements form an increasing pair, so every element is its own length-1 LIS.

We restricted generated values to `[-1000, 1000]` and lengths up to 30 to keep property runs fast while still exercising the algorithm widely. The `@UniqueElements` constraint plus a sort step generates the strictly monotonic inputs needed for properties 4 and 5.

## Coverage

JaCoCo results after running `mvn test`:

| Metric | Coverage |
|---|---|
| Instructions | 77% (188/243) |
| Branches | 91% (33/36) |
| Lines | 82% (32/39) |
| Methods | 50% (2/4) |

The overall numbers are pulled down by two factors unrelated to the algorithm logic:

**Main.java**: the `main()` demo runner is never invoked by the test suite. It contributes uncovered lines and one uncovered method to the report but contains no logic worth testing.

**Internal safety guards**: the two `throw new IllegalStateException(...)` statements are unreachable when the corrected initialization holds. They are intentional defensive checks that would catch a future regression in the inner-loop update logic; they cannot fire under valid inputs.

All reachable lines in the algorithmic core of `NumberOfLongestIncreasingSubsequence.java` are covered by the test suite.
