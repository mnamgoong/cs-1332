import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of a Binary Search Tree (BST).
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 *
 */
public class BST<T extends Comparable<? super T>> {

    private BSTNode<T> root;
    private int size;

    /**
     * Constructs and initializes a new empty BST.
     */
    public BST() {
        // instance variables are initialized to default values
    }

    /**
     * Constructs and initializes a new BST with the data in the Collection,
     * added in the same order.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Data cannot be null.");
            } else {
                add(element);
            }
        }
    }

    /**
     * Recursively adds the data to the tree. The data becomes a leaf in the
     * tree.
     *
     * O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        root = add(root, data);
    }

    /**
     * A private helper method that traverses the tree to find the
     * appropriate location to add the data. If the data is already in the
     * tree, then nothing is done (the duplicate isn't added, and size isn't
     * incremented).
     *
     * @param curr the node to compare against
     * @param data the data to add
     * @return the node that was added
     */
    private BSTNode<T> add(BSTNode<T> curr, T data) {
        if (curr == null) { // empty root or leaf node
            size++;
            return new BSTNode<>(data);
        } else if (curr.getData().compareTo(data) > 0) { // add to left side
            curr.setLeft(add(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) { // add to right side
            curr.setRight(add(curr.getRight(), data));
        } else { // duplicate
            return curr;
        }
        return curr;
    }

    /**
     * Recursively removes and returns the data from the tree matching the
     * given parameter.
     *
     * O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (!contains(data) || root == null) {
            throw new java.util.NoSuchElementException("Data is not in the "
                    + "tree.");
        }
        root = remove(root, data);
        size--;
        return data;
    }

    /**
     * A private helper method that finds and removes the data from the tree
     * and returns the new root.
     *
     * Considers 3 cases:
     * 1: The node containing the data is a leaf (no children). The data is
     * simply removed.
     * 2: The node containing the data has one child. The data is simply
     * replaced with its child.
     * 3: The node containing the data has 2 children. The successor is
     * found and used to replace the data.
     *
     * @param curr the node to compare against
     * @param data the data to add
     * @return the new root
     */
    private BSTNode<T> remove(BSTNode<T> curr, T data) {
        if (curr.getData().compareTo(data) > 0) { // traverse left side
            curr.setLeft(remove(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) { // traverse right side
            curr.setRight(remove(curr.getRight(), data));
        } else { // data found
            // one child
            if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            }
            // two children
            curr.setData(successor(curr.getRight()).getData());
            curr.setRight(remove(curr.getRight(), curr.getData()));
        }
        return curr;
    }

    /**
     * Finds the given node's successor.
     *
     * @param curr the node to get a successor for
     * @return the successor
     */
    private BSTNode<T> successor(BSTNode<T> curr) {
        BSTNode<T> successor = curr;
        while (curr.getLeft() != null) {
            successor = curr.getLeft();
            curr = curr.getLeft();
        }
        return successor;
    }

    /**
     * Recursively finds and returns the data from the tree matching the given
     * parameter.
     *
     * O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (!contains(data) || root == null) {
            throw new java.util.NoSuchElementException("Data is not in the "
                    + "tree.");
        }
        return search(root, data);
    }

    /**
     * A private helper method that finds and returns the data from the tree.
     *
     * @param curr the node to compare against
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     */
    private T search(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        } else if (curr.getData().compareTo(data) > 0) { // traverse left side
            return search(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) { // traverse right side
            return search(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Recursively finds and returns whether or not data matching the given
     * parameter is contained within the tree.
     *
     * O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        T contained = search(root, data);
        if (contained == null) {
            return false;
        }
        return true;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Recursive and O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        LinkedList<T> preordered = new LinkedList<>();
        preorder(root, preordered);
        return preordered;
    }

    /**
     * A private helper method that generates a pre-order traversal of the tree.
     *
     * @param curr the root being looked at
     * @param preordered the LinkedList to contain the pre-order traversal
     */
    private void preorder(BSTNode<T> curr, LinkedList<T> preordered) {
        if (curr != null) {
            preordered.add(curr.getData());
            preorder(curr.getLeft(), preordered);
            preorder(curr.getRight(), preordered);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * Recursive and O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        LinkedList<T> inordered = new LinkedList<>();
        inorder(root, inordered);
        return inordered;
    }

    /**
     * A private helper method that generates an in-order traversal of the tree.
     *
     * @param curr the node being looked at
     * @param inordered the LinkedList to contain the in-order traversal
     */
    private void inorder(BSTNode<T> curr, LinkedList<T> inordered) {
        if (curr != null) {
            inorder(curr.getLeft(), inordered);
            inordered.add(curr.getData());
            inorder(curr.getRight(), inordered);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Recursive and O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        LinkedList<T> postordered = new LinkedList<>();
        postorder(root, postordered);
        return postordered;
    }

    /**
     * A private helper method that generates a post-order traversal of the
     * tree.
     *
     * @param curr the node being looked at
     * @param postordered the LinkedList to contain the post-order traversal
     */
    private void postorder(BSTNode<T> curr, LinkedList<T> postordered) {
        if (curr != null) {
            postorder(curr.getLeft(), postordered);
            postorder(curr.getRight(), postordered);
            postordered.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * Recursive and O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        LinkedList<T> levelordered = new LinkedList<>();
        if (root == null) {
            return null;
        }
        Queue<BSTNode> nodes = new LinkedList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            BSTNode<T> removed = nodes.remove();
            levelordered.add(removed.getData());
            if (removed.getLeft() != null) {
                nodes.add(removed.getLeft());
            }
            if (removed.getRight() != null) {
                nodes.add(removed.getRight());
            }
        }
        return levelordered;
    }

    /**
     * Recursively finds and returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * O(n)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return height(root);
    }

    /**
     * A private helper method that finds and returns the height of the tree.
     *
     * @param curr the node being looked at
     * @return the height of the tree
     */
    private int height(BSTNode<T> curr) {
        if (curr == null) { // null child
            return -1;
        }
        int leftHeight = height(curr.getLeft());
        int rightHeight = height(curr.getRight());
        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        } else {
            return rightHeight + 1;
        }
    }

    /**
     * Clears the tree. Clears all data and resets the size.
     *
     * O(1)
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * First finds the deepest common ancestor of both data and adds it to the
     * list. Then traverses to data1 while adding its ancestors to the front
     * of the list. Finally, traverses to data2 while adding its ancestors to
     * the back of the list. Note that there is no relationship between the
     * data parameters in that they may not belong to the same branch.
     *
     * If both data1 and data2 are equal and in the tree, the list will be of
     * size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) returns the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) returns the list [50, 25, 37]
     * findPathBetween(75, 75) returns the list [75]
     *
     * O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (!contains(data1) || !contains(data2)) {
            throw new java.util.NoSuchElementException("Data is not in the "
                    + "tree.");
        }
        LinkedList<T> path = new LinkedList<>();
        if (data1.equals(data2)) {
            path.add(data1);
            return path;
        }
        BSTNode<T> ancestor = ancestor(root, data1, data2);
        path.add(ancestor.getData());
        findPathToData1(path, ancestor, data1);
        findPathToData2(path, ancestor, data2);
        return path;
    }

    /**
     * A private helper method that traverses to data1 while adding its
     * ancestors to the front of the list.
     *
     * @param path the LinkedList to add the path to data1
     * @param ancestor the deepest common ancestor
     * @param data1 the data1 to find a path to
     */
    private void findPathToData1(LinkedList<T> path, BSTNode<T> ancestor,
                                 T data1) {
        if (ancestor.getData().compareTo(data1) > 0) {
            path.addFirst(ancestor.getLeft().getData());
            findPathToData1(path, ancestor.getLeft(), data1);
        } else if (ancestor.getData().compareTo(data1) < 0) {
            path.addFirst(ancestor.getRight().getData());
            findPathToData1(path, ancestor.getRight(), data1);
        } else {
            return;
        }
    }

    /**
     * A private helper method that traverses to data2 while adding its
     * ancestors to the back of the list.
     *
     * @param path the LinkedList to add the path to data2
     * @param ancestor the deepest common ancestor
     * @param data2 the data2 to find a path to
     */
    private void findPathToData2(List<T> path, BSTNode<T> ancestor, T data2) {
        if (ancestor.getData().compareTo(data2) > 0) {
            path.add(ancestor.getLeft().getData());
            findPathToData2(path, ancestor.getLeft(), data2);
        } else if (ancestor.getData().compareTo(data2) < 0) {
            path.add(ancestor.getRight().getData());
            findPathToData2(path, ancestor.getRight(), data2);
        } else {
            return;
        }
    }

    /**
     * A private helper method to find the deepest common ancestor between
     * data1 and data2.
     *
     * @param curr the node being looked at
     * @param data1 the data1 being considered
     * @param data2 the data2 being considered
     * @return the deepest common ancestor
     */
    private BSTNode<T> ancestor(BSTNode<T> curr, T data1, T data2) {
        if (curr.getData().compareTo(data1) > 0
                && curr.getData().compareTo(data2) > 0) {
            return ancestor(curr.getLeft(), data1, data2);
        } else if (curr.getData().compareTo(data1) < 0
                && curr.getData().compareTo(data2) < 0) {
            return ancestor(curr.getRight(), data1, data2);
        } else {
            return curr;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * @return the size of the tree
     */
    public int size() {
        return size;
    }
}
