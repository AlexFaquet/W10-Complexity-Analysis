public class MergeSort {

    /**
     * Public entry point.
     */
    public static void sort(int[] arr) {
        if (arr.length <= 1) return;
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    // Recursively sort arr[left..right]
    private static void mergeSort(int[] arr, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr, temp, left, mid);
        mergeSort(arr, temp, mid + 1, right);
        merge(arr, temp, left, mid, right);
    }

    // Merge two sorted halves: arr[left..mid] and arr[mid+1..right]
    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        // Copy relevant segment into temp
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;      // pointer into left half
        int j = mid + 1;   // pointer into right half
        int k = left;      // pointer into original array

        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }

        // Copy any remaining elements from the left half
        while (i <= mid) {
            arr[k++] = temp[i++];
        }

        // No need to explicitly copy right half: already in place
    }
}
