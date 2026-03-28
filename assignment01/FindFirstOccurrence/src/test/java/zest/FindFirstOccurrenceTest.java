package zest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FindFirstOccurrenceTest {

    // Specification-based tests

    // Null inputs: the Javadoc contract throws IllegalArgumentException for null.
    @Test
    void nullHaystackThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> FindFirstOccurrence.strStr(null, "abc"));
    }

    @Test
    void nullNeedleThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> FindFirstOccurrence.strStr("abc", null));
    }

    // README example: needle at the beginning of haystack.
    @Test
    void needleAtStart() {
        assertEquals(0, FindFirstOccurrence.strStr("sadbutsad", "sad"));
    }

    // Needle appears only at the end of haystack.
    @Test
    void needleAtEnd() {
        assertEquals(3, FindFirstOccurrence.strStr("butsad", "sad"));
    }

    // Needle is somewhere in the middle.
    @Test
    void needleInMiddle() {
        assertEquals(2, FindFirstOccurrence.strStr("abcdef", "cde"));
    }

    // Needle is not present in haystack at all.
    @Test
    void needleNotFound() {
        assertEquals(-1, FindFirstOccurrence.strStr("hello", "xyz"));
    }

    // Needle is longer than haystack, so it cannot possibly be found.
    @Test
    void needleLongerThanHaystack() {
        assertEquals(-1, FindFirstOccurrence.strStr("ab", "abcd"));
    }

    // Needle and haystack are exactly the same string.
    @Test
    void needleEqualsHaystack() {
        assertEquals(0, FindFirstOccurrence.strStr("abc", "abc"));
    }

    // Both are single characters and they match.
    @Test
    void singleCharFound() {
        assertEquals(0, FindFirstOccurrence.strStr("a", "a"));
    }

    // Both are single characters and they do not match.
    @Test
    void singleCharNotFound() {
        assertEquals(-1, FindFirstOccurrence.strStr("a", "b"));
    }

    // Multiple occurrences of needle: the method must return the first one.
    @Test
    void multipleOccurrencesReturnsFirst() {
        assertEquals(0, FindFirstOccurrence.strStr("abcabc", "abc"));
    }

    // A partial match at index 0 that does not succeed, followed by a real match.
    // "aab" contains "ab" starting at index 1, not index 0.
    @Test
    void partialMatchThenFullMatch() {
        assertEquals(1, FindFirstOccurrence.strStr("aab", "ab"));
    }

    // Structural tests

    // Covers the inner while loop exiting because of a character mismatch
    // at different positions within the needle.
    @Test
    void mismatchInMiddleOfNeedle() {
        assertEquals(-1, FindFirstOccurrence.strStr("abcxyz", "abcz"));
    }
}
