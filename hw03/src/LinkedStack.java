/**
 * The implementation of a LinkedStack, which is a Stack backed by a
 * singly-linked list without a tail reference. Does NOT exhibit circular
 * behavior.
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 *
 */
public class LinkedStack<T> {

    private LinkedNode<T> head;
    private int size;

    /**
     * Adds the data to the top of the stack.
     *
     * O(1) runtime efficiency.
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (size == 0) {
            head = new LinkedNode<>(data);
        } else {
            LinkedNode<T> newHead = new LinkedNode<>(data, head);
            head = newHead;
        }
        size++;
    }

    /**
     * Removes and returns the data from the top of the stack.
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
        T popped = head.getData();
        head = head.getNext();
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
        return head.getData();
    }

    /**
     * Returns the head node of the stack.
     *
     * @return the node at the head of the stack
     */
    public LinkedNode<T> getHead() {
        return head;
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
