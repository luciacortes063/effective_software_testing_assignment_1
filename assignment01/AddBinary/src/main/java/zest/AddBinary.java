package zest;

public class AddBinary {

    /**
     * Adds two binary strings and returns the result as a binary string.
     *
     * @param a first binary string
     * @param b second binary string
     * @return binary sum of a and b
     * @throws IllegalArgumentException if a or b is null
     */
    public static String addBinary(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Input strings cannot be null");
        }

        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0) {

            int sum = carry;

            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }

            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }

            result.append(sum % 2);
            carry = sum / 2;
        }

        if (carry > 0) {
            result.append(carry);
        } // Bug fix: After the loop, there may still be a carry left over.
        // For example 1 + 1: the loop writes 0 and exits with carry = 1.
        // Without this, that carry is lost and the result would be 0 instead of 10

        return result.reverse().toString();
    }

}