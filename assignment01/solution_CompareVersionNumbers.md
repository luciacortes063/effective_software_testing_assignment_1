# Solution for Compare Version Numbers

## 1. Specification-Based Testing

### Step 1: Understanding the requirements

- The method takes two version strings (for example "1.0.1") and compares them revision by revision from left to right. 
- It returns "1" if version1 is greater, "-1" if version1 is smaller, and "0" if they are equal. 
- Missing revisions in shorter versions are treated as "0". 
- Leading zeros in revisions should be ignored. 
- Null inputs throw an "IllegalArgumentException".

### Step 2: Exploring the program

We tried the README example ("1.01" vs "1.001") and got "0", which is correct. Then we tried "2.0" vs "1.0" and got -1 instead of 1. The return values for greater and smaller were swapped in the code.

### Step 3: Identifying the partitions

Partitions for inputs:
- null -> must throw exception
- Single revision (for example "1")
- Multiple revisions (for example "1.0.1")
- Same number of revisions
- Different number of revisions (one version is shorter)

Partitions according to comparison outcome:
- version1 > version2 -> return 1
- version1 < version2 -> return -1
- version1 == version2 -> return 0

Partitions according to where the versions differ:
- Differ at the first revision
- Differ at a later revision
- Only differ because one has trailing zeros (should be equal)

Special cases from the specifications:
- Leading zeros in revisions ("1.01" vs "1.001")
- Trailing zeros ("1.0.0.0" vs "1.0")
- Large revision numbers (up to 32-bit integer range)

### Step 4: Analyzing the boundaries

The main boundary is the transition from equal to not equal. For example, "1.0" vs "1.0" is `0`, but "1.0" vs "1.1" is -1. It's important to test both sides of that line.

Also, there's the boundary between having the same number of revisions and having different ones (specifically where the missing revision defaults to 0 and changes the outcome).

### Step 5: Test cases

| Test case | version1 | version2 | Expected | Why                                                            |
|-----------|-----|-----|----------|----------------------------------------------------------------|
| T1        | null | "1.0" | exception | null version1                                                  |
| T2        | "1.0" | null | exception | null version2                                                  |
| T3        | "1.0" | "1.0" | 0        | equal versions                                                 |
| T4        | "1.01" | "1.001" | 0        | README example where leading zeros are ignored                 |
| T5        | "2.0" | "1.0" | 1        | version1 greater at first revision -> this test reveals the bug |
| T6        | "1.0" | "2.0" | -1       | version1 smaller at first revision —> this test reveals the bug |
| T7        | "1.2" | "1.1" | 1        | differ at second revision                                      |
| T8        | "1.0.1" | "1.0.2" | -1       | differ at third revision                                       |
| T9        | "1.0.1" | "1.0" | 1        | version1 has more revisions                                    |
| T10       | "1.0" | "1.0.1" | -1       | version2 has more revisions                                    |
| T11       | "1.0.0.0" | "1.0" | 0        | trailing zeros equal                                           |
| T12       | "1" | "1" | 0        | single revision equal                                          |
| T13       | "2" | "1" | 1        | single revision greater                                        |
| T14       | "1" | "2" | -1       | single revision smaller                                        |
| T15       | "1.2147483647" | "1.0" | 1        | large revision number                                          |

### Step 6: Automating the tests

See "CompareVersionNumbersTest.java"

### Step 7: Extra checks

No additional cases were needed after going through the partitions.

### Bug found

The return values inside the comparison were swapped. When "num1 > num2" (version1 has a larger revision), the code returned -1 instead of 1. When num1 < num2, it returned 1 instead of -1. This means the method always reported the wrong version whenever the versions differed.

The test that caught it was "version1GreaterAtFirstRevision" as "compareVersion("2.0", "1.0")" returned -1 instead of 1.

To fix it, we swapped the return values:

```java
// before
if (num1 > num2) {
        return -1;
        } else if (num1 < num2) {
        return 1;
        }

// after
        if (num1 > num2) {
        return 1;
        } else if (num1 < num2) {
        return -1;
        }
```

## 2. Structural Testing

After running JaCoCo ("mvn test jacoco:report"), the results were:

| Metric | Result |
|--------|--------|
| Instruction coverage | 95% (3 instructions missed) |
| **Branch coverage** | **100%** |
| Line coverage | 13/14 |
| Method coverage | 1/2 missed |

Branch coverage is 100%. The missed line and method is the default constructor, never called because all methods are static, so it's not worth testing.

The conditions in the method and how they are covered:

| Condition | Covered by |
|-----------|-----------|
| "version1 == null \|\| version2 == null" (true) | T1, T2 |
| "version1 == null \|\| version2 == null" (false) | all other tests |
| "i < v1Parts.length" (true and false) | T9, T10, "version1ShorterThanVersion2", "version2ShorterThanVersion1" |
| "i < v2Parts.length" (true and false) | same as above |
| "num1 > num2" (true) | T5, T7, T9, T13 |
| "num1 > num2" (false) | T3, T4, T6, T8, T10 |
| "num1 < num2" (true) | T6, T8, T10, T14 |
| "num1 < num2" (false) | T3, T4 |

Two structural tests were added to explicitly cover the branches where one version is shorter than the other and the missing revision defaults to 0:

- "version1ShorterThanVersion2": "1" vs "1.1" -> version1 runs out of parts first
- "version2ShorterThanVersion1": "1.1" vs "1" -> version2 runs out of parts first


## 3. Mutation Testing

After running PIT ("mvn test-compile org.pitest:pitest-maven:mutationCoverage") we obtained:

- Generated 14 mutations -> Killed 13 (93%)
- Test strength 93%

| Mutator | Generated | Killed | Survived |
|---------|-----------|--------|----------|
| ConditionalsBoundaryMutator | 5 | 4 | 1 |
| NegateConditionalsMutator | 7 | 7 | 0 |
| PrimitiveReturnsMutator | 2 | 2 | 0 |

### Surviving mutant

The 1 surviving mutant comes from "ConditionalsBoundaryMutator" and we think that is an equivalent mutant. So we believe that is not worth writing a test for it. Our existing tests with different-length versions already cover boundaries as thoroughly as possible.