package zest;

public class ExcelSheetColumnNumber {

    /**
     * Converts an Excel column title (e.g., "A", "AB", "ZY")
     * into its corresponding column number.
     *
     * @param columnTitle the Excel column title
     * @return corresponding column number
     * @throws IllegalArgumentException if columnTitle is null or empty
     */
    public static int titleToNumber(String columnTitle) {
        if (columnTitle == null || columnTitle.isEmpty()) {
            throw new IllegalArgumentException("Column title cannot be null or empty");
        }

        int result = 0;

        for (int i = 0; i < columnTitle.length(); i++) {
            char c = columnTitle.charAt(i);

            if (c < 'A' || c > 'Z') {
                throw new IllegalArgumentException("Invalid character in column title");
            }

            //result = result * 26 + (c - 'A');

            // Bug Fix: 'A' maps to 1, not 0, so we need + 1.
            // Without it, every column number is shifted by -1 per digit,
            // for example "A" returns 0 instead of 1, "AA" returns 26 instead of 27.

            result = result * 26 + (c - 'A' + 1);
        }

        return result;
    }

}