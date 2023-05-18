import java.util.NoSuchElementException;

/**
 * The implementation of an ArrayList, which is a list data structure backed
 * by an array where all of the data is contiguous and aligned with index 0
 * of the array.
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     */
    public static final int INITIAL_CAPACITY = 9;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList. The backing array is an empty array of the
     * initial capacity.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds a new element to the specified index while shifting current
     * elements as necessary to maintain the order of the list. If the
     * ArrayList does not have enough space, it is resized to twice its old
     * capacity.
     *
     * Amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        // Checks if the ArrayList is full
        if (!isEmpty() && backingArray[backingArray.length - 1] != null) {
            T[] oldArray = backingArray;
            // Resizes the ArrayList to twice its old capacity
            backingArray = (T[]) new Object[size * 2];
            for (int i = 0; i < oldArray.length; i++) {
                backingArray[i] = oldArray[i];
            }
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index cannot be less than "
                    + "0 or greater than " + size + ".");
        }
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into "
                    + "the data structure.");
        }
        if (index == size) {
            backingArray[index] = data;
        } else {
            // Shifts subsequent elements backward by one position
            for (int i = backingArray.length - 1; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
        }
        size++;
    }

    /**
     * Adds a new element to the front of the list.
     *
     * O(n) for all cases.
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into "
                    + "the data structure.");
        }
        addAtIndex(0, data);
    }

    /**
     * Adds a new element to the back of the list.
     *
     * Amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into "
                    + "the data structure.");
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Shifts current
     * elements as necessary to maintain the order of the list. All unused
     * positions are set to null.
     *
     * O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index cannot be less than "
                    + "0 or greater than or equal to " + size + ".");
        }
        T removed;
        if (index == size - 1) {
            removed = backingArray[size - 1];
        } else {
            removed = backingArray[index];
            // Shifts subsequent elements forward by one position
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
        }
        backingArray[size - 1] = null;
        size--;
        return removed;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * O(n) for all cases.
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty so there is "
                    + "no element to remove.");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * O(1) for all cases.
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty so there is "
                    + "no element to remove.");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index.
     *
     * O(1) for all cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index cannot be less than "
                    + "0 or greater than or equal to " + size + ".");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * O(1) for all cases.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list. Resets the backing array to a new array of the
     * initial capacity and resets the size.
     *
     * O(1) for all cases.
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }
}
