import java.util.Arrays;
import java.util.function.Consumer;

public class ExperimentRunner {

    @FunctionalInterface
    interface IntSorter {
        void sort(int[] arr);
    }

    private static final int TRIALS = 10;
    private static final int MAX_VALUE = 1_000_000;

    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10_000, 20_000, 50_000};

        IntSorter selection = SelectionSort::sort;
        IntSorter merge = MergeSort::sort;
        IntSorter quick = QuickSort::sort;

        // fixed seed so experiments are repeatable
        ArrayGenerator generator = new ArrayGenerator(42L);

        // CSV header
        System.out.println("algorithm,inputType,n,avgMs,stdMs");

        runExperiments("selection", selection, sizes, generator);
        runExperiments("merge", merge, sizes, generator);
        runExperiments("quick", quick, sizes, generator);
    }

    private static void runExperiments(String name,
                                       IntSorter sorter,
                                       int[] sizes,
                                       ArrayGenerator generator) {

        String[] inputTypes = {"random", "sorted", "reversed", "nearlySorted"};

        for (int n : sizes) {
            for (String type : inputTypes) {

                long[] times = new long[TRIALS];

                // Pre-generate base array once per (n, type) so all trials see same data pattern
                int[] base = createArray(type, n, generator);

                for (int t = 0; t < TRIALS; t++) {
                    int[] arr = Arrays.copyOf(base, base.length);

                    long start = System.nanoTime();
                    sorter.sort(arr);
                    long end = System.nanoTime();

                    if (!isSorted(arr)) {
                        throw new IllegalStateException(
                                "Array not sorted for " + name + " n=" + n + " type=" + type);
                    }

                    times[t] = end - start;
                }

                double avgMs = average(times) / 1_000_000.0;
                double stdMs = stdDev(times, avgMs * 1_000_000.0) / 1_000_000.0;

                System.out.printf("%s,%s,%d,%.3f,%.3f%n", name, type, n, avgMs, stdMs);
            }
        }
    }

    private static int[] createArray(String type, int n, ArrayGenerator generator) {
        switch (type) {
            case "random":
                return generator.randomArray(n, MAX_VALUE);
            case "sorted":
                return generator.sortedArray(n, MAX_VALUE);
            case "reversed":
                return generator.reverseSortedArray(n, MAX_VALUE);
            case "nearlySorted":
                // 1% of positions swapped
                int swaps = Math.max(1, n / 100);
                return generator.nearlySortedArray(n, MAX_VALUE, swaps);
            default:
                throw new IllegalArgumentException("Unknown input type: " + type);
        }
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }

    private static double average(long[] values) {
        long sum = 0;
        for (long v : values) sum += v;
        return (double) sum / values.length;
    }

    private static double stdDev(long[] values, double meanNs) {
        double sumSq = 0.0;
        for (long v : values) {
            double diff = v - meanNs;
            sumSq += diff * diff;
        }
        return Math.sqrt(sumSq / values.length);
    }
}
