package zest;

public class Main {
    public static void main(String[] args) {
        NumberOfLongestIncreasingSubsequence solution = new NumberOfLongestIncreasingSubsequence();
        int[] nums = {1, 3, 5, 4, 7};
        System.out.println("Number of longest increasing subsequences: " + solution.findNumberOfLIS(nums));
    }
}
