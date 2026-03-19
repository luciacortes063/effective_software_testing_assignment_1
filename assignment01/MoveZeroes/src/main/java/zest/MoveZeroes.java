package zest;

public class MoveZeroes {

    /**
     * Moves all zero values in the array to the end,
     * preserving the relative order of non-zero elements.
     * The operation is performed in-place.
     * @param numbers the array to modify
     * @throws IllegalArgumentException if numbers is null
     */
    public static void moveZeroes(int[] numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        int insertPosition = 0;

        // First pass: move all non-zero elements forward
        for (int i = 0; i < numbers.length-1; i++) {
            if (numbers[i] != 0) {
                numbers[insertPosition] = numbers[i];
                insertPosition++;
            }
        }

        // Second pass: fill remaining positions with zero
        while (insertPosition < numbers.length) {
            numbers[insertPosition] = 0;
            insertPosition++;
        }
    }

}