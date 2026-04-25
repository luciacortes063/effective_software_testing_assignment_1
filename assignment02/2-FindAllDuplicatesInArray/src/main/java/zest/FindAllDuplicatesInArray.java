package zest;

import java.util.ArrayList;
import java.util.List;

public class FindAllDuplicatesInArray {

    public List<Integer> findDuplicates(int[] nums) {
        // Pre-conditions
        if (nums == null) {
            throw new IllegalArgumentException("nums must not be null");
        }
        if (nums.length < 1 || nums.length > 100_000) {
            throw new IllegalArgumentException("nums length must be between 1 and 100000, got: " + nums.length);
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 1 || nums[i] > nums.length) {
                throw new IllegalArgumentException(
                        "nums[" + i + "] must be in [1, " + nums.length + "], got: " + nums[i]);
            }
        }

        List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;

            // Invariant: index must always be a valid position in the array
            if (index < 0 || index >= nums.length) {
                throw new IllegalStateException("invariant violated: index " + index + " is out of bounds");
            }

            if (nums[index] < 0) {
                duplicates.add(Math.abs(nums[i]));
            } else {
                nums[index] = -nums[index];
            }
        }

        // Post-condition: every element in the result must be in [1, n]
        for (int d : duplicates) {
            if (d < 1 || d > nums.length) {
                throw new IllegalStateException("post-condition violated: result contains value out of range: " + d);
            }
        }

        return duplicates;
    }
}