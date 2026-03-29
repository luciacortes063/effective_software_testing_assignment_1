package zest;

public class ReverseInteger {

    /**
     * Reverses the digits of a signed 32-bit integer.
     * Returns 0 if the reversed value overflows.
     *
     * @param x the integer to reverse
     * @return reversed integer or 0 if overflow occurs
     */
    public static int reverse(int x) {
        int reversed = 0;

        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            // Check for overflow before multiplying by 10
            if (reversed > Integer.MAX_VALUE / 10) {
                return 0;
            }

            // Check for underflow
            if (reversed < Integer.MIN_VALUE / 10) {
                return 0;
            }

            reversed = reversed * 10 + digit;
        }

        return reversed;
    }

}