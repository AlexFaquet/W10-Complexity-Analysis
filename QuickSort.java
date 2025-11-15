public class QuickSort {

    /**
     * Public method to sort an array using Quicksort.
     * We use the last element as the pivot (fixed pivot), because the
     * practical asks us to observe worst-case behaviour on sorted data.
     */
    public static void sort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }

    /**
     * Recursively sorts the subarray arr[low..high]
     */
    private static void quicksort(int[] arr, int low, int high) {
        // Base case: a subarray of size 0 or 1 is already sorted
        if (low >= high) {
            return;
        }

        // Partition the array around the pivot
        int pivotIndex = partition(arr, low, high);

        // Recursively sort left side and right side
        quicksort(arr, low, pivotIndex - 1);
        quicksort(arr, pivotIndex + 1, high);
    }

    /**
     * Partition the subarray arr[low..high] around a pivot.
     * We choose the last element arr[high] as the pivot.
     *
     * After partitioning:
     * - all elements <= pivot will be on the left side
     * - all elements > pivot will be on the right side
     * - the pivot will be in its final sorted position
     *
     * @return the final index of the pivot
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];     // pivot = last element
        int i = low - 1;           // marks end of "elements <= pivot"

        // Move through arr[low..high-1]
        for (int j = low; j < high; j++) {
            // If element is <= pivot, move it into the "small" section
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // Finally, put the pivot just after the last "small" element
        swap(arr, i + 1, high);

        return i + 1;              // pivot's new index
    }

    /**
     * Swap helper method
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // Optional: small main to test correctness quickly
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 1, 2, 7};
        QuickSort.sort(arr);
        for (int x : arr) System.out.print(x + " ");
    }
}
