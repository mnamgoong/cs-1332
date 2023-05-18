/**
 * The implementation of an ArrayStack, which is a Stack backed by an array.
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 *
 */
public class ArrayStack<T> {

    public static final int INITIAL_CAPACITY = 9;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the data to the top of the stack.
     *
     * If sufficient space is not available in the backing array, the
     * array is resizded to double the current length.
     *
     * Amortized O(1) runtime efficiency.
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (size == backingArray.length) {
            T[] oldArray = backingArray;
            backingArray = (T[]) new Object[size * 2];
            for (int i = 0; i < oldArray.length; i++) {
                backingArray[i] = oldArray[i];
            }
        }
        backingArray[size] = data;
        size++;
    }

    /**
     * Removes and returns the data from the top of the stack.
     *
     * All spots that are popped from are replaced with null.
     *
     * O(1) runtime efficiency.
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Stack is empty.");
        }
        T popped = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return popped;
    }

    /**
     * Returns the data from the top of the stack without removing it.
     *
     * O(1) runtime efficiency.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Stack is empty.");
        }
        return backingArray[size - 1];
    }

    /**
     * Returns the backing array of the stack.
     *
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     *
     * @return the size of the stack
     */
    public int size() {
        return size;
    }
}
