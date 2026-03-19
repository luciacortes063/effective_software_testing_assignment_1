# Effective Software Testing Lab (Assignment 1)

To correctly complete this assignment, you **must**:

1. **Work in a team of three students.**  
   The assignment must be submitted by groups of *three* students. Follow this process to form and register your group:

    a. Start by coordinating with two other students. Once you have agreed on the formation of your group, **each of you** should enroll under the same group number on OLAT via [this link](https://lms.uzh.ch/auth/RepositoryEntry/17846829597/CourseNode/113292116353268).

    b. When enrolling, select **the first group available** from the top of the list. The higher-numbered groups are reserved as extras and should not be selected.

    c. If you accidentally register for a group already selected by others, you can immediately **unenroll** and choose the next available group.

2. **Work with your team only** (unless otherwise stated). While you may discuss ideas with other teams, each team is expected to develop its own unique solution. A strict plagiarism policy will be enforced for all submitted work.

3. **Use Generative AI appropriately.** you must ensure that the final solution is your own original work. Generative AI should only be used as a tutor or assistant (e.g., to help troubleshooting or learning a new API), not for directly producing the solutions. If you make use of generative AI, **you have to document all the prompts you have used and an overall explanation of how you used Generative AI**. At the end, you are responsible for the answers you submit. To avoid misuse of Generative AI, we will do the following:

    a. **_Random Audits_**: After all three assignments have been submitted and graded, a subset of groups will be **semi-randomly selected** to present and explain their work. This presentation will cover the rationale behind specific decisions, the test cases designed, and the developed code itself. Each group member is expected to demonstrate **a thorough understanding of the entire submitted solution**. Inability to do so may result in individual penalties, disqualification of the group’s submission, and, in severe cases, the incident may be reported as **academic misconduct**. It is essential that **every group member fully understands and can explain all parts of the submitted work** — there is no option to claim unfamiliarity with any part of the solution. **Failure to demonstrate this understanding will result in failed assignment(s) for the student.**
  
    b. _Plagiarism detection_: Automated tools will be used to detect code similarities and identify AI-generated content. Any instances of code reuse or reliance on AI beyond the limits described above will be flagged for further review.

4. **Follow the instructions and submission guidelines precisely**. Ensure that your solutions adhere to the structure outlined in the submission section.

5. **Deadline.** The deadline for this assignment is **March 30, 2026, at 18:00** (Zurich, CH, time).


## Submission

Before the deadline, finalize your work and prepare **one single zip file**. This zip file must include all the folders provided with this assignment, each one augmented with the tests you write for the corresponding problem (as described below).

In addition, in the **root folder**, create **one markdown file per problem***, named using the format:

`solution_[problem name].md` (e.g., `solution_IntervalMerger.md`)

In each of these files, document your decisions and provide the information requested below.



## Exercises
This assignment consists of 12 exercises. Each exercise has its own folder and contains a problem definition and the corresponding solution in Java. **Some solutions intentionally contain bugs**. Your task is to test the solutions, according to the instructions given in the _Testing_ section below. If your tests reveal bugs, you must fix them until all the tests pass. Report the bug and your fix in `solution_[problem name].md` of each problem. 

We suggest the use of the IntelliJ IDEA integrated development environment, which can be downloaded for free [here](https://www.jetbrains.com/idea/) and for which an educational license is available.

### Importing an Exercise
To import an exercise in IntelliJ IDEA, click "File"->"Open" and select the exercise folder.

### Structure of an Exercise
Every exercise contains a `src` folder, which contains at least two files,
`ExerciseName.java` and `ExerciseNameTest.java`. The `ExerciseName.java` file contains the solution which you have to test, and the `ExerciseNameTest.java` file is the file where you have to write your tests. If your test reveals any bugs, fix them by modifying `ExerciseName.java`.

In addition to the `src` folder, each project contains a `README.md` file that describes the problem and a `pom.xml` file that contains the Maven configuration. Maven is a tool for simplifying the process of building Java code (see [here](https://www.jetbrains.com/help/idea/maven-support.html) for more).

### Requirements
Java 22 has been used for all the exercises; we cannot guarantee things will work with other versions. Use [JUnit 5](https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html) for implementing your test suite.

## Testing 
You are asked to perform effective and systematic software testing for each exercise's solution, specifically:

1. Perform **specification-based testing**, following the 7 principles taught in the book and the lectures. Document each principle in the 'solution_[problem name].md'. If you find a bug, report the bug, the test that revealed the bug, as well as the bug fix.

2. Enhance the previous test suite using **structural testing**. Specifically, aim for maximizing *condition+Line* coverage, which can be measured using the [JaCoCo](https://www.eclemma.org/jacoco/trunk/doc/maven.html) plugin. Document the process by reporting which conditions, and lines did you miss with specification-based testing (if any), and what tests did you add to cover them.

3. Now that you have a good testing suite, evaluate and possibly augment it further using **mutation testing** (you will need [PItest](https://pitest.org/quickstart/maven/) and [PItest plugin for JUnit 5](https://github.com/pitest/pitest-junit5-plugin)). Report the mutation coverage in the 'solution_[problem name].md'. Explain whether the mutants that survive (if any) were worth writing tests for or not.

## Grading Criteria
Each assignment will be graded with either 0 (fail) or 1 (pass). For an assignment to receive a 1, it must: 

1. Provide clear evidence of effort.

2. Provide a clear 'solution_[problem name].md'` file for each of 12 problems. 

3. Provide code that fulfils the requirements of the assignment or explains why this could not be achieved and what has been tried and how.
