package zest;

public class Main {
    public static void main(String[] args) {
        MinCostClimbingStairs solution = new MinCostClimbingStairs();
        int[] cost = {10, 15, 20};
        System.out.println("Minimum cost to climb stairs: " + solution.minCostClimbingStairs(cost));
    }
}
