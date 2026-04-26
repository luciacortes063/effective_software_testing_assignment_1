package zest;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberOfLongestIncreasingSubsequenceTest {

    private NumberOfLongestIncreasingSubsequence solution = new NumberOfLongestIncreasingSubsequence();

    // Task 1 Coverage: basic cases to "activate" every line of the algorithm

    @Test
    void testExample1FromReadme() {
        // Readme example 1: [1,3,5,4,7] -> 2 LIS of length 4 ([1,3,4,7] and [1,3,5,7])
        int[] nums = {1, 3, 5, 4, 7};
        assertEquals(2, solution.findNumberOfLIS(nums));
    }

    @Test
    void testExample2FromReadme() {
        // Readme example 2: [2,2,2,2,2] -> 5 LIS of length 1
        int[] nums = {2, 2, 2, 2, 2};
        assertEquals(5, solution.findNumberOfLIS(nums));
    }

    @Test
    void testSingleElement() {
        // a single element is itself the unique LIS of length 1
        int[] nums = {7};
        assertEquals(1, solution.findNumberOfLIS(nums));
    }

    @Test
    void testStrictlyIncreasing() {
        // strictly increasing: there is exactly one LIS (the array itself)
        int[] nums = {1, 2, 3, 4, 5};
        assertEquals(1, solution.findNumberOfLIS(nums));
    }

    @Test
    void testStrictlyDecreasing() {
        // strictly decreasing: every element is its own length-1 LIS, so result = n
        int[] nums = {5, 4, 3, 2, 1};
        assertEquals(5, solution.findNumberOfLIS(nums));
    }

    @Test
    void testTwoElementsIncreasing() {
        int[] nums = {1, 2};
        assertEquals(1, solution.findNumberOfLIS(nums));
    }

    @Test
    void testTwoElementsEqual() {
        // equals are not strictly increasing -> two length-1 LIS
        int[] nums = {3, 3};
        assertEquals(2, solution.findNumberOfLIS(nums));
    }

    @Test
    void testTwoElementsDecreasing() {
        int[] nums = {2, 1};
        assertEquals(2, solution.findNumberOfLIS(nums));
    }

    @Test
    void testNegativeValues() {
        // includes negative range to exercise the lower bound of the value domain
        int[] nums = {-3, -2, -1, 0};
        assertEquals(1, solution.findNumberOfLIS(nums));
    }

    @Test
    void testMultipleEqualLengthChains() {
        // [1,2,4,3,5,4,7,2] -> several length-4 chains
        int[] nums = {1, 2, 4, 3, 5, 4, 7, 2};
        // chains of length 5: [1,2,4,5,7], [1,2,3,5,7], [1,2,3,4,7] -> 3
        assertEquals(3, solution.findNumberOfLIS(nums));
    }

    @Test
    void testBoundaryValues() {
        // values at the edges of the allowed range [-10^6, 10^6]
        int[] nums = {-1_000_000, 0, 1_000_000};
        assertEquals(1, solution.findNumberOfLIS(nums));
    }

    // Task 3 Contract tests: pre-conditions

    @Test
    void testNullInputThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> solution.findNumberOfLIS(null));
    }

    @Test
    void testEmptyArrayThrows() {
        // length 0 is below the minimum of 1
        assertThrows(IllegalArgumentException.class,
                () -> solution.findNumberOfLIS(new int[]{}));
    }

    @Test
    void testArrayTooLongThrows() {
        // length 2001 exceeds the maximum of 2000
        assertThrows(IllegalArgumentException.class,
                () -> solution.findNumberOfLIS(new int[2001]));
    }

    @Test
    void testValueTooLowThrows() {
        // -10^6 - 1 is below the minimum allowed value
        int[] nums = {0, -1_000_001, 1};
        assertThrows(IllegalArgumentException.class,
                () -> solution.findNumberOfLIS(nums));
    }

    @Test
    void testValueTooHighThrows() {
        // 10^6 + 1 is above the maximum allowed value
        int[] nums = {0, 1_000_001, 1};
        assertThrows(IllegalArgumentException.class,
                () -> solution.findNumberOfLIS(nums));
    }

    // Task 3 Contract tests: post-conditions

    @Test
    void testResultIsAtLeastOne() {
        // any valid non-empty input has at least one LIS
        int[] nums = {3, 1, 4, 1, 5};
        assertTrue(solution.findNumberOfLIS(nums) >= 1);
    }

    @Test
    void testResultUpperBoundForAllEqual() {
        // for all-equal arrays the result is exactly n
        int[] nums = {7, 7, 7};
        assertEquals(nums.length, solution.findNumberOfLIS(nums));
    }

    // Task 4: Property-based tests using jqwik

    // Result is always >= 1 for any valid input
    @Property
    void resultAlwaysAtLeastOne(
            @ForAll @Size(min = 1, max = 50) List<@IntRange(min = -1000, max = 1000) Integer> numsList) {
        int[] nums = toArray(numsList);
        assertTrue(solution.findNumberOfLIS(nums) >= 1);
    }

    // For a single-element array, the result is exactly 1
    @Property
    void singleElementResultIsOne(@ForAll @IntRange(min = -1_000_000, max = 1_000_000) int v) {
        int[] nums = {v};
        assertEquals(1, solution.findNumberOfLIS(nums));
    }

    // For an all-equal array of length n, every element is its own length-1 LIS so result = n
    @Property
    void allEqualResultEqualsLength(
            @ForAll @IntRange(min = 1, max = 30) int n,
            @ForAll @IntRange(min = -1000, max = 1000) int v) {
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = v;
        assertEquals(n, solution.findNumberOfLIS(nums));
    }

    // For a strictly increasing array of distinct values, there is exactly one LIS
    @Property
    void strictlyIncreasingResultIsOne(
            @ForAll @Size(min = 1, max = 30) @UniqueElements
            List<@IntRange(min = -1000, max = 1000) Integer> numsList) {
        List<Integer> sorted = new ArrayList<>(numsList);
        Collections.sort(sorted);
        int[] nums = toArray(sorted);
        assertEquals(1, solution.findNumberOfLIS(nums));
    }

    // For a strictly decreasing array of distinct values, every element is its own LIS so result = n
    @Property
    void strictlyDecreasingResultEqualsLength(
            @ForAll @Size(min = 1, max = 30) @UniqueElements
            List<@IntRange(min = -1000, max = 1000) Integer> numsList) {
        List<Integer> sorted = new ArrayList<>(numsList);
        Collections.sort(sorted, Collections.reverseOrder());
        int[] nums = toArray(sorted);
        assertEquals(nums.length, solution.findNumberOfLIS(nums));
    }

    // Helper

    private int[] toArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
