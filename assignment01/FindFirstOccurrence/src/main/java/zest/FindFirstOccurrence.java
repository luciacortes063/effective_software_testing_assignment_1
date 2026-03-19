package zest;

public class FindFirstOccurrence {

    /**
     * Returns the index of the first occurrence of needle in haystack,
     * or -1 if needle is not part of haystack.
     *
     * @param haystack the string to search in
     * @param needle the string to search for
     * @return index of first occurrence or -1
     * @throws IllegalArgumentException if haystack or needle is null
     */
    public static int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            throw new IllegalArgumentException("Input strings cannot be null");
        }

        int hLen = haystack.length();
        int nLen = needle.length();

        if (nLen > hLen) {
            return -1;
        }

        for (int i = 0; i <= hLen - nLen; i++) {
            int j = 0;

            while (j < nLen && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }

            if (j == nLen) {
                return i;
            }
        }

        return -1;
    }

}