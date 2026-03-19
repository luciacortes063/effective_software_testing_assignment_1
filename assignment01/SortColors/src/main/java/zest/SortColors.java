package zest;

public class SortColors {

    /**
     * Sorts the array in-place so that all 0s come first,
     * followed by 1s, then 2s.
     *
     * @param nums the input array containing only 0, 1, and 2
     * @throws IllegalArgumentException if nums is null
     */
    public static void sortColors(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
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