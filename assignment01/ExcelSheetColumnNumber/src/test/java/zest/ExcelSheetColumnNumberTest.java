package zest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExcelSheetColumnNumberTest {

    // Specification-based tests

    // Null and empty inputs should throw an exception.
    @Test
    void nullInputThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> ExcelSheetColumnNumber.titleToNumber(null));
    }

    @Test
    void emptyInputThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> ExcelSheetColumnNumber.titleToNumber(""));
    }

    // Invalid characters (not A-Z) should throw an exception.
    @Test
    void invalidCharacterThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> ExcelSheetColumnNumber.titleToNumber("A1"));
    }

    @Test
    void lowercaseThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> ExcelSheetColumnNumber.titleToNumber("a"));
    }

    // Boundary -> first column "A" must return 1, not 0.
    // This is the simplest test that exposes the bug.
    @Test
    void singleA() {
        assertEquals(1, ExcelSheetColumnNumber.titleToNumber("A"));
    }

    // Last single-letter column.
    @Test
    void singleZ() {
        assertEquals(26, ExcelSheetColumnNumber.titleToNumber("Z"));
    }

    // Boundary between single and double letters: "AA" = 27.
    @Test
    void doubleAA() {
        assertEquals(27, ExcelSheetColumnNumber.titleToNumber("AA"));
    }

    @Test
    void doubleAB() {
        assertEquals(28, ExcelSheetColumnNumber.titleToNumber("AB"));
    }

    // "ZZ" is the last column with 2 letters: 26*26 + 26 = 702.
    @Test
    void doubleZZ() {
        assertEquals(702, ExcelSheetColumnNumber.titleToNumber("ZZ"));
    }

    // Example from the README: "ZY" = 701.
    @Test
    void readmeExample() {
        assertEquals(701, ExcelSheetColumnNumber.titleToNumber("ZY"));
    }

    // A few values in the middle of the range to check the base-26 arithmetic.
    @Test
    void singleB() {
        assertEquals(2, ExcelSheetColumnNumber.titleToNumber("B"));
    }

    @Test
    void tripleAAA() {
        // A*26^2 + A*26 + A = 676 + 26 + 1 = 703
        assertEquals(703, ExcelSheetColumnNumber.titleToNumber("AAA"));
    }

    // Maximum allowed input from the constraints: "FXSHRXW".
    @Test
    void maximumInput() {
        assertEquals(2147483647, ExcelSheetColumnNumber.titleToNumber("FXSHRXW"));
    }

    // Structural tests

    // Forces the invalid character branch with a character just below 'A'.
    @Test
    void characterJustBelowA() {
        assertThrows(IllegalArgumentException.class,
                () -> ExcelSheetColumnNumber.titleToNumber("@"));
    }

    // Forces the invalid character branch with a character just above 'Z'.
    @Test
    void characterJustAboveZ() {
        assertThrows(IllegalArgumentException.class,
                () -> ExcelSheetColumnNumber.titleToNumber("["));
    }
}