package zest;

public class SortColors {

    /**
     * Sorts the array in-place so that all 0s come first,
     * followed by 1s, then 2s.
     *
     * @param nums the input array containing only 0, 1, and 2
     * @throws IllegalArgumentException if nums is null
     * @throws IllegalArgumentException if nums contains invalid values
     */
    public static void sortColors(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        // Ensure all elements are valid (0, 1, or 2)
        for (int num : nums) {
            if (ValidColor.isValidColor(num) == false) {
                throw new IllegalArgumentException("Input array can only contain 0, 1, and 2");
            }
        }


        int low = 0;               // boundary for 0s
        int mid = 0;               // current index
        int high = nums.length - 1; // boundary for 2s

        while (mid <= high) {

            if (nums[mid] == 0) {
                swap(nums, low, mid);
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else { // nums[mid] == 2
                swap(nums, mid, high);
                high--;
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}