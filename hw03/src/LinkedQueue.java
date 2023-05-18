/**
 * The implementation of a LinkedQueue, which is a Queue backed by a
 * singly-linked list with a tail reference. Does NOT exhibit circular behavior.
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 *
 */
public class LinkedQueue<T> {

    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the back of the queue.
     *
     * O(1) runtime efficiency.
     *
     * @param data the data to add to the front of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (size == 0) {
            head = new LinkedNode<>(data);
            tail = head;
        } else {
            tail.setNext(new LinkedNode<>(data));
            tail = tail.getNext();
        }
        size++;
    }

    /**
     * Removes and returns the data from the front of the queue.
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
        LinkedNode<T> oldHead = head;
        head = head.getNext();
        oldHead.setNext(null);
        size--;
        if (size == 0) {
            tail = head;
        }
        return oldHead.getData();
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
            throw new java.util.NoSuchElementException("Queue is empty.");
        }
        return head.getData();
    }

    /**
     * Returns the head node of the queue.
     *
     * @return the node at the head of the queue
     */
    public LinkedNode<T> getHead() {
        return head;
    }

    /**
     * Returns the tail node of the queue.
     *
     * @return the node at the tail of the queue
     */
    public LinkedNode<T> getTail() {
        return tail;
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
