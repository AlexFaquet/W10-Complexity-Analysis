package src;
import java.util.Random;

public class ArrayGenerator {

    private final Random random;

    public ArrayGenerator(long seed) {
        this.random = new Random(seed);
    }

    public int[] randomArray(int n, int maxValue) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(maxValue);
        }
        return arr;
    }

    public int[] sortedArray(int n, int maxValue) {
        int[] arr = randomArray(n, maxValue);
        java.util.Arrays.sort(arr);
        return arr;
    }

    public int[] reverseSortedArray(int n, int maxValue) {
        int[] arr = sortedArray(n, maxValue);
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

public static int[] generateNearlySorted(int n, double swapFraction) {
    int[] arr = new int[n];

    // Start with sorted array
    for (int i = 0; i < n; i++) {
        arr[i] = i;
    }

    // Number of swaps = swapFraction * n  (e.g., 0.01 = 1%)
    int swaps = (int) (swapFraction * n);
    Random random = new Random();

    for (int i = 0; i < swaps; i++) {
        int a = random.nextInt(n);
        int b = random.nextInt(n);
        // Swap elements a and b
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    return arr;
}


}
