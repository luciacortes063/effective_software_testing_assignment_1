package zest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BestTimeToBuyAndSellStockTest {

    // Specification-based tests

    // Null and empty inputs should throw an exception
    @Test
    void nullInputThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> BestTimeToBuyAndSellStock.maxProfit(null));
    }

    @Test
    void emptyInputThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> BestTimeToBuyAndSellStock.maxProfit(new int[]{}));
    }

    // Only one price -> you cannot buy and sell, so profit is 0.
    @Test
    void singleElement() {
        assertEquals(0, BestTimeToBuyAndSellStock.maxProfit(new int[]{5}));
    }

    // Example from the README-> buy at 1, sell at 6.
    @Test
    void readmeExample() {
        assertEquals(5, BestTimeToBuyAndSellStock.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }

    // Prices go up every day-> the best is to buy on day 0 and sell on the last day.
    @Test
    void strictlyIncreasing() {
        assertEquals(4, BestTimeToBuyAndSellStock.maxProfit(new int[]{1, 2, 3, 4, 5}));
    }

    // Prices go down every day-> the transaction won't have any profit.
    @Test
    void strictlyDecreasing() {
        assertEquals(0, BestTimeToBuyAndSellStock.maxProfit(new int[]{5, 4, 3, 2, 1}));
    }

    // All prices are the same-> profit is 0.
    @Test
    void constantPrices() {
        assertEquals(0, BestTimeToBuyAndSellStock.maxProfit(new int[]{3, 3, 3, 3}));
    }

    // The best buy point is not the first element -> [7,1,5,3,6,4].
    // The minimum price (1) comes after a higher price (7), so minPrice must be updated as we scan -> not fixed at prices[0].
    @Test
    void bestBuyNotAtStart() {
        assertEquals(5, BestTimeToBuyAndSellStock.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }

    // The best profit is at the end of the array.
    @Test
    void profitAtEnd() {
        assertEquals(6, BestTimeToBuyAndSellStock.maxProfit(new int[]{2, 1, 4, 3, 1, 7}));
    }

    // Two prices only-> one profitable, one not.
    @Test
    void twoPricesProfitable() {
        assertEquals(3, BestTimeToBuyAndSellStock.maxProfit(new int[]{1, 4}));
    }

    @Test
    void twoPricesNotProfitable() {
        assertEquals(0, BestTimeToBuyAndSellStock.maxProfit(new int[]{4, 1}));
    }

    // Boundary -> prices with a zero value (minimum allowed by constraints).
    @Test
    void priceOfZero() {
        assertEquals(5, BestTimeToBuyAndSellStock.maxProfit(new int[]{3, 0, 5}));
    }

    // Structural tests

    // Forces the minPrice update branch (prices[i] < minPrice) to be taken
    // multiple times in a row, then a profit is made afterwards.
    @Test
    void minPriceUpdatedSeveralTimes() {
        assertEquals(7, BestTimeToBuyAndSellStock.maxProfit(new int[]{5, 3, 2, 1, 8}));
    }
}