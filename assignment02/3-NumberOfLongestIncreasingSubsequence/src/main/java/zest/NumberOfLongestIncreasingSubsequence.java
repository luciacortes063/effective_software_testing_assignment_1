package zest;

import java.util.Arrays;

public class NumberOfLongestIncreasingSubsequence {
    public int findNumberOfLIS(int[] nums) {
        // Pre-conditions
        if (nums == null) {
            throw new IllegalArgumentException("nums must not be null");
        }
        if (nums.length < 1 || nums.length > 2000) {
            throw new IllegalArgumentException("nums length must be between 1 and 2000, got: " + nums.length);
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < -1_000_000 || nums[i] > 1_000_000) {
                throw new IllegalArgumentException(
                        "nums[" + i + "] must be in [-10^6, 10^6], got: " + nums[i]);
            }
        }

        int n = nums.length;
        int[] lengths = new int[n];
        int[] counts = new int[n];
        // Bug: lengths and counts were filled with 0. Each element is itself a length-1
        // subsequence with count 1, so both must start at 1 (otherwise the result is always 0).
        Arrays.fill(lengths, 1);
        Arrays.fill(counts, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (lengths[j] + 1 > lengths[i]) {
                        lengths[i] = lengths[j] + 1;
                        counts[i] = counts[j];
                    } else if (lengths[j] + 1 == lengths[i]) {
                        counts[i] += counts[j];
                    }
                }
            }
            // Invariant: each entry must remain a valid LIS length / count, i.e. >= 1
            if (lengths[i] < 1 || counts[i] < 1) {
                throw new IllegalStateException(
                        "invariant violated at i=" + i + ": lengths=" + lengths[i] + ", counts=" + counts[i]);
            }
        }

        int maxLength = 0;
        for (int length : lengths) {
            if (length > maxLength) {
                maxLength = length;
            }
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            if (lengths[i] == maxLength) {
                result += counts[i];
            }
        }

        // Post-condition: at least one LIS exists for any non-empty input
        if (result < 1) {
            throw new IllegalStateException("post-condition violated: result must be >= 1, got " + result);
        }

        return result;
    }
}
