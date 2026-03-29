package zest;

public class ValidPerfectSquare {

    /**
     * Returns true if num is a perfect square, false otherwise.
     *
     * @param num a positive integer
     * @return true if num is a perfect square
     * @throws IllegalArgumentException if num <= 0
     */
    public static boolean isPerfectSquare(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Input must be positive");
        }

        if (num == 1) {
            return true;
        }

        long left = 1;
        long right = num;

        // It needs to run the last iteration where left == right
        while (left <= right) {
            long mid = left + (right - left) / 2;
            long square = mid * mid;

            if (square == num) {
                return true;
            } else if (square < num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

}