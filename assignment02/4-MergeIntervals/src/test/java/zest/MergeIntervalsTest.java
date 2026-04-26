package zest;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeIntervalsTest {

    private MergeIntervals solution = new MergeIntervals();

    // Task 1 Coverage: basic cases to "activate" every line of the algorithm

    @Test
    void testExample1FromReadme() {
        // Readme example 1: [[1,3],[2,6],[8,10],[15,18]] -> [[1,6],[8,10],[15,18]]
        int[][] input = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] expected = {{1, 6}, {8, 10}, {15, 18}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testExample2FromReadme() {
        // Readme example 2: [[1,4],[4,5]] -> [[1,5]] (touching intervals are merged)
        int[][] input = {{1, 4}, {4, 5}};
        int[][] expected = {{1, 5}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testEmptyInputReturnsEmpty() {
        // empty input is allowed by the README (length range is [0, 10^4]); the original
        // implementation crashed here with ArrayIndexOutOfBoundsException
        assertArrayEquals(new int[0][], solution.merge(new int[0][]));
    }

    @Test
    void testSingleInterval() {
        int[][] input = {{5, 7}};
        int[][] expected = {{5, 7}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testNoOverlaps() {
        int[][] input = {{1, 2}, {3, 4}, {5, 6}};
        int[][] expected = {{1, 2}, {3, 4}, {5, 6}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testAllOverlapIntoOne() {
        int[][] input = {{1, 10}, {2, 5}, {3, 7}, {6, 9}};
        int[][] expected = {{1, 10}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testUnsortedInput() {
        // intervals provided out of order should still merge correctly after sort
        int[][] input = {{8, 10}, {1, 3}, {15, 18}, {2, 6}};
        int[][] expected = {{1, 6}, {8, 10}, {15, 18}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testIdenticalIntervals() {
        int[][] input = {{1, 4}, {1, 4}, {1, 4}};
        int[][] expected = {{1, 4}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testSinglePointIntervals() {
        // start == end is valid; non-touching points stay separate
        int[][] input = {{1, 1}, {3, 3}, {5, 5}};
        int[][] expected = {{1, 1}, {3, 3}, {5, 5}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testTouchingPointIntervals() {
        // [a,a] and [a,b] touching at a -> merged
        int[][] input = {{1, 1}, {1, 5}};
        int[][] expected = {{1, 5}};
        assertArrayEquals(expected, solution.merge(input));
    }

    @Test
    void testNegativeBounds() {
        int[][] input = {{-5, -2}, {-3, 0}, {2, 4}};
        int[][] expected = {{-5, 0}, {2, 4}};
        assertArrayEquals(expected, solution.merge(input));
    }

    // Task 3 Contract tests: pre-conditions

    @Test
    void testNullInputThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> solution.merge(null));
    }

    @Test
    void testInputTooLongThrows() {
        // length 10001 exceeds the maximum of 10000
        int[][] input = new int[10_001][];
        for (int i = 0; i < input.length; i++) input[i] = new int[]{i, i};
        assertThrows(IllegalArgumentException.class,
                () -> solution.merge(input));
    }

    @Test
    void testNullIntervalThrows() {
        int[][] input = {{1, 2}, null, {5, 6}};
        assertThrows(IllegalArgumentException.class,
                () -> solution.merge(input));
    }

    @Test
    void testIntervalWrongLengthThrows() {
        int[][] input = {{1, 2}, {3}};
        assertThrows(IllegalArgumentException.class,
                () -> solution.merge(input));
    }

    @Test
    void testIntervalStartGreaterThanEndThrows() {
        int[][] input = {{5, 1}};
        assertThrows(IllegalArgumentException.class,
                () -> solution.merge(input));
    }

    // Task 3 Contract tests: post-conditions

    @Test
    void testResultIsSorted() {
        int[][] input = {{8, 10}, {1, 3}, {15, 18}, {2, 6}};
        int[][] result = solution.merge(input);
        for (int i = 1; i < result.length; i++) {
            assertTrue(result[i - 1][0] <= result[i][0],
                    "result must be sorted by start");
        }
    }

    @Test
    void testResultIntervalsAreWellFormed() {
        int[][] input = {{1, 3}, {2, 6}, {8, 10}};
        for (int[] iv : solution.merge(input)) {
            assertTrue(iv[0] <= iv[1], "every output interval must have start <= end");
        }
    }

    @Test
    void testResultIntervalsDoNotOverlap() {
        int[][] input = {{1, 4}, {3, 5}, {10, 12}, {11, 13}};
        int[][] result = solution.merge(input);
        for (int i = 1; i < result.length; i++) {
            assertTrue(result[i][0] > result[i - 1][1],
                    "consecutive merged intervals must not overlap");
        }
    }

    @Test
    void testResultSizeAtMostInputSize() {
        int[][] input = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        assertTrue(solution.merge(input).length <= input.length);
    }

    // Task 4: Property-based tests using jqwik

    // Output is always sorted by start
    @Property
    void resultAlwaysSortedByStart(@ForAll("validIntervals") int[][] intervals) {
        int[][] result = solution.merge(deepClone(intervals));
        for (int i = 1; i < result.length; i++) {
            assertTrue(result[i - 1][0] <= result[i][0]);
        }
    }

    // No two consecutive output intervals may overlap or touch
    @Property
    void resultIntervalsAreDisjoint(@ForAll("validIntervals") int[][] intervals) {
        int[][] result = solution.merge(deepClone(intervals));
        for (int i = 1; i < result.length; i++) {
            assertTrue(result[i][0] > result[i - 1][1]);
        }
    }

    // Every output interval must be well-formed (start <= end)
    @Property
    void resultIntervalsAreWellFormed(@ForAll("validIntervals") int[][] intervals) {
        for (int[] iv : solution.merge(deepClone(intervals))) {
            assertTrue(iv[0] <= iv[1]);
        }
    }

    // Output size never exceeds input size (merging can only reduce or preserve count)
    @Property
    void resultSizeAtMostInputSize(@ForAll("validIntervals") int[][] intervals) {
        assertTrue(solution.merge(deepClone(intervals)).length <= intervals.length);
    }

    // The union of points covered by output equals the union of points covered by input.
    // We check this on a small integer domain by evaluating membership for every integer
    // in the bounding range.
    @Property
    void resultPreservesCoverage(@ForAll("smallValidIntervals") int[][] intervals) {
        int[][] result = solution.merge(deepClone(intervals));
        if (intervals.length == 0) {
            assertEquals(0, result.length);
            return;
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int[] iv : intervals) {
            min = Math.min(min, iv[0]);
            max = Math.max(max, iv[1]);
        }
        for (int x = min; x <= max; x++) {
            assertEquals(covers(intervals, x), covers(result, x),
                    "coverage of point " + x + " must be preserved");
        }
    }

    // Idempotence: merging the output again yields the same output.
    @Property
    void mergeIsIdempotent(@ForAll("validIntervals") int[][] intervals) {
        int[][] once = solution.merge(deepClone(intervals));
        int[][] twice = solution.merge(deepClone(once));
        assertArrayEquals(once, twice);
    }

    // Arbitrary providers

    // General valid intervals, possibly large in count
    @Provide
    Arbitrary<int[][]> validIntervals() {
        Arbitrary<Integer> n = Arbitraries.integers().between(0, 30);
        return n.flatMap(size -> {
            List<Arbitrary<int[]>> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(intervalArbitrary(-100, 100));
            }
            if (list.isEmpty()) {
                return Arbitraries.just(new int[0][]);
            }
            return Combinators.combine(list).as(parts -> parts.toArray(new int[0][]));
        });
    }

    // Smaller numeric range to keep the coverage-preservation property fast
    @Provide
    Arbitrary<int[][]> smallValidIntervals() {
        Arbitrary<Integer> n = Arbitraries.integers().between(0, 8);
        return n.flatMap(size -> {
            List<Arbitrary<int[]>> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(intervalArbitrary(-20, 20));
            }
            if (list.isEmpty()) {
                return Arbitraries.just(new int[0][]);
            }
            return Combinators.combine(list).as(parts -> parts.toArray(new int[0][]));
        });
    }

    private Arbitrary<int[]> intervalArbitrary(int min, int max) {
        Arbitrary<Integer> a = Arbitraries.integers().between(min, max);
        Arbitrary<Integer> b = Arbitraries.integers().between(min, max);
        return Combinators.combine(a, b).as((x, y) -> new int[]{Math.min(x, y), Math.max(x, y)});
    }

    // Helpers

    private boolean covers(int[][] intervals, int x) {
        for (int[] iv : intervals) {
            if (iv[0] <= x && x <= iv[1]) return true;
        }
        return false;
    }

    private int[][] deepClone(int[][] in) {
        int[][] out = new int[in.length][];
        for (int i = 0; i < in.length; i++) out[i] = in[i].clone();
        return out;
    }
}
