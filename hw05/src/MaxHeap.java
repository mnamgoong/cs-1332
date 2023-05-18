import java.util.ArrayList;

/**
 * Implementation of a MaxHeap that is backed by an array of contiguous
 * elements.
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 *
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     */
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backingArray has an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * First copies over the data from the ArrayList to the backingArray
     * (leaving index 0 of the backingArray empty). The data in the
     * backingArray is in the same order as it appears in the passed in
     * ArrayList.
     *
     * Then uses the BuildHeap algorithm, which involves building the heap
     * from bottom up by repeated use of downHeap operations.
     *
     * The backingArray has capacity 2n + 1, where n is the number of data in
     * the passed in ArrayList (not INITIAL_CAPACITY). Index 0 remains empty,
     * indices 1 to n contains the data in proper order, and the rest of the
     * indices are empty.
     * 
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data cannot be null.");
            }
            backingArray[i + 1] = data.get(i);
            size++;
        }
        for (int i = size / 2; i >= 1; i--) {
            downHeap(i);
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backingArray (the
     * backingArray is full except for index 0), it is resized to double its
     * current length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (size == backingArray.length - 1) {
            T[] oldArray = backingArray;
            backingArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < oldArray.length; i++) {
                backingArray[i] = oldArray[i];
            }
        }
        if (isEmpty()) {
            backingArray[1] = data;
        } else {
            backingArray[size + 1] = data;
            upHeap(size + 1);
        }
        size++;
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Replaces any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty.");
        }
        T removed = backingArray[1];
        swap(1, size);
        backingArray[size] = null;
        size--;
        downHeap(1);
        return removed;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty.");
        }
        if (size == 1) {
            return backingArray[1];
        }
        T max = backingArray[1];
        for (T data : backingArray) {
            if (data != null && max.compareTo(data) < 0) {
                max = data;
            }
        }
        return max;
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Swaps two elements in the backingArray.
     *
     * @param num1 the index of the first element to swap
     * @param num2 the index of the second element to swap
     */
    private void swap(int num1, int num2) {
        T temp = backingArray[num1];
        backingArray[num1] = backingArray[num2];
        backingArray[num2] = temp;
    }

    /**
     * Private helper method that performs the downHeap algorithm to fix the
     * order property.
     *
     * Compares a node's data with its children's data to determine whether a
     * swap is necessary. Swaps if the child's data is greater than the parent
     * node's data.
     *
     * @param i the index of the parent node
     */
    private void downHeap(int i) {
        int max = i;
        if (2 * i <= size
                && backingArray[2 * i].compareTo(backingArray[max]) > 0) {
            max = 2 * i;
        }
        if (2 * i + 1 <= size
                && backingArray[2 * i + 1].compareTo(backingArray[max]) > 0) {
            max = 2 * i + 1;
        }
        if (max != i) {
            swap(i, max);
            downHeap(max);
        }
    }

    /**
     * Private helper method that performs the upHeap algorithm to fix the
     * order property.
     *
     * Compares a child's data with its parent's data to determine whether a
     * swap is necessary. Swaps if the child's data is greater than the parent
     * node's data.
     *
     * @param i the index of the child node
     */
    private void upHeap(int i) {
        if (i > 1 && backingArray[i].compareTo(backingArray[i / 2]) > 0) {
            swap(i / 2, i); // swap with parent
            upHeap(i / 2);
        }
    }
}
