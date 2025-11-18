package src;

import java.util.Arrays;
import java.util.Random;

public class ExperimentRunner {

    private static final int TRIALS = 5;      // number of runs per condition
    private static final int MAX_VALUE = 1_000_000;

    public static void main(String[] args) {
        int[] sizes = {1000, 3000, 6000, 9000, 12000, 15000};
        String[] inputTypes = {"random", "sorted", "reversed", "nearlySorted"};

        // Fixed seed so experiments are repeatable
        Random random = new Random(42);

        // Create CSV writer
        CSVWriter csv = new CSVWriter("results/results.csv");

        for (int n : sizes) {
            for (String inputType : inputTypes) {

                // Generate a base array for this (n, inputType)
                int[] base = generateArray(n, inputType, random);

                // Run each algorithm TRIALS times on this base array
                for (int trial = 1; trial <= TRIALS; trial++) {
                    double selectionMs = timeSelection(base);
                    csv.writeRow("selection", inputType, n, selectionMs, trial);

                    double mergeMs = timeMerge(base);
                    csv.writeRow("merge", inputType, n, mergeMs, trial);

                    double quickMs = timeQuick(base);
                    csv.writeRow("quick", inputType, n, quickMs, trial);
                }
            }
        }

        // Close CSV file
        csv.close();

        System.out.println("Experiments finished. Results written to results.csv");
    }

    /**
     * Generates an array of length n in the requested order.
     * - "random": shuffled random integers
     * - "sorted": ascending order
     * - "reversed": descending order
     * - "nearlySorted": start sorted, then do a small number of random swaps
     */
    private static int[] generateArray(int n, String type, Random random) {
        int[] arr = new int[n];

        // start with random values, then sort to get a base "sorted" array
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(MAX_VALUE);
        }
        Arrays.sort(arr); // now arr is sorted ascending

        if (type.equals("sorted")) {
            return arr;

        } else if (type.equals("reversed")) {
            // reverse the sorted array
            for (int i = 0, j = n - 1; i < j; i++, j--) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
            return arr;

        } else if (type.equals("nearlySorted")) {
            // start from sorted and perform a small number of random swaps
            int[] nearly = Arrays.copyOf(arr, arr.length);
            int swaps = Math.max(1, (int) (0.01 * n)); // 1% of n, at least 1 swap

            for (int k = 0; k < swaps; k++) {
                int i = random.nextInt(n);
                int j = random.nextInt(n);
                int tmp = nearly[i];
                nearly[i] = nearly[j];
                nearly[j] = tmp;
            }
            return nearly;

        } else if (type.equals("random")) {
            // shuffle to get random order (Fisher–Yates)
            for (int i = n - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
            return arr;

        } else {
            throw new IllegalArgumentException("Unknown input type: " + type);
        }
    }

    // ---- Single-trial timing helpers ----

    private static double timeSelection(int[] base) {
        int[] arr = Arrays.copyOf(base, base.length);
        long start = System.nanoTime();
        SelectionSort.sort(arr);
        long end = System.nanoTime();
        return (end - start) / 1_000_000.0; // ms
    }

    private static double timeMerge(int[] base) {
        int[] arr = Arrays.copyOf(base, base.length);
        long start = System.nanoTime();
        MergeSort.sort(arr);
        long end = System.nanoTime();
        return (end - start) / 1_000_000.0; // ms
    }

    private static double timeQuick(int[] base) {
        int[] arr = Arrays.copyOf(base, base.length);
        long start = System.nanoTime();
        QuickSort.sort(arr);
        long end = System.nanoTime();
        return (end - start) / 1_000_000.0; // ms
    }
}
