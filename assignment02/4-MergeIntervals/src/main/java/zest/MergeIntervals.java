package zest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        // Pre-conditions
        if (intervals == null) {
            throw new IllegalArgumentException("intervals must not be null");
        }
        if (intervals.length > 10_000) {
            throw new IllegalArgumentException(
                    "intervals length must be <= 10000, got: " + intervals.length);
        }
        for (int i = 0; i < intervals.length; i++) {
            if (intervals[i] == null) {
                throw new IllegalArgumentException("intervals[" + i + "] must not be null");
            }
            if (intervals[i].length != 2) {
                throw new IllegalArgumentException(
                        "intervals[" + i + "] must have length 2, got: " + intervals[i].length);
            }
            if (intervals[i][0] > intervals[i][1]) {
                throw new IllegalArgumentException(
                        "intervals[" + i + "] must satisfy start <= end, got: ["
                                + intervals[i][0] + "," + intervals[i][1] + "]");
            }
        }

        // Bug: the original code did `int[] newInterval = intervals[0]` unconditionally,
        // which throws ArrayIndexOutOfBoundsException for empty input even though the
        // README allows length 0.
        if (intervals.length == 0) {
            return new int[0][];
        }

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> result = new ArrayList<>();
        int[] newInterval = intervals[0];
        result.add(newInterval);

        for (int[] interval : intervals) {
            if (interval[0] <= newInterval[1]) {
                newInterval[1] = Math.max(newInterval[1], interval[1]);
            } else {
                newInterval = interval;
                result.add(newInterval);
            }

            // Invariant: the most recently added interval must remain well-formed
            if (newInterval[0] > newInterval[1]) {
                throw new IllegalStateException(
                        "invariant violated: newInterval start > end ["
                                + newInterval[0] + "," + newInterval[1] + "]");
            }
        }

        int[][] merged = result.toArray(new int[result.size()][]);

        // Post-conditions:
        //   1) every output interval is well-formed (start <= end)
        //   2) the output is sorted by start
        //   3) consecutive output intervals do not overlap (next.start > prev.end)
        for (int i = 0; i < merged.length; i++) {
            if (merged[i][0] > merged[i][1]) {
                throw new IllegalStateException(
                        "post-condition violated: merged[" + i + "] is not well-formed");
            }
            if (i > 0 && merged[i][0] <= merged[i - 1][1]) {
                throw new IllegalStateException(
                        "post-condition violated: merged[" + i + "] overlaps with merged[" + (i - 1) + "]");
            }
        }

        return merged;
    }
}
