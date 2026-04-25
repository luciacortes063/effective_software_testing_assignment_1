package zest;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinCostClimbingStairsTest {

    private MinCostClimbingStairs solution = new MinCostClimbingStairs();

    // Task 1 Coverage: basic cases to "activate" every line of the algorithm

    @Test
    void testExample1FromReadme() {
        // Readme example: cost=[10,15,20] start at step 1 and then jump to top: total 15
        int[] cost = {10, 15, 20};
        assertEquals(15, solution.minCostClimbingStairs(cost));
    }

    @Test
    void testExample2FromReadme() {
        // Readme example 2. Expected output: 6
        int[] cost = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        assertEquals(6, solution.minCostClimbingStairs(cost));
    }

    @Test
    void testMinimumLengthArray() {
        // smallest valid input (length 2): pick the cheaper step
        int[] cost = {5, 3};
        assertEquals(3, solution.minCostClimbingStairs(cost));
    }

    @Test
    void testMinimumLengthFirstCheaper() {
        int[] cost = {2, 8};
        assertEquals(2, solution.minCostClimbingStairs(cost));
    }

    @Test
    void testAllZeroCosts() {
        // all zeros: result must be 0
        int[] cost = {0, 0, 0, 0};
        assertEquals(0, solution.minCostClimbingStairs(cost));
    }

    @Test
    void testAllSameCosts() {
        // with equal costs the result is just the cost of two steps
        int[] cost = {4, 4, 4, 4};
        assertEquals(8, solution.minCostClimbingStairs(cost));
    }

    @Test
    void testLongerArray() {
        int[] cost = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // computed manually: optimal path sums to 25
        assertEquals(25, solution.minCostClimbingStairs(cost));
    }

    // Task 3 Contract tests: pre-conditions

    @Test
    void testNullInputThrows() {
        // null is never a valid input
        assertThrows(IllegalArgumentException.class,
                () -> solution.minCostClimbingStairs(null));
    }

    @Test
    void testArrayTooShortThrows() {
        // length 1 violates the minimum length of 2
        int[] cost = {10};
        assertThrows(IllegalArgumentException.class,
                () -> solution.minCostClimbingStairs(cost));
    }

    @Test
    void testEmptyArrayThrows() {
        int[] cost = {};
        assertThrows(IllegalArgumentException.class,
                () -> solution.minCostClimbingStairs(cost));
    }

    @Test
    void testArrayTooLongThrows() {
        // length 1001 exceeds the maximum of 1000
        int[] cost = new int[1001];
        assertThrows(IllegalArgumentException.class,
                () -> solution.minCostClimbingStairs(cost));
    }

    @Test
    void testNegativeCostThrows() {
        // cost values can't be negative
        int[] cost = {10, -1, 20};
        assertThrows(IllegalArgumentException.class,
                () -> solution.minCostClimbingStairs(cost));
    }

    @Test
    void testCostAboveMaxThrows() {
        // 1000 exceeds the allowed maximum of 999
        int[] cost = {0, 1000};
        assertThrows(IllegalArgumentException.class,
                () -> solution.minCostClimbingStairs(cost));
    }

    // Task 3 Contract tests: post-conditions

    @Test
    void testResultIsNonNegative() {
        // result can't be negative
        int[] cost = {3, 7, 2, 5};
        assertTrue(solution.minCostClimbingStairs(cost) >= 0);
    }

    @Test
    void testResultDoesNotExceedTotalCost() {
        // the minimum path can never cost more than paying every step
        int[] cost = {5, 10, 15, 20};
        int total = 0;
        for (int c : cost) total += c;
        assertTrue(solution.minCostClimbingStairs(cost) <= total);
    }


    // Task 4: Property-based tests using jqwik

    // Result must always be non-negative for any valid input
    @Property
    void resultAlwaysNonNegative(
            @ForAll @Size(min = 2, max = 50) List<@IntRange(min = 0, max = 999) Integer> costList) {
        int[] cost = toArray(costList);
        assertTrue(solution.minCostClimbingStairs(cost) >= 0);
    }

    // Result can never exceed the sum of all costs (worst case: pay every step)
    @Property
    void resultNeverExceedsTotalCost(
            @ForAll @Size(min = 2, max = 50) List<@IntRange(min = 0, max = 999) Integer> costList) {
        int[] cost = toArray(costList);
        int total = costList.stream().mapToInt(Integer::intValue).sum();
        assertTrue(solution.minCostClimbingStairs(cost) <= total);
    }

    // For a two-element array, the answer is simply the cheaper of the two steps
    @Property
    void twoElementResultIsMinOfBoth(
            @ForAll @IntRange(min = 0, max = 999) int a,
            @ForAll @IntRange(min = 0, max = 999) int b) {
        int[] cost = {a, b};
        assertEquals(Math.min(a, b), solution.minCostClimbingStairs(cost));
    }

    // Scaling all costs by the same factor should scale the result by the same factor
    @Property
    void resultScalesWithCosts(
            @ForAll @Size(min = 2, max = 20) List<@IntRange(min = 0, max = 99) Integer> costList,
            @ForAll @IntRange(min = 1, max = 5) int factor) {
        int[] original = toArray(costList);
        int[] scaled = costList.stream().mapToInt(v -> v * factor).toArray();
        assertEquals(
                solution.minCostClimbingStairs(original) * factor,
                solution.minCostClimbingStairs(scaled)
        );
    }

    // Helper

    private int[] toArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}