# Solution: MinCostClimbingStairs

## Bug Found and Fix

Running the two examples from the README revealed a bug. Example 1 ([10, 15, 20]) returned the correct value of 15, but Example 2 ([1, 100, 1, 1, 1, 100, 1, 1, 100, 1]) returned 5 instead of the expected 6.

The issue was in the initialization of the DP array:

dp[0] = 0


dp[0]  represents the minimum cost to reach step 0, which should be cost[0]. Setting it to 0  makes the first step free, which is wrong. The fix was:

dp[0] = cost[0]


Example 1 passed with the bug because the optimal path there starts at step 1 (not step 0), so the wrong value in dp[0] didn't affect the final result. Example 2, however, has an optimal path that starts at step 0, which revealed the bug.


## Task 2 Contracts

### Pre-conditions

Based on the README constraints:

- cost must not be null
- cost.length must be in [2, 1000]
- Every element (cost[i]) must be in [0, 999]

### Post-conditions

- The return value must be positive >= 0 (since all costs are non-negative)
- The return value must be <= sum(cost) (we can never pay more than every step)

### Invariants

During the DP computation, every value in the dp array must remain non-negative. This is a  consequence of the pre-condition that all costs are non-negative, but we check it explicitly so any logic error would be caught, rather than silently producing a wrong result.

All contracts are implemented directly in MinCostClimbingStairs.java using guard clauses and explicit checks.

## Task 3 Testing the Contracts

The test file covers all three categories:

**Pre-condition violations**: we verify that an IllegalArgumentException is thrown for:
- null input
- arrays of length 0, 1, or 1001
- arrays containing a negative value
- arrays containing a value above 999

**Post-condition checks**: we verify that for valid inputs:
- the result is always positive
- the result never exceeds the sum of all costs

**Normal operation**: the two examples from the README, a minimum-length array, all-zero costs, all-equal costs, and a longer manually computed case all pass.

## Task 4: Property-Based Testing

We used jqwik and identified four properties:

1. **Non-negativity**: the result is always positive for any valid input. This follows from all costs being non-negative and is a sanity check.

2. **Upper bound**: the result never exceeds the sum of all elements. The optimal path can skip steps but can never avoid paying more than the total.

3. **Two-element base case**: when the array has exactly two elements, the answer is min(cost[0], cost[1]) —> you just pick the cheaper step and jump to the top. 

4. **Homogeneity / scaling**: multiplying all costs by a constant factor k should multiply the result by the same k. This is true because the structure of the optimal path does not change when costs are scaled uniformly.

We kept the generated input sizes small (max 50 elements) to keep the tests fast, and used @IntRange(min=0, max=999) to stay within the valid domain so no exceptions are thrown during property runs.

## Coverage

JaCoCo results after running `mvn test`:

| Metric | Coverage |
|---|---|
| Instructions | 74% (114/154) |
| Branches | 88% (16/18) |
| Lines | 72% (18/25) |
| Methods | 50% (2/4) |

The overall numbers are pulled down by two factors unrelated to the actual algorithm logic:

**Main.java**: the main() method is a simple demo runner that is never invoked by the test suite. It contributes several uncovered lines and methods to the report, but it contains no logic worth testing.

**Internal safety guards**: the two throw new IllegalStateException(...) statements inside MinCostClimbingStairs.java are intentionally unreachable with valid inputs. They act as internal defensive checks against future regressions in the logic, not as paths meant to be "taken" from outside. With all costs in [0, 999], these branches can never trigger.