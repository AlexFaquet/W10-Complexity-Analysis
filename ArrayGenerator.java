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

    /**
     * Nearly sorted: mostly sorted, with a few random swaps.
     */
    public int[] nearlySortedArray(int n, int maxValue, int swaps) {
        int[] arr = sortedArray(n, maxValue);
        for (int k = 0; k < swaps; k++) {
            int i = random.nextInt(n);
            int j = random.nextInt(n);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }
}
