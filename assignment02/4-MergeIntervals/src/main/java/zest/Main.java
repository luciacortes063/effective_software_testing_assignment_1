package zest;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MergeIntervals solution = new MergeIntervals();
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] merged = solution.merge(intervals);
        System.out.println("Merged intervals: " + Arrays.deepToString(merged));
    }
}
