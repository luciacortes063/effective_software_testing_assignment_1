package zest;

public class MinCostClimbingStairs {

    public int minCostClimbingStairs(int[] cost) {
        // Pre-conditions
        if (cost == null) {
            throw new IllegalArgumentException("cost array must not be null");
        }
        if (cost.length < 2 || cost.length > 1000) {
            throw new IllegalArgumentException("cost array length must be between 2 and 1000, got: " + cost.length);
        }
        for (int i = 0; i < cost.length; i++) {
            if (cost[i] < 0 || cost[i] > 999) {
                throw new IllegalArgumentException("cost[" + i + "] must be between 0 and 999, got: " + cost[i]);
            }
        }

        int n = cost.length;
        int[] dp = new int[n];

        // Bug: dp[0] was incorrectly set to 0 instead of cost[0]. This caused wrong results whenever starting from step 0 is optimal.
        dp[0] = cost[0];
        dp[1] = cost[1];

        for (int i = 2; i < n; i++) {
            dp[i] = cost[i] + Math.min(dp[i - 1], dp[i - 2]);
            // Invariant: every dp value must be non-negative given valid inputs
            if (dp[i] < 0) {
                throw new IllegalStateException("invariant violated: dp[" + i + "] is negative");
            }
        }

        int result = Math.min(dp[n - 1], dp[n - 2]);

        // Post-condition: result must be non-negative
        if (result < 0) {
            throw new IllegalStateException("post-condition violated: result is negative");
        }

        return result;
    }
}