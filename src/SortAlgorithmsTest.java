package src;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;


import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for the three sorting algorithms:
 * SelectionSort, MergeSort, QuickSort.
 *
 * The goal is to check correctness by comparing against Arrays.sort.
 */
public class SortAlgorithmsTest {

    // ---------- Helper methods ----------

    private void assertSelectionSortCorrect(int[] original) {
        int[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);

        int[] actual = Arrays.copyOf(original, original.length);
        SelectionSort.sort(actual);

        assertArrayEquals(expected, actual,
                "SelectionSort failed on input " + Arrays.toString(original));
    }

    private void assertMergeSortCorrect(int[] original) {
        int[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);

        int[] actual = Arrays.copyOf(original, original.length);
        MergeSort.sort(actual);

        assertArrayEquals(expected, actual,
                "MergeSort failed on input " + Arrays.toString(original));
    }

    private void assertQuickSortCorrect(int[] original) {
        int[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);

        int[] actual = Arrays.copyOf(original, original.length);
        QuickSort.sort(actual);

        assertArrayEquals(expected, actual,
                "QuickSort failed on input " + Arrays.toString(original));
    }

    private void assertAllAlgorithmsCorrect(int[] original) {
        assertSelectionSortCorrect(original);
        assertMergeSortCorrect(original);
        assertQuickSortCorrect(original);
    }

    // ---------- Tests ----------

    @Test
    public void testEmptyArray() {
        assertAllAlgorithmsCorrect(new int[] {});
    }

    @Test
    public void testSingleElement() {
        assertAllAlgorithmsCorrect(new int[] { 42 });
    }

    @Test
    public void testAlreadySorted() {
        assertAllAlgorithmsCorrect(new int[] { 1, 2, 3, 4, 5, 6 });
    }

    @Test
    public void testReverseSorted() {
        assertAllAlgorithmsCorrect(new int[] { 6, 5, 4, 3, 2, 1 });
    }

    @Test
    public void testWithDuplicates() {
        assertAllAlgorithmsCorrect(new int[] { 5, 1, 5, 3, 3, 3, 2, 5, 1 });
    }

    @Test
    public void testAllEqualElements() {
        assertAllAlgorithmsCorrect(new int[] { 7, 7, 7, 7, 7, 7 });
    }

    @Test
    public void testRandomSmallArray() {
        int[] arr = { 9, -3, 0, 4, 8, -1, 2 };
        assertAllAlgorithmsCorrect(arr);
    }

    @Test
    public void testRandomLargerArray() {
        Random random = new Random(42);
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100000) - 50000;
        }
        assertAllAlgorithmsCorrect(arr);
    }
}
