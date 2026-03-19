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

            result = result * 26 + (c - 'A');
        }

        return result;
    }

}