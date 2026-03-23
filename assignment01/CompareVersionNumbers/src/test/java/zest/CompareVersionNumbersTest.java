package zest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompareVersionNumbersTest {

    // Specification-based tests

    // Null inputs should throw an exception.
    @Test
    void nullVersion1ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> CompareVersionNumbers.compareVersion(null, "1.0"));
    }

    @Test
    void nullVersion2ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> CompareVersionNumbers.compareVersion("1.0", null));
    }

    // Both versions are exactly equal.
    @Test
    void equalVersions() {
        assertEquals(0, CompareVersionNumbers.compareVersion("1.0", "1.0"));
    }

    // Example from the README: leading zeros should be ignored, so "1.01" == "1.001".
    @Test
    void leadingZerosAreIgnored() {
        assertEquals(0, CompareVersionNumbers.compareVersion("1.01", "1.001"));
    }

    // version1 is greater at the first revision.
    // Expected: 1. With the bug it returns -1.
    @Test
    void version1GreaterAtFirstRevision() {
        assertEquals(1, CompareVersionNumbers.compareVersion("2.0", "1.0"));
    }

    // version1 is smaller at the first revision.
    // Expected: -1. With the bug it returns 1.
    @Test
    void version1SmallerAtFirstRevision() {
        assertEquals(-1, CompareVersionNumbers.compareVersion("1.0", "2.0"));
    }

    // Versions differ at a later revision, not the first.
    @Test
    void differAtSecondRevision() {
        assertEquals(1, CompareVersionNumbers.compareVersion("1.2", "1.1"));
    }

    @Test
    void differAtThirdRevision() {
        assertEquals(-1, CompareVersionNumbers.compareVersion("1.0.1", "1.0.2"));
    }

    // Different number of revisions -> missing ones are treated as 0.
    @Test
    void version1HasMoreRevisions() {
        assertEquals(1, CompareVersionNumbers.compareVersion("1.0.1", "1.0"));
    }

    @Test
    void version2HasMoreRevisions() {
        assertEquals(-1, CompareVersionNumbers.compareVersion("1.0", "1.0.1"));
    }

    // Trailing zeros -> 1.0.0.0 should equal 1.0
    @Test
    void trailingZerosAreEqual() {
        assertEquals(0, CompareVersionNumbers.compareVersion("1.0.0.0", "1.0"));
    }

    // Single revision versions
    @Test
    void singleRevisionEqual() {
        assertEquals(0, CompareVersionNumbers.compareVersion("1", "1"));
    }

    @Test
    void singleRevisionGreater() {
        assertEquals(1, CompareVersionNumbers.compareVersion("2", "1"));
    }

    @Test
    void singleRevisionSmaller() {
        assertEquals(-1, CompareVersionNumbers.compareVersion("1", "2"));
    }

    // Large revision numbers in the 32-bit integer range.
    @Test
    void largeRevisionNumbers() {
        assertEquals(1, CompareVersionNumbers.compareVersion("1.2147483647", "1.0"));
    }

    // Structural tests

    // Forces i < v1Parts.length to return 0 (version1 is shorter).
    @Test
    void version1ShorterThanVersion2() {
        assertEquals(-1, CompareVersionNumbers.compareVersion("1", "1.1"));
    }

    // Forces i < v2Parts.length to return 0 (version2 is shorter).
    @Test
    void version2ShorterThanVersion1() {
        assertEquals(1, CompareVersionNumbers.compareVersion("1.1", "1"));
    }
}