package zest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LongestCommonPrefixTest {

    // Specification-based tests

    // Null input: the Javadoc contract throws IllegalArgumentException.
    @Test
    void nullInputThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> LongestCommonPrefix.longestCommonPrefix(null));
    }

    // Empty array: no strings at all, so no common prefix.
    @Test
    void emptyArray() {
        assertEquals("", LongestCommonPrefix.longestCommonPrefix(new String[]{}));
    }

    // Single string: the prefix is the string itself.
    @Test
    void singleString() {
        assertEquals("abc", LongestCommonPrefix.longestCommonPrefix(new String[]{"abc"}));
    }

    // README example: three strings with a two-character common prefix.
    @Test
    void readmeExample() {
        assertEquals("fl", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"flower", "flow", "flight"}));
    }

    // No common prefix at all: the first characters already differ.
    @Test
    void noCommonPrefix() {
        assertEquals("", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"dog", "racecar", "car"}));
    }

    // All strings are identical: the prefix is the full string.
    @Test
    void allIdentical() {
        assertEquals("abc", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"abc", "abc", "abc"}));
    }

    // One of the strings is empty: the common prefix must be "".
    @Test
    void oneEmptyString() {
        assertEquals("", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"", "abc", "ab"}));
    }

    // Two strings where one is a prefix of the other.
    @Test
    void oneIsPrefixOfOther() {
        assertEquals("inter", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"inter", "interview"}));
    }

    // Common prefix is a single character.
    @Test
    void prefixIsOneChar() {
        assertEquals("a", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"a", "ab", "abc"}));
    }

    // First string is the shortest: prefix is limited by it.
    @Test
    void firstStringIsShortest() {
        assertEquals("ab", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"ab", "abc", "abcd"}));
    }

    // First string is the longest: prefix shrinks based on shorter strings.
    @Test
    void firstStringIsLongest() {
        assertEquals("ab", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"abcd", "abc", "ab"}));
    }

    // Structural tests

    // The empty string is in the middle of the array (not at the start),
    // forcing the prefix to shrink all the way to "" via the while loop.
    @Test
    void emptyStringInMiddle() {
        assertEquals("", LongestCommonPrefix.longestCommonPrefix(
                new String[]{"abc", "", "ab"}));
    }
}
