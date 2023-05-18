import java.util.NoSuchElementException;

/**
 * The implementation of an ArrayQueue, which is a Queue backed by an array.
 * Exhibits circular behavior.
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 *
 */
public class ArrayQueue<T> {

    public static final int INITIAL_CAPACITY = 9;

    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the data to the back of the queue.
     *
     * If sufficient space is not available in the backing array, the
     * array is resized to double the current length. When resizing, the
     * elements are copied to the beginning of the new array and front is reset
     * to 0.
     *
     * Amortized O(1) runtime efficiency.
     *
     * @param data the data to add to the back of the queue
     * @throws IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (size == backingArray.length) {
            T[] oldArray = backingArray;
            backingArray = (T[]) new Object[size * 2];
            for (int i = 0; i < oldArray.length; i++) {
                backingArray[i] = oldArray[(front + size) % oldArray.length];
                front++;
            }
            front = 0;
        }
        backingArray[(front + size) % backingArray.length] = data;
        size++;
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * All spots that are dequeued from are replaced with null.
     *
     * If the queue becomes empty as a result of this call, front is not
     * reset to 0.
     *
     * O(1) runtime efficiency.
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Queue is empty.");
        }
        T popped = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size--;
        return popped;
    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * O(1) runtime efficiency.
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return backingArray[front];
    }

    /**
     * Returns the backing array of the queue.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return size;
    }

}
