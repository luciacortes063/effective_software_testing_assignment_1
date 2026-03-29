package zest;

/**
 * Utility class to validate if a given integer is a valid color (0, 1, or 2).
 */
public class ValidColor {
    /**
     * Checks if the given color is valid (0, 1, or 2).
     *
     * @param color the color to validate
     * @return true if the color is valid, false otherwise
     */
    public static boolean isValidColor(int color) {
        return color == 0 || color == 1 || color == 2;
    }
}
