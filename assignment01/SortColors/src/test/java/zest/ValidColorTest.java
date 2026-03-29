package zest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValidColorTest {
    @Test
    void validColorZeroReturnsTrue() {
        assertTrue(ValidColor.isValidColor(0));
    }

    @Test
    void validColorOneReturnsTrue() {
        assertTrue(ValidColor.isValidColor(1));
    }

    @Test
    void validColorTwoReturnsTrue() {
        assertTrue(ValidColor.isValidColor(2));
    }

    @Test
    void negativeValueReturnsFalse() {
        assertFalse(ValidColor.isValidColor(-1));
    }

    @Test
    void valueGreaterThanTwoReturnsFalse() {
        assertFalse(ValidColor.isValidColor(3));
    }
}