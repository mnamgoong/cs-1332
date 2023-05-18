import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Michelle Namgoong
 * @userid mnamgoong3
 * @GTID 903684766
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Data cannot be null.");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        root = add(root, data);
    }

    /**
     * Helper method for add(T data).
     *
     * @param curr the root of the tree
     * @param data the data to be added
     * @return the new balanced node
     */
    private AVLNode<T> add(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(add(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(add(curr.getRight(), data));
        } else {
            return curr;
        }
        update(curr);
        return rebalance(curr);
    }

    /**
     * Updates the node's height and balance factor.
     *
     * @param curr the node to be updated
     */
    private void update(AVLNode<T> curr) {
        int leftHeight = height(curr.getLeft());
        int rightHeight = height(curr.getRight());
        curr.setHeight(Math.max(leftHeight, rightHeight) + 1);
        curr.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Rebalances the node as necessary through rotations.
     *
     * @param curr the node to be rebalanced
     * @return the rebalanced node
     */
    private AVLNode<T> rebalance(AVLNode<T> curr) {
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rotateRight(curr.getRight()));
            }
            curr = rotateLeft(curr);
        }
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotateLeft(curr.getLeft()));
            }
            curr = rotateRight(curr);
        }
        return curr;
    }

    /**
     * Performs a right rotation on the node.
     *
     * @param curr the node to be rotated
     * @return the node that is right-rotated
     */
    private AVLNode<T> rotateRight(AVLNode<T> curr) {
        AVLNode<T> leftChild = curr.getLeft();
        curr.setLeft(leftChild.getRight());
        leftChild.setRight(curr);
        update(curr);
        update(leftChild);
        return leftChild;
    }

    /**
     * Performs a left rotation on the node.
     *
     * @param curr the node to be rotated
     * @return the node that is left-rotated
     */
    private AVLNode<T> rotateLeft(AVLNode<T> curr) {
        AVLNode<T> rightChild = curr.getRight();
        curr.setRight(rightChild.getLeft());
        rightChild.setLeft(curr);
        update(curr);
        update(rightChild);
        return rightChild;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = remove(root, data, removed);
        return removed.getData();
    }

    /**
     * Helper method for remove(T data).
     *
     * @param curr the root of the tree
     * @param data the data to remove from the tree.
     * @param removed the node that stores the removed node's data
     * @return the new balanced node after removal
     */
    private AVLNode<T> remove(AVLNode<T> curr, T data, AVLNode<T> removed) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is not in the "
                    + "tree.");
        }
        if (curr.getData().compareTo(data) > 0) { // traverse left side
            curr.setLeft(remove(curr.getLeft(), data, removed));
        } else if (curr.getData().compareTo(data) < 0) { // traverse right side
            curr.setRight(remove(curr.getRight(), data, removed));
        } else { // data found
            removed.setData(curr.getData());
            size--;
            if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                AVLNode<T> child = new AVLNode<>(null);
                curr.setRight(successor(curr.getRight(), child));
                curr.setData(child.getData());
            }
        }
        update(curr);
        return rebalance(curr);
    }

    /**
     * Finds the successor of the node.
     *
     * @param curr the node being inspected
     * @param child the child of the node to be removed
     * @return the successor of the node to be removed
     */
    private AVLNode<T> successor(AVLNode<T> curr, AVLNode<T> child) {
        if (curr.getLeft() == null) {
            child.setData(curr.getData());
            return curr.getRight();
        }
        curr.setLeft(successor(curr.getLeft(), child));
        update(curr);
        return rebalance(curr);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in. Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return get(root, data);
    }

    /**
     * Helper method for get(T data)
     *
     * @param curr the root of the tree
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in. Return the data that was stored in the
     * tree.
     */
    private T get(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is not in the "
                    + "tree.");
        } else if (curr.getData().compareTo(data) > 0) { // traverse left side
            return get(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) { // traverse right side
            return get(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        try {
            get(data);
        } catch (java.util.NoSuchElementException exception) {
            return false;
        }
        return true;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (!contains(data) || root == null) {
            throw new java.util.NoSuchElementException("Data is not in the "
                    + "tree.");
        }
        return predecessor(root, data);
    }

    /**
     * Helper method for predecessor(T data)
     *
     * @param curr the root of the tree
     * @param data the data to find the predecessor of
     * @return the predecessor of the data. If there is no smaller data than the
     * one given, return null.
     */
    private T predecessor(AVLNode<T> curr, T data) {
        AVLNode<T> predecessor = null;
        while (curr.getData().compareTo(data) != 0) {
            if (curr.getData().compareTo(data) > 0) {
                curr = curr.getLeft();
            } else {
                predecessor = curr;
                curr = curr.getRight();
            }
        }
        if (curr != null && curr.getLeft() != null) {
            curr = curr.getLeft();
            while (curr.getRight() != null) {
                curr = curr.getRight();
            }
            predecessor = curr;
        }
        if (predecessor == null) {
            return null;
        }
        return predecessor.getData();
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k cannot be less than 0 or "
                    + "greater than the number of data in the tree.");
        }
        List<T> smallest = new LinkedList<>();
        kSmallest(root, smallest, k);
        return smallest;
    }

    /**
     * Returns the k-smallest elements from the AVL in sorted order, least to
     * greatest.
     *
     * @param curr the root of the tree
     * @param smallest the list that will hold the k-smallest elements
     * @param k the number of smallest elements to return
     */
    private void kSmallest(AVLNode<T> curr, List<T> smallest, int k) {
        if (curr != null) {
            kSmallest(curr.getLeft(), smallest, k);
            if (smallest.size() < k) {
                smallest.add(curr.getData());
            }
            kSmallest(curr.getRight(), smallest, k);
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Finds the height of the node
     *
     * @param curr the node to find the height for
     * @return the height of the node
     */
    private int height(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        return curr.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}