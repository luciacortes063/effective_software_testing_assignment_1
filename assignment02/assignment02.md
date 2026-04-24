# Effective Software Testing Lab (Assignment 2)

To correctly complete this assignment, you **must**:

1. **Work in a team of three students.**  
   The assignment must be submitted by groups of *three* students. You are required to maintain the same group composition as in Assignment 01.

2. **Work with your team only** (unless otherwise stated). While you may discuss ideas with other teams, each team is expected to develop its own unique solution. A strict plagiarism policy will be enforced for all submitted work.

3. **Use Generative AI appropriately.** you must ensure that the final solution is your own original work. Generative AI should only be used as a tutor or assistant (e.g., to help troubleshooting or learning a new API), not for directly producing the solutions. If you make use of generative AI, **you have to document all the prompts you have used and an overall explanation of how you used Generative AI**. At the end, you are responsible for the answers you submit. To avoid misuse of Generative AI, we will do the following:

    a. ***Random Audits***: After all three assignments have been submitted and graded, a subset of groups will be **semi-randomly selected** to present and explain their work. This presentation will cover the rationale behind specific decisions, the test cases designed, and the developed code itself. Each group member is expected to demonstrate **a thorough understanding of the entire submitted solution**. Inability to do so may result in individual penalties, disqualification of the group’s submission, and, in severe cases, the incident may be reported as **academic misconduct**. It is essential that **every group member fully understands and can explain all parts of the submitted work** — there is no option to claim unfamiliarity with any part of the solution. **Failure to demonstrate this understanding will result in failed assignment(s) for the student.**
  
    b. *Plagiarism detection*: Automated tools will be used to detect code similarities and identify AI-generated content. Any instances of code reuse or reliance on AI beyond the limits described above will be flagged for further review.

4. **Follow the instructions and submission guidelines precisely**. Ensure that your solutions adhere to the structure outlined in the submission section.

5. **Deadline.** The deadline for this assignment is **April 27, 2026, at 18:00** (Zurich, CH, time).

## Submission

Before the deadline, finalize your work and prepare **one single zip file**. This zip file must include all the folders provided with this assignment, each one augmented with the tests you write for the corresponding problem (as described below).

For exercices 1-5, in the **root folder**, create **one markdown file per problem***, named using the format:

`solution_[problem name].md` (e.g., `solution_IntervalMerger.md`)

In each of these files, document your decisions and provide the information requested below.

For Exercise 6 (the fuzzing assignment), you can include your explanations, notes, and rationales directly as Markdown cells within the provided Jupyter notebook `fuzzing_assignment.ipynb`. There is no need to create a separate Markdown file for Exercise 6.

## Exercises

This assignment consists of 6 exercises.

Exercises 1-5 each have their own folder and contain a problem definition and the corresponding solution in Java. **Some solutions intentionally contain bugs**. Your task is to test the solutions, according to the instructions given in the *Testing* section below. If your tests reveal bugs, you must fix them until all the tests pass. Report the bug and your fix in `solution_[problem name].md` of each problem.

We suggest the use of the IntelliJ IDEA integrated development environment, which can be downloaded for free [here](https://www.jetbrains.com/idea/) and for which an educational license is available.

In exercise 6, both the description and the solution for this exercise are integrated into a Jupyter notebook named `fuzzing_assignment.ipynb`. You will complete the assignment by filling in code cells and, if needed, by adding explanatory Markdown cells directly into the notebook.

### Importing an Exercise

To import a Java exercise in IntelliJ IDEA, click "File"->"Open" and select the exercise folder. For python, you can use any environment you prefer, we recommend Visual Studio Code.

### Structure of an Exercise

In exercises 1-5, each exercise contains a `src` folder, which contains at least two files,
`ExerciseName.java` and `ExerciseNameTest.java`. The `ExerciseName.java` file contains the solution which you have to test, and the `ExerciseNameTest.java` file is the file where you have to write your tests. If your test reveals any bugs, fix them by modifying `ExerciseName.java`.

In addition to the `src` folder, each project contains a `README.md` file that describes the problem and a `pom.xml` file that contains the Maven configuration. Maven is a tool for simplifying the process of building Java code (see [here](https://www.jetbrains.com/help/idea/maven-support.html) for more).

Exercise 6 is in Python and contains a Jupyter notebook that includes both the problem description and the solution cells you must complete (`fuzzing_assignment.ipynb`), a Python file containing helper classes and functions (`assignment_utils.py`), and a short list of Python dependencies required to run the notebook (`requirements.txt`). Generally, you should not modify `assignment_utils.py` unless necessary. If you make changes, they must be clearly documented. You must ensure that all required packages are installed.

### Requirements

Java 11 has been used for all the exercises; we cannot guarantee things will work with other versions. Use [JUnit 5](https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html) for implementing your test suite. For python, we used python 3.13.

## Testing

This assignment covers **Chapter 3: Structural Testing and Code Coverage**, introduces **Chapter 4: Designing Contracts**, and **Chapter 5: Property-Based Testing**, as well as the guest lecture on **Fuzz Testing**. You will apply the concepts learned to design and implement tests for given exercises.

### Task 1: Code Coverage

In this task, you are required to achieve the highest possible (ideally, 100\%) line coverage. for the provided Java solutions. Utilize the [JaCoCo] plugin to analyze and generate coverage reports.

### Task 2: Designing Contracts

- For the provided Java solutions, design and document contracts including pre-conditions, post-conditions, and invariants (use problem description and constraints in `README.md` of each problem).
- Implement the designed contracts into the source code.
- For invariants, ensure they are checked at the start and end of each public method or after any state-changing operation.

### Task 3: Testing Contracts

Develop a suite of JUnit tests specifically aimed at verifying that the contracts are correctly enforced. This should include tests that:

- Validate normal operation when pre-conditions are met.
- Confirm that appropriate exceptions or errors are thrown when pre-conditions are violated.
- Ensure post-conditions hold after the execution of functions under various conditions.
- Verify that invariants are maintained throughout the software module's lifecycle, especially after state changes.

### Task 4: Property-Based Testing

- Use **property-based testing** techniques to derive tests for the provided Java solutions.
- Identify properties that should hold true for any inputs and document your rationale.
- Use a property-based testing framework to automate the testing process.
- hint: Add jqwik framework to Your pom.xml

### Task 5: Fuzz testing

Check the respective Jupyter notebook, where the tasks defined will extend your understanding of Fuzz testing, and you will get a glimpse of different fuzzing strategies.

## Grading Criteria

Each assignment will be graded with either 0 (fail) or 1 (pass). For an assignment to receive a 1, it must:

1. Provide clear evidence of effort.

2. Provide a clear 'solution_[problem name].md'` file for each of 5 problems, plus clear solution documentation in the Jupyter notebook for exercice 06.

3. Provide code that fulfils the requirements of the assignment or explains why this could not be achieved and what has been tried and how.
