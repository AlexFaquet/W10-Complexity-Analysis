public class QuickSort {

    /**
     * Public entry point.
     * Sorts the array in-place using quicksort with a fixed pivot.
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // nothing to do
        }
        quicksort(arr, 0, arr.length - 1);
    }

private static void quicksort(int[] arr, int low, int high) {
    // Add protection against very unbalanced partitions
    if (high - low + 1 <= 10) {
        quicksort(arr, low, high);
        return;
    }
    
    if (low >= high) {
        return;
    }

    int pivotIndex = partition(arr, low, high);
    quicksort(arr, low, pivotIndex - 1);
    quicksort(arr, pivotIndex + 1, high);
}

    /**
     * Partitions arr[low..high] around a fixed pivot (the element at 'low').
     * Returns the final index of the pivot.
     */
    private static int partition(int[] arr, int low, int high) {
    // Fixed pivot: first element
    int pivot = arr[low];
    int i = low + 1; // index of first element > pivot
    
    for (int j = low + 1; j <= high; j++) {
        if (arr[j] <= pivot) {
            swap(arr, i, j);
            i++;
        }
    }
    
    // Place pivot in correct position
    swap(arr, low, i - 1);
    return i - 1;
}

    // Utility: swap two positions in the array
    private static void swap(int[] arr, int a, int b) {
        if (a == b) return;
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    // Small test harness if you want to test this file alone:
    public static void main(String[] args) {
        int[] arr = {5, 1, 9, 3, 7, 2, 8};
        QuickSort.sort(arr);
        for (int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
