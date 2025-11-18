This project contains the code for running and analysing the performance of three sorting algorithms: selection sort, merge sort, and quicksort (last-element pivot). The experiment is written in Java and produces a results.csv file. A small Python script is used to generate graphs from the CSV file. JUnit tests are included to check correctness of the algorithms.

Directory structure:
src/ Java source files
test/ JUnit tests
analysis/ Python analysis script
results/ CSV output and generated graphs
lib/ JUnit jar file


==== Bash Script Shortcuts ====

1. To run the full experiment:

-
bash run_experiments.sh
-

 2. To run the JUnit tests:

-
bash run_tests.sh
-

==== Running the Java Experiment ====

1. From the project root, compile all files:

-
javac -cp "lib/junit-platform-console-standalone-1.13.4.jar" -d out src/*.java
-

2. Run the experiment:

-
java src/ExperimentRunner.java
-

This writes the file:

results/results.csv

Each row contains: algorithm, inputType, n, avgMs.


==== Running the Python Analysis ====

The analysis script reads results.csv and produces several PNG graphs inside the results/ folder.

1. Install the Python dependencies:

-
pip install pandas matplotlib
-

2. Run the analysis script from the project root:

-
python analysis/analysize_sorts.py
-

The script output the following files in results/:

selection_performance.png
merge_performance.png
quick_performance.png
comparison_random.png


==== Running the JUnit tests ====

All Java files in this project, including the test file, use the package:

package src;

To run the tests, follow these steps:

1. Make sure the JUnit 5 standalone jar is in the lib/ directory:
lib/junit-platform-console-standalone-1.13.4.jar

2. Compile all Java files (main code + test file) into the output folder:
javac -cp lib/junit-platform-console-standalone-1.13.4.jar:out -d out src/*.java

3. Run the JUnit 5 console launcher
java -jar lib/junit-platform-console-standalone-1.13.4.jar --class-path "out" --scan-class-path


