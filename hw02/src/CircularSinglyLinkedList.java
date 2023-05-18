import java.util.NoSuchElementException;

/**
 * The implementation of a CircularSinglyLinkedList without a tail pointer.
 * In a CircularSinglyLinkedList, each node has a reference to the next node,
 * and the next reference for the last node in the list will point to the
 * head node.
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 *
 */
public class CircularSinglyLinkedList<T> {

    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /**
     * Adds the data to the specified index.
     *
     * O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index cannot be less "
                    + "than 0 or greater than " + size + ".");
        }
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into "
                    + "the data structure.");
        }
        if (index == 0 || index == size) {
            if (isEmpty()) {
                head = new CircularSinglyLinkedListNode<>(data);
                head.setNext(head);
            } else {
                CircularSinglyLinkedListNode<T> newIndex =
                        new CircularSinglyLinkedListNode<>(head.getData(),
                                head.getNext());
                head.setNext(newIndex);
                head.setData(data);
                if (index == size) {
                    head = head.getNext();
                }
            }
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            CircularSinglyLinkedListNode<T> newIndex =
                    new CircularSinglyLinkedListNode<>(curr.getData(),
                            curr.getNext());
            curr.setNext(newIndex);
            curr.setData(data);
        }
        size++;
    }

    /**
     * Adds the data to the front of the list.
     *
     * O(1) for all cases.
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
     * Adds the data to the back of the list.
     *
     * O(1) for all cases.
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
     * Removes and returns the data at the specified index.
     *
     * O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index cannot be less "
                    + "than 0 or greater than or equal to " + size + ".");
        }
        T removed;
        if (index == 0) {
            removed = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            removed = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
        }
        size--;
        return removed;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * O(1) for all cases.
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty so there is "
                    + "no data to remove.");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * O(n) for all cases.
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
     * Returns the data at the specified index.
     *
     * O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index cannot be less than "
                    + "0 or greater than or equal to " + size + ".");
        }
        CircularSinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * O(1) for all cases.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list. Clears all data and resets the size.
     *
     * O(1) for all cases.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * O(n) for all cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from "
                    + "the data structure.");
        }
        CircularSinglyLinkedListNode<T> curr = head;
        T removed = null;
        int removedIndex = 0;
        for (int i = 0; i < size; i++) {
            if (curr.getData() == data) {
                removed = curr.getData();
                removedIndex = i;
            }
            curr = curr.getNext();
        }
        if (removed == null) {
            throw new NoSuchElementException("The data is not found in the "
                    + "data structure.");
        } else {
            removeAtIndex(removedIndex);
            return removed;
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        CircularSinglyLinkedListNode<T> curr = head;
        T[] array = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = curr.getData();
            curr = curr.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        return head;
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
