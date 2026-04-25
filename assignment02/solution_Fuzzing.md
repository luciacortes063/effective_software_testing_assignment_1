# Solution: Fuzzing

## What Was Implemented

Exercise 6 was solved in `6-Fuzzing/fuzzing_assignment.ipynb` by filling all `# Your code here` cells.

Implemented parts:

1. **Task 01**

- Compared `MutationBlackboxFuzzer` with 5 simple seeds vs 5 complex seeds.
- Used 30 runs and 10,000 trials per run.
- Used Mann-Whitney U (`alternative='less'`).

2. **Task 02**

- Extended mutation strategy with:
  - swap two random bytes
  - XOR random byte at random position
  - bit rotation on random byte
- Added chunk deletion + duplication strategy with probability `1-p`.
- Compared baseline vs advanced blackbox fuzzer over 30 runs and 5,000 trials.

3. **Task 03**

- Compared baseline blackbox vs provided mutation greybox fuzzer.
- Used 30 runs and 5,000 trials.

4. **Task 04**

- Implemented `BoostedMutationGreyboxFuzzer`.
- Added path-frequency tracking via `getPathID()`.
- Seed selection in `create_candidate()` is weighted inversely to path frequency.
- Compared boosted vs base greybox on:
  - `literal_eval()` (30 runs, 5,000 trials)
  - `crashme()` (30 runs, 10,000 trials)

## Results (from executed notebook)

### Task 01: Simple vs Complex seeds

- Simple mean coverage: **13.67**
- Complex mean coverage: **12.00**
- p-value: **0.926488**
- Conclusion: **No statistically significant evidence that simple seeds are worse.**

### Task 02: Baseline vs Advanced mutations

- Baseline mean coverage: **12.83**
- Advanced mean coverage: **12.77**
- p-value: **0.518958**
- Conclusion: **No statistically significant evidence that advanced mutations are better.**

### Task 03: Blackbox vs Greybox

- Blackbox mean coverage: **12.00**
- Greybox mean coverage: **12.97**
- p-value: **0.166855**
- Conclusion: **No statistically significant evidence that greybox is better** on this setup.

### Task 04: Boosted Greybox

#### On `literal_eval()`

- Base greybox mean coverage: **13.90**
- Boosted greybox mean coverage: **12.97**
- p-value: **0.721472**
- Conclusion: **No statistically significant advantage for boosted greybox** on `literal_eval()`.

#### On `crashme()`

- Coverage p-value: **0.008782**
- Crash-count p-value: **0.011675**
- Conclusion: **Boosted greybox performs significantly better** on `crashme()` for both coverage and crash discovery.

## Notes

- The notebook includes boxplots for each comparison.
- A few mutation edge cases (empty-string mutations) were fixed to avoid runtime errors.
- The solution stays simple and close to lecture code, with only the required extensions.
