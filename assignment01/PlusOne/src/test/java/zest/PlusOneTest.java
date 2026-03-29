package zest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PlusOneTest {
    @Test
    void nullInputArrayThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> PlusOne.plusOne(null));
    }

    @Test
    void emptyInputArrayThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> PlusOne.plusOne(new int[]{}));
    }

    @Test
    void singleDigitIncrement() {
        assertArrayEquals(new int[]{2}, PlusOne.plusOne(new int[]{1})); 
    }

    @Test
    void singleDigitNineIncrement() {
        assertArrayEquals(new int[]{1, 0}, PlusOne.plusOne(new int[]{9})); 
    }

    @Test
    void multipleDigitsIncrement() {
        assertArrayEquals(new int[]{1, 2, 4}, PlusOne.plusOne(new int[]{1, 2, 3})); 
    }
    
    @Test
    void multipleCarries() {
        assertArrayEquals(new int[]{1, 0, 0}, PlusOne.plusOne(new int[]{9, 9})); 
    }
}