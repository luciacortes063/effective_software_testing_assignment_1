package zest;

public class BestTimeToBuyAndSellStock {

    /**
     * Returns the maximum profit that can be achieved by
     * buying on one day and selling on a later day.
     *
     * @param prices array of stock prices
     * @return maximum possible profit, or 0 if no profit is possible
     * @throws IllegalArgumentException if prices is null or empty
     */
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }

        int minPrice = 0;
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {

            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                int profit = prices[i] - minPrice;
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }

        return maxProfit;
    }

}