package zest;

public class LengthOfLastWord {

    /**
     * Returns the length of the last word in the given string.
     * A word is defined as a maximal substring consisting of non-space characters.
     *
     * @param s the input string
     * @return length of the last word
     * @throws IllegalArgumentException if s is null
     */
    public static int lengthOfLastWord(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        int length = 0;
        int i = s.length() - 1;

        // Skip trailing spaces
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }

        // Count characters of last word
        while (i >= 0 && s.charAt(i) != ' ') {
            length++;
            i--;
        }

        return length;
    }

}