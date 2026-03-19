## Problem: BestTimeToBuyAndSellStock

# Description
The Best Time to Buy and Sell Stock problem requires determining the maximum profit that can be achieved from a single buy-sell transaction.

You are given an integer array prices, where prices[i] represents the price of a stock on the i-th day.

You may choose one day to buy the stock and a different future day to sell it. The goal is to maximize the profit from this transaction.

If no profit can be achieved, the method should return 0.

Inputs:

**An integer array prices.

Constraints:

**1 ≤ prices.length ≤ 10^5

**0 ≤ prices[i] ≤ 10^4

Output:

**An integer representing the maximum profit achievable from one buy-sell transaction.

**If no profitable transaction is possible, return 0.

Behavior:

**The method should iterate through the array and determine the optimal buy and sell days such that the buy day occurs before the sell day.

**The method must only allow one buy and one sell, ensuring that the buy occurs before the sell, and that the profit is maximized.

**The method should handle edge cases such as a single-day price array, strictly increasing prices, strictly decreasing prices, constant prices, and multiple potential profit opportunities.

#Example

**Input: prices = [7,1,5,3,6,4]
**Output: 5