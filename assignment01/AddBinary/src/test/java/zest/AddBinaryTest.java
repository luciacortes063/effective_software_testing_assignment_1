package zest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddBinaryTest {

    // Specification-based tests
    // Null inputs: the specification says that null is not allowed -> we expect an exception if one of the inputs is null.
    // So we make 2 tests where one of the inputs is null
    @Test
    void nullAThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> AddBinary.addBinary(null, "1"));
    }

    @Test
    void nullBThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> AddBinary.addBinary("1", null));
    }

    // Both inputs are "0" -> the simplest possible case.
    @Test
    void bothZero() {
        assertEquals("0", AddBinary.addBinary("0", "0"));
    }

    // One input is "0" -> so the result should equal the other input. We make 2 test cases to measure a = 0 and b = 0
    // and a = 1 and b = 1
    @Test
    void addZeroToOne() {
        assertEquals("1", AddBinary.addBinary("0", "1"));
    }

    @Test
    void addOneToZero() {
        assertEquals("1", AddBinary.addBinary("1", "0"));
    }


    // This test tests the boundary between "no carry" and "carry left over after the loop", revealing the bug in AddBinary.java
    // 1 + 1 = 10 in binary-> so the result needs one more digit than the inputs
    @Test
    void singleBitCarry() {
        assertEquals("10", AddBinary.addBinary("1", "1"));
    }

    // Test from the example given in the Readme: 11 + 1 = 100
    @Test
    void readmeExample() {
        assertEquals("100", AddBinary.addBinary("11", "1"));
    }

    // Equal-length inputs where an intermediate carry is generated
    // but there is also a final carry: 10 + 11 = 101 (2 + 3 = 5)
    @Test
    void equalLengthWithCarry() {
        assertEquals("101", AddBinary.addBinary("10", "11"));
    }

    // a is longer than b: 1010 + 11 = 1101 (10 + 3 = 13)
    @Test
    void aLongerThanB() {
        assertEquals("1101", AddBinary.addBinary("1010", "11"));
    }

    // b is longer than a
    @Test
    void bLongerThanA() {
        assertEquals("1101", AddBinary.addBinary("11", "1010"));
    }

    // Every column produces a carry, one after another: 1111 + 1 = 10000 (15 + 1 = 16)
    @Test
    void multipleConsecutiveCarries() {
        assertEquals("10000", AddBinary.addBinary("1111", "1"));
    }

    // Carry generated in the middle of the addition, not just at the end:
    // 111 + 111 = 1110 (7 + 7 = 14)
    @Test
    void carryInMiddle() {
        assertEquals("1110", AddBinary.addBinary("111", "111"));
    }


    // Structural tests

    // Forces the i >= 0 branch to be false first (b is much longer),
    // so the inner "if (i >= 0)" block is skipped for several iterations.
    @Test
    void onlyBContributesForSeveralBits() {
        assertEquals("1011", AddBinary.addBinary("1", "1010"));
    }

    // Forces the j >= 0 branch to be false first (a is much longer).
    @Test
    void onlyAContributesForSeveralBits() {
        assertEquals("1011", AddBinary.addBinary("1010", "1"));
    }
}