package zest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LengthOfLastWordTest {

    // Specification-based tests

    // Null input: the Javadoc contract throws IllegalArgumentException.
    @Test
    void nullInputThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> LengthOfLastWord.lengthOfLastWord(null));
    }

    // Single word with no spaces at all.
    @Test
    void singleWord() {
        assertEquals(5, LengthOfLastWord.lengthOfLastWord("Hello"));
    }

    // README example: two words separated by a single space.
    @Test
    void twoWords() {
        assertEquals(5, LengthOfLastWord.lengthOfLastWord("Hello World"));
    }

    // Trailing spaces after the last word should be ignored.
    @Test
    void trailingSpaces() {
        assertEquals(5, LengthOfLastWord.lengthOfLastWord("Hello World   "));
    }

    // Leading spaces before the only word.
    @Test
    void leadingSpaces() {
        assertEquals(5, LengthOfLastWord.lengthOfLastWord("   Hello"));
    }

    // Multiple spaces between words; the last word is still "World".
    @Test
    void multipleSpacesBetweenWords() {
        assertEquals(5, LengthOfLastWord.lengthOfLastWord("Hello   World"));
    }

    // Single character string.
    @Test
    void singleChar() {
        assertEquals(1, LengthOfLastWord.lengthOfLastWord("a"));
    }

    // Single character surrounded by spaces.
    @Test
    void singleCharWithSpaces() {
        assertEquals(1, LengthOfLastWord.lengthOfLastWord("  a  "));
    }

    // Multiple words; last word has a different length.
    @Test
    void manyWords() {
        assertEquals(4, LengthOfLastWord.lengthOfLastWord("fly me to the moon"));
    }

    // Word followed by trailing spaces only (word is at the start).
    @Test
    void wordThenTrailingSpaces() {
        assertEquals(4, LengthOfLastWord.lengthOfLastWord("word    "));
    }

    // The last word is longer than the first word.
    @Test
    void longLastWord() {
        assertEquals(12, LengthOfLastWord.lengthOfLastWord("a verylongword"));
    }

    // Structural tests

    // Forces the trailing-space loop to consume the entire string from the right
    // before the word-count loop runs, testing the i >= 0 boundary in the first loop.
    // Then the second loop runs on a word that starts at the very beginning of the string.
    @Test
    void wordAtVeryStart() {
        assertEquals(3, LengthOfLastWord.lengthOfLastWord("abc     "));
    }
}
