import java.util.Arrays;
import java.util.Random;

public class ExperimentRunner {

    private static final int TRIALS = 5;      // number of runs per condition
    private static final int MAX_VALUE = 1_000_000;

    public static void main(String[] args) {
        int[] sizes = {1000, 3000, 6000, 9000, 12000, 15000};
        String[] inputTypes = {"random", "sorted", "reversed"};

        // Fixed seed so experiments are repeatable
        Random random = new Random(42);

        // Create CSV writer
        CSVWriter csv = new CSVWriter("results.csv");

        for (int n : sizes) {
            for (String inputType : inputTypes) {

                // Generate a base array for this (n, inputType)
                int[] base = generateArray(n, inputType, random);

                // Time each algorithm on this base array
                double selectionAvg = averageTimeSelection(base);
                double mergeAvg     = averageTimeMerge(base);
                double quickAvg     = averageTimeQuick(base);

                // Write rows to CSV
                csv.writeRow("selection", inputType, n, selectionAvg);
                csv.writeRow("merge",     inputType, n, mergeAvg);
                csv.writeRow("quick",     inputType, n, quickAvg);
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
     */
    private static int[] generateArray(int n, String type, Random random) {
        int[] arr = new int[n];

        // start with sorted values
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

    private static double averageTimeSelection(int[] base) {
        long totalNs = 0;

        for (int t = 0; t < TRIALS; t++) {
            int[] arr = Arrays.copyOf(base, base.length);

            long start = System.nanoTime();
            SelectionSort.sort(arr);
            long end = System.nanoTime();

            totalNs += (end - start);
        }

        double avgNs = totalNs / (double) TRIALS;
        return avgNs / 1_000_000.0; // convert to ms
    }

    private static double averageTimeMerge(int[] base) {
        long totalNs = 0;

        for (int t = 0; t < TRIALS; t++) {
            int[] arr = Arrays.copyOf(base, base.length);

            long start = System.nanoTime();
            MergeSort.sort(arr);
            long end = System.nanoTime();

            totalNs += (end - start);
        }

        double avgNs = totalNs / (double) TRIALS;
        return avgNs / 1_000_000.0;
    }

    private static double averageTimeQuick(int[] base) {
        long totalNs = 0;

        for (int t = 0; t < TRIALS; t++) {
            int[] arr = Arrays.copyOf(base, base.length);

            long start = System.nanoTime();
            QuickSort.sort(arr);
            long end = System.nanoTime();

            totalNs += (end - start);
        }

        double avgNs = totalNs / (double) TRIALS;
        return avgNs / 1_000_000.0;
    }
}
