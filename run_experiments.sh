#!/bin/sh

echo "Compiling Java sources..."
javac -cp "lib/junit-platform-console-standalone-1.13.4.jar" -d out src/*.java || { echo "Compilation failed"; exit 1; }

echo "Running ExperimentRunner..."
java src/ExperimentRunner.java || { echo "Experiment failed"; exit 1; }

echo "Running Python analysis..."
cd analysis || exit 1
python3 analyze_sorts.py || { echo "Python analysis failed"; exit 1; }

echo "All done!"
