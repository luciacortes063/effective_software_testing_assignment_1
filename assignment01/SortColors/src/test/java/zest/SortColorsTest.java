package zest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SortColorsTest {
    @Test
    void nullArrayThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> SortColors.sortColors(null));
    }

    @Test
    void emptyInputArray() {
        int[] input = new int[]{};
        SortColors.sortColors(input);
        assertArrayEquals(new int[]{}, input);
    }

    @Test
    void invalidPositiveInputArrayThrowsException() {
        int[] input = new int[]{0, 1, 3}; // 3 is invalid
        assertThrows(IllegalArgumentException.class, () -> SortColors.sortColors(input));
    }

    @Test
    void invalidNegativeInputArrayThrowsException() {
        int[] input = new int[]{0, -1, 1}; // -1 is invalid
        assertThrows(IllegalArgumentException.class, () -> SortColors.sortColors(input));
    }

    @Test
    void leftSwap() {
        int[] inputA = new int[]{1, 0};
        SortColors.sortColors(inputA);
        assertArrayEquals(new int[]{0, 1}, inputA);
    }

    @Test
    void rightSwap() {
        int[] inputB = new int[]{2, 1};
        SortColors.sortColors(inputB);
        assertArrayEquals(new int[]{1, 2}, inputB);
    }

    @Test
    void alreadySortedArray() {
        int[] input = new int[]{0, 0, 1, 1, 2, 2};
        SortColors.sortColors(input);
        assertArrayEquals(new int[]{0, 0, 1, 1, 2, 2}, input);
    }

    @Test
    void reverseSortedArray() {
        int[] input = new int[]{2, 2, 1, 1, 0, 0};
        SortColors.sortColors(input);
        assertArrayEquals(new int[]{0, 0, 1, 1, 2, 2}, input);
    }

    @Test
    void mixedArray() {
        int[] input = new int[]{2, 0, 2, 1, 1, 0};
        SortColors.sortColors(input);
        assertArrayEquals(new int[]{0, 0, 1, 1, 2, 2}, input);
    }

    @Test
    void singleElementArray() {
        int[] input = new int[]{1};
        SortColors.sortColors(input);
        assertArrayEquals(new int[]{1}, input);
    }
}