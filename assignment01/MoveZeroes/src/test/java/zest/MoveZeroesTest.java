package zest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoveZeroesTest {

    // Specification-based tests

    // Null input: the Javadoc contract throws IllegalArgumentException.
    @Test
    void nullInputThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> MoveZeroes.moveZeroes(null));
    }

    // README example: mixed zeros and non-zeros. This test revealed the bug.
    @Test
    void readmeExample() {
        int[] nums = {0, 1, 0, 3, 12};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{1, 3, 12, 0, 0}, nums);
    }

    // No zeros at all: the array should remain unchanged.
    @Test
    void noZeroes() {
        int[] nums = {1, 2, 3};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{1, 2, 3}, nums);
    }

    // All zeros: nothing to move forward, array stays the same.
    @Test
    void allZeroes() {
        int[] nums = {0, 0, 0};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{0, 0, 0}, nums);
    }

    // Single non-zero element.
    @Test
    void singleNonZero() {
        int[] nums = {5};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{5}, nums);
    }

    // Single zero element.
    @Test
    void singleZero() {
        int[] nums = {0};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{0}, nums);
    }

    // Zero at the start, non-zero at the end.
    @Test
    void zeroAtStart() {
        int[] nums = {0, 1};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{1, 0}, nums);
    }

    // Non-zero at the start, zero at the end: already in correct order.
    @Test
    void zeroAtEnd() {
        int[] nums = {1, 0};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{1, 0}, nums);
    }

    // Multiple consecutive zeros followed by non-zero elements.
    @Test
    void consecutiveZeroes() {
        int[] nums = {0, 0, 1, 2};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{1, 2, 0, 0}, nums);
    }

    // Negative numbers mixed with zeros: relative order of non-zeros must be preserved.
    @Test
    void negativeNumbers() {
        int[] nums = {0, -1, 0, 3};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{-1, 3, 0, 0}, nums);
    }

    // Empty array: nothing to do, should not throw.
    @Test
    void emptyArray() {
        int[] nums = {};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{}, nums);
    }

    // Last element is non-zero with zeros before it. Also reveals the bug.
    @Test
    void lastElementIsNonZero() {
        int[] nums = {0, 0, 5};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{5, 0, 0}, nums);
    }

    // Structural tests

    // Alternating pattern: zero, non-zero, zero, non-zero.
    // Forces the first pass to skip and copy on alternating iterations.
    @Test
    void alternatingZeroNonZero() {
        int[] nums = {0, 1, 0, 2, 0, 3};
        MoveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{1, 2, 3, 0, 0, 0}, nums);
    }
}
