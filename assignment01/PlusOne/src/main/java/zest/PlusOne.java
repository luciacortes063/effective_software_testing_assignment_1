package zest;

public class PlusOne {

    /**
     * Increments a large integer represented as an array of digits.
     *
     * @param digits the array representing the integer
     * @return a new array representing the incremented integer
     * @throws IllegalArgumentException if digits is null or empty
     */
    public static int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }

        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            digits[i] = 0;
        }

        // If we reach here, all digits were 9 (e.g., 999)
        // We need an extra digit at the beginning
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }

}