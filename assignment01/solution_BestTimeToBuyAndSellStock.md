# Solution for Best Time to Buy and Sell Stock

## 1. Specification-Based Testing

### Step 1: Understanding the requirements

- The method receives an array of stock prices and returns the maximum profit from a single buy-sell transaction. 
- It must buy before it sells. 
- If no profitable transaction exists, the program should return 0. 
- Null or empty input should throw an "IllegalArgumentException". 
- Prices are between 0 and 10^4, and the array has at least one element.

### Step 2:  Exploring the program

We tried the README example "[7, 1, 5, 3, 6, 4]" straight away and got "6" instead of "5". The correct answer is buying at 1 and selling at 6, which gives profit 5. Getting 6 showed that the algorithm was treating some a fix price of 0 as the buy price, which revealed that "minPrice = 0" was wrong and was not being updated.

### Step 3: Identifying the partitions

Partitions according to the Input array:
- null -> must throw exception
- Empty array -> must throw an exception
- Single element -> should return 0 because we can't buy and sell on the same day
- Two elements
- Multiple elements

Partitions according to Price behavior:
- Strictly increasing -> the profit should be the last minus the first
- Strictly decreasing ->  no profit, the program should return 0
- Constant prices -> the profit should be 0
- The best buy point is not at the start 
- Multiple ups and downs, where the highest profit is in the middle or at the end

Boundary:
- Price of 0 (minimum allowed by constraints)
- Only two prices (smallest array where a transaction is possible)

### Step 4: Analyzing the boundaries

The main boundary is the transition from "no profitable transaction" to "there is one". With two elements "[1, 4]" profit is 3, but with "[4, 1]" profit is 0. That edge between these two cases is worth testing.
It's also worth checking what happens when the lowest price appears after a higher price. This is the case that reveals the bug, because "minPrice" needs to be updated as we scan, not fixed to a value of 0.

### Step 5: Test cases

| Test case | Input | Expected | Why                                                   |
|-----------|-------|----------|-------------------------------------------------------|
| T1        | null  | exception | null input                                            |
| T2        | []    | exception | empty input                                           |
| T3        | [5]   | 0        | single element, no transaction possible               |
| T4        | [7,1,5,3,6,4] | 5        | README example which reveals the bug                  |
| T5        | [1,2,3,4,5] | 4        | strictly increasing                                   |
| T6        | [5,4,3,2,1] | 0        | strictly decreasing                                   |
| T7        | [3,3,3,3] | 0        | constant prices                                       |
| T8        | [7,1,5,3,6,4] | 5        | best buy is not at index 0 -> it also reveals the bug |
| T9        | [2,1,4,3,1,7] | 6        | profit at the end                                     |
| T10       | [1,4] | 3        | two prices with profit                                |
| T11       | [4,1] | 0        | two prices with no profit                             |
| T12       | [3,0,5] | 5        | price of zero in the array                            |

### Step 6: Automating the tests

See "BestTimeToBuyAndSellStockTest.java".

### Step 7: Extra checks

No additional cases were needed. The partitions and boundaries already cover the realistic input space well.

### Bug found

"minPrice" was initialized to 0 instead of "prices[0]". Since all prices in the array are ≥ 0, no price triggers "prices[i] < minPrice" in the early iterations, so the algorithm computes profits as if you could buy at price 0 (a day that doesn't exist). For "[7,1,5,3,6,4]" it returns 6 (thinking you bought at 0 and sold at 6) instead of the correct answer 5 (buy at 1 and sell at 6).

The test that caught it was "readmeExample" as "maxProfit(new int[]{7,1,5,3,6,4})" returned "6" instead of "5".

To fix it, we changed the initialization of "minPrice":

```java
// before
int minPrice = 0;

// after
int minPrice = prices[0];
```

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric | Result |
|--------|--------|
| Instruction coverage | 94% (3 instructions missed) |
| **Branch coverage** | **100%** |
| Line coverage | 11/12 |
| Method coverage | 1/2 missed |

Branch coverage is 100%, which is the main goal. The missed line and method is again the default constructor, never called because all methods are static, so it's not worth testing.

The conditions covered are:

| Condition | Covered by |
|-----------|-----------|
| "prices == null \|\| prices.length == 0" (true) | T1, T2 |
| "prices == null \|\| prices.length == 0" (false) | all other tests |
| "prices[i] < minPrice" (true) | T4, T8, T12, "minPriceUpdatedSeveralTimes" |
| "prices[i] < minPrice" (false) | T5, T9, T10 |
| "profit > maxProfit" (true) | T5, T9, T10 |
| "profit > maxProfit" (false) | T7, T11 |

In addition, one structural test was added to make the "prices[i] < minPrice" branch more explicit: the case where minPrice is updated several times in a row before a profitable sell:
- "minPriceUpdatedSeveralTimes": [5, 3, 2, 1, 8] ->  the price drops three times before rising. The expected profit is 7.

## 3. Mutation Testing

After running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") we found:

- Generated 10 mutations —> Killed 8 (80%)
- Test strength 80%

| Mutator | Generated | Killed | Survived |
|---------|-----------|--------|----------|
| ConditionalsBoundaryMutator | 3 | 1 | 2 |
| NegateConditionalsMutator | 5 | 5 | 0 |
| MathMutator | 1 | 1 | 0 |
| PrimitiveReturnsMutator | 1 | 1 | 0 |

### Surviving mutants

The 2 surviving mutants both come from "ConditionalsBoundaryMutator" and we think that they are equivalent mutants because they change the code but produce exactly the same observable behavior:

- Mutant 1: "prices[i] < minPrice" -> "prices[i] <= minPrice"

When a price equals "minPrice", the original code goes to the "else" branch and computes a profit of 0 (since "prices[i] - minPrice = 0"), which never updates "maxProfit". The mutant instead updates "minPrice" to the same value it already had. Both paths produce the same final result, so no test can distinguish them.

- Mutant 2: "profit > maxProfit" -> "profit >= maxProfit"

When "profit == maxProfit", the original skips the update and the mutant performs it, but since the value being assigned is the same as the current one, the result is identical.

These are equivalent mutants. Writing tests for them is not possible, because any input that triggers the boundary condition produces the same output either way, so the tests would always pass.