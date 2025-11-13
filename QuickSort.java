public class QuickSort {

    /**
     * Public entry point.
     * Sorts the array in-place using quicksort with a fixed pivot.
     */
    public static void sort(int[] arr) {
        if (arr.length <= 1) return;
        quicksort(arr, 0, arr.length - 1);
    }

    private static void quicksort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        int pivotIndex = partition(arr, low, high);
        quicksort(arr, low, pivotIndex - 1);
        quicksort(arr, pivotIndex + 1, high);
    }

    /**
     * Partitions arr[low..high] around a fixed pivot (the element at 'high').
     * Returns the final index of the pivot.
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // fixed pivot choice: last element
        int i = low - 1;       // place for the next smaller element

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // Place pivot just after the last smaller element
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int a, int b) {
        if (a == b) return;
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
