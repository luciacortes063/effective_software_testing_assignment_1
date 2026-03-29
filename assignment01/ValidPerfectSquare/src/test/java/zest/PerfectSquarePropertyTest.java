package zest;

import net.jqwik.api.ForAll;
import net.jqwik.api.Example;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import static org.junit.jupiter.api.Assertions.*;

class PerfectSquarePropertyTest {

    @Example
    void negativeNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> ValidPerfectSquare.isPerfectSquare(-1));
    }

    @Property
    // The largest perfect square that fits in a 32-bit signed integer is 46340^2 = 2147395600
    void validPerfectSquaresReturnTrue(@ForAll @IntRange(min = 1, max = 46340) int x) {
        int perfectSquare = x * x; 
        
        assertTrue(ValidPerfectSquare.isPerfectSquare(perfectSquare));
    }

    @Property
    void nonPerfectSquaresReturnFalse(@ForAll @IntRange(min = 1, max = 46340) int x) {
        int nonPerfectSquare = x * x + 1;

        assertFalse(ValidPerfectSquare.isPerfectSquare(nonPerfectSquare));
    }
}