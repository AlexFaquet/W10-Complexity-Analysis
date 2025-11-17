#!/bin/sh

echo "Compiling Java sources..."
javac -cp "lib/junit-platform-console-standalone-1.13.4.jar" -d out src/*.java || { echo "Compilation failed"; exit 1; }

echo "Running JUnit tests..."
java -jar lib/junit-platform-console-standalone-1.13.4.jar --class-path "out" --scan-class-path