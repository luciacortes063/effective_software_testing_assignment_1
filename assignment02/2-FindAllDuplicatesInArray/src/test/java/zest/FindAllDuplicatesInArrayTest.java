package zest;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FindAllDuplicatesInArrayTest {

    private FindAllDuplicatesInArray solution = new FindAllDuplicatesInArray();

    // Task 1: Coverage

    @Test
    void testExample1FromReadme() {
        // Example 1 from Readme. [4,3,2,7,8,2,3,1] -> duplicates are 2 and 3
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> result = solution.findDuplicates(nums);
        assertEquals(new HashSet<>(Arrays.asList(2, 3)), new HashSet<>(result));
    }

    @Test
    void testExample2FromReadme() {
        // Example 2 from Readme. [1,1,2] -> 1 appears twice
        int[] nums = {1, 1, 2};
        List<Integer> result = solution.findDuplicates(nums);
        assertEquals(List.of(1), result);
    }

    @Test
    void testExample3FromReadme() {
        // single element, no duplicates possible
        int[] nums = {1};
        assertTrue(solution.findDuplicates(nums).isEmpty());
    }

    @Test
    void testNoDuplicates() {
        // all elements appear once -> empty result
        int[] nums = {1, 2, 3, 4, 5};
        assertTrue(solution.findDuplicates(nums).isEmpty());
    }

    @Test
    void testAllDuplicates() {
        // every element appears twice
        int[] nums = {1, 1, 2, 2, 3, 3};
        List<Integer> result = solution.findDuplicates(nums);
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3)), new HashSet<>(result));
    }

    @Test
    void testSingleDuplicate() {
        int[] nums = {2, 1, 2};
        List<Integer> result = solution.findDuplicates(nums);
        assertEquals(List.of(2), result);
    }

    @Test
    void testTwoElements_noDuplicate() {
        int[] nums = {1, 2};
        assertTrue(solution.findDuplicates(nums).isEmpty());
    }

    @Test
    void testTwoElements_withDuplicate() {
        int[] nums = {2, 2};
        List<Integer> result = solution.findDuplicates(nums);
        assertEquals(List.of(2), result);
    }

    // Task 3 Contract tests: pre-conditions

    @Test
    void testNullInputThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> solution.findDuplicates(null));
    }

    @Test
    void testEmptyArrayThrows() {
        // length 0 is below the minimum of 1
        assertThrows(IllegalArgumentException.class,
                () -> solution.findDuplicates(new int[]{}));
    }

    @Test
    void testArrayTooLongThrows() {
        // length 100001 exceeds the maximum of 100000
        assertThrows(IllegalArgumentException.class,
                () -> solution.findDuplicates(new int[100_001]));
    }

    @Test
    void testElementZeroThrows() {
        // 0 is below the valid range [1, n]
        int[] nums = {1, 0, 2};
        assertThrows(IllegalArgumentException.class,
                () -> solution.findDuplicates(nums));
    }

    @Test
    void testElementAboveNThrows() {
        // element 4 in an array of length 3 is out of range
        int[] nums = {1, 2, 4};
        assertThrows(IllegalArgumentException.class,
                () -> solution.findDuplicates(nums));
    }

    @Test
    void testNegativeElementThrows() {
        int[] nums = {1, -1, 2};
        assertThrows(IllegalArgumentException.class,
                () -> solution.findDuplicates(nums));
    }

    // Task 3 Contract tests: post-conditions

    @Test
    void testResultIsNotNull() {
        int[] nums = {1, 2, 3};
        assertNotNull(solution.findDuplicates(nums));
    }

    @Test
    void testResultElementsAreInValidRange() {
        // every value in the result must be in [1, n]
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        int n = nums.length;
        for (int d : solution.findDuplicates(nums)) {
            assertTrue(d >= 1 && d <= n);
        }
    }

    @Test
    void testResultSizeAtMostHalfN() {
        // can't have more duplicates than floor(n/2)
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        assertTrue(solution.findDuplicates(nums).size() <= nums.length / 2);
    }

    @Test
    void testNoDuplicatesInResult() {
        // each duplicate value should appear only once in the result list
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> result = solution.findDuplicates(nums);
        assertEquals(result.size(), new HashSet<>(result).size());
    }

    // Task 4 Property-based tests using jqwik

    // Every value in the result appeared exactly twice in the input
    @Property
    void everyResultElementAppearedTwiceInInput(@ForAll("validInputs") int[] nums) {
        int[] copy = nums.clone();
        List<Integer> result = solution.findDuplicates(copy);

        for (int d : result) {
            long count = 0;
            for (int v : nums) if (v == d) count++;
            assertEquals(2, count, d + " should appear exactly twice in input");
        }
    }

    // Elements that appear only once must not end up in the result
    @Property
    void uniqueElementsNotInResult(@ForAll("validInputs") int[] nums) {
        // count occurrences in original
        Map<Integer, Long> freq = new HashMap<>();
        for (int v : nums) freq.merge(v, 1L, Long::sum);

        int[] copy = nums.clone();
        List<Integer> result = solution.findDuplicates(copy);
        Set<Integer> resultSet = new HashSet<>(result);

        for (Map.Entry<Integer, Long> e : freq.entrySet()) {
            if (e.getValue() == 1) {
                assertFalse(resultSet.contains(e.getKey()),
                        e.getKey() + " appears once so should not be in result");
            }
        }
    }

    // Result must have no repeated values
    @Property
    void resultHasNoDuplicateValues(@ForAll("validInputs") int[] nums) {
        List<Integer> result = solution.findDuplicates(nums.clone());
        assertEquals(result.size(), new HashSet<>(result).size());
    }

    // Result size can never exceed floor(n/2)
    @Property
    void resultSizeAtMostHalfN(@ForAll("validInputs") int[] nums) {
        List<Integer> result = solution.findDuplicates(nums.clone());
        assertTrue(result.size() <= nums.length / 2);
    }

    // Arbitrary: generate valid inputs where each value is in [1, n]
    // and appears at most twice

    @Provide
    Arbitrary<int[]> validInputs() {
        return Arbitraries.integers().between(1, 20).flatMap(n -> {
            // build a pool: each number from 1..n appears once or twice
            List<Integer> pool = new ArrayList<>();
            for (int v = 1; v <= n; v++) {
                pool.add(v);
                if (Math.random() < 0.4) pool.add(v); // ~40% chance of a duplicate
            }
            Collections.shuffle(pool);
            return Arbitraries.just(pool.stream().mapToInt(Integer::intValue).toArray());
        });
    }
}