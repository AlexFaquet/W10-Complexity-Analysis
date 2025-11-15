import java.util.Arrays;
import java.util.Random;

public class ExperimentRunner {

    private static final int TRIALS = 5;
    private static final int MAX_VALUE = 1_000_000;

    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 20000};

        Random random = new Random(42); // fixed seed for repeatability

        System.out.println("algorithm,inputType,n,avgMs");

        for (int n : sizes) {
            // random input
            runForAllAlgorithms("random", n, random);

            // sorted input
            runForAllAlgorithms("sorted", n, random);

            // reversed input
            runForAllAlgorithms("reversed", n, random);
        }
    }

    private static void runForAllAlgorithms(String inputType, int n, Random random) {
        int[] baseArray = generateArray(inputType, n, random);

        double selectionAvg = averageTimeSelection(baseArray);
        double mergeAvg     = averageTimeMerge(baseArray);
        double quickAvg     = averageTimeQuick(baseArray);

        System.out.printf("selection,%s,%d,%.3f%n", inputType, n, selectionAvg);
        System.out.printf("merge,%s,%d,%.3f%n", inputType, n, mergeAvg);
        System.out.printf("quick,%s,%d,%.3f%n", inputType, n, quickAvg);
    }

private static int[] generateArray(String type, int n, Random random) {
    int[] arr = new int[n];
    
    if (type.equals("sorted")) {
        // Generate sorted array directly
        for (int i = 0; i < n; i++) {
            arr[i] = i; // or use random but sorted values
        }
    } else {
        // Generate random array first
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(MAX_VALUE);
        }
        
        if (type.equals("reversed")) {
            Arrays.sort(arr);
            // Reverse the sorted array
            for (int i = 0, j = n - 1; i < j; i++, j--) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        // For "random", we keep the random array as-is
    }
    return arr;
}

    private static double averageTimeSelection(int[] base) {
        long total = 0;
        for (int t = 0; t < TRIALS; t++) {
            int[] arr = Arrays.copyOf(base, base.length);
            long start = System.nanoTime();
            SelectionSort.sort(arr);
            long end = System.nanoTime();
            total += (end - start);
        }
        return (total / (double) TRIALS) / 1_000_000.0; // ms
    }

    private static double averageTimeMerge(int[] base) {
        long total = 0;
        for (int t = 0; t < TRIALS; t++) {
            int[] arr = Arrays.copyOf(base, base.length);
            long start = System.nanoTime();
            MergeSort.sort(arr);
            long end = System.nanoTime();
            total += (end - start);
        }
        return (total / (double) TRIALS) / 1_000_000.0;
    }

    private static double averageTimeQuick(int[] base) {
        long total = 0;
        for (int t = 0; t < TRIALS; t++) {
            int[] arr = Arrays.copyOf(base, base.length);
            long start = System.nanoTime();
            QuickSort.sort(arr);
            long end = System.nanoTime();
            total += (end - start);
        }
        return (total / (double) TRIALS) / 1_000_000.0;
    }
}
