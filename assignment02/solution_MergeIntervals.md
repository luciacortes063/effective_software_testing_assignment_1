# Solution: MergeIntervals

## Bug Found and Fix

Adding a test for empty input revealed a bug. The README states that `intervals.length` is in `[0, 10^4]`, so `merge(new int[0][])` is a valid call. The original code immediately did:

```java
int[] newInterval = intervals[0];
result.add(newInterval);
```

which throws `ArrayIndexOutOfBoundsException` whenever the input is empty.

The fix is an early return after the pre-condition checks:

```java
if (intervals.length == 0) {
    return new int[0][];
}
```

The README examples themselves never trigger the bug (both have non-empty input), so the issue only shows up once empty input is tested explicitly. After the fix, both README examples plus the empty case and every additional test case pass.

## Task 2: Contracts

### Pre-conditions

Based on the README constraints:

- `intervals` must not be null
- `intervals.length` must be in `[0, 10000]`
- Each `intervals[i]` must not be null, must have length `2`, and must satisfy `start <= end`

### Post-conditions

- The result is non-null
- Every output interval is well-formed (`start <= end`)
- The output is sorted by `start`
- Consecutive output intervals do not overlap or touch: `result[i].start > result[i-1].end`

### Invariants

After every iteration of the merge loop, the most recently added interval (`newInterval`) must remain well-formed (`start <= end`). This follows from the pre-condition that every input interval is well-formed and from the merge step using `Math.max` on the end value, but we check it explicitly so any future regression in the update logic would surface immediately.

All contracts are implemented in `MergeIntervals.java` using guard clauses and explicit checks.

## Task 3: Testing the Contracts

The test file covers all three categories:

**Pre-condition violations**: we verify that `IllegalArgumentException` is thrown for:
- null input
- input length 10001 (above the maximum of 10000)
- an array containing a `null` interval
- an interval with length other than 2
- an interval where `start > end`

**Post-condition checks**: we verify that for valid inputs:
- the result is sorted by `start`
- every output interval is well-formed (`start <= end`)
- consecutive output intervals do not overlap (`next.start > prev.end`)
- the result size is at most the input size (merging only reduces or preserves count)

**Normal operation**: both README examples, the empty-input case, single-interval, no-overlap, all-merge-into-one, unsorted input, identical intervals, single-point intervals (`start == end`), touching point intervals, and negative bounds all pass.

## Task 4: Property-Based Testing

We used jqwik with a custom `@Provide` method that generates valid intervals (bounds in a finite integer range, with `start <= end` enforced) and identified five properties:

1. **Sorted output**: the result is always sorted by `start`. The implementation sorts the input as its first step, so this must hold for every input.

2. **Disjoint output**: no two consecutive output intervals overlap or touch. Whenever they would, the merge step combines them into one.

3. **Well-formed output**: every output interval satisfies `start <= end`. This follows from the input pre-condition and the use of `Math.max` on the end value during merging.

4. **Size monotonicity**: the result size never exceeds the input size. Merging can only reduce or preserve the number of intervals.

5. **Coverage preservation**: the union of points covered by the output equals the union of points covered by the input. We check this on a small integer domain by evaluating membership for every integer in the bounding range. This is the strongest correctness property — it pins down that merging neither adds nor removes any covered points.

6. **Idempotence**: merging the output again yields the same output. A correctly merged set of intervals is already in its canonical form.

We used a small integer domain (`[-100, 100]`, and `[-20, 20]` for the coverage-preservation property) and small input sizes (up to 30 intervals, 8 for coverage) to keep property runs fast while still exercising a wide variety of overlap configurations. We deep-clone the input before each call because the implementation mutates the input array in place; without cloning, `mergeIsIdempotent` would compare against an already-mutated reference.

## Coverage

JaCoCo results after running `mvn test`:

| Metric | Coverage |
|---|---|
| Instructions | 67% (201/299) |
| Branches | 89% (25/28) |
| Lines | 76% (29/38) |
| Methods | 60% (3/5) |

The overall numbers are pulled down by two factors unrelated to the algorithm logic:

**Main.java**: the `main()` demo runner is never invoked by the test suite. It contributes uncovered lines and one uncovered method (plus one uncovered class) to the report but contains no logic worth testing.

**Internal safety guards**: the two `throw new IllegalStateException(...)` statements (loop invariant and post-condition violation paths) are unreachable when the algorithm is correct. They are intentional defensive checks against future regressions and cannot fire under valid inputs.

All reachable lines in the algorithmic core of `MergeIntervals.java` are covered by the test suite.
