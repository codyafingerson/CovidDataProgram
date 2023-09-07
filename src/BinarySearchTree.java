// Portions of this code are taken from Algorithms, 4th ed by Sedgewick and Wayne

import java.util.NoSuchElementException;

/**
 * Binary search tree of generic key-value pairs
 * @param <Key>
 * @param <Value>
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @author Cody Fingerson
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private Node root; // root of BST

    private class Node {
        private Key key; // sorted by key
        private Value val; // associated data with the key
        private Node left, right; // left and right subtrees
        private int size; // number of nodes in subtree

        private Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Creates an empty binary search tree
     */
    public BinarySearchTree() {}

    /**
     * Check if the tree is empty
     * @return {@code true} if empty {@code false} if not empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the size of the entire tree
     * @return size of the tree
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * See if the tree contains a specified key
     * @param key key to search for
     * @return {@code true} if found {@code false} if not found
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the specified key
     * @param key the key to return the data of
     * @return the data associated with the given key
     */
    public Value get(Key key) {
        return get(root, key);
    }

    // Recursion method
    // Get the value associated with the key
    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null; // if the given no is empty, return.
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key); // If the given key is less than the current key, place on the left.
        else if (cmp > 0) return get(x.right, key); // If the given key is greater than the current key, place on the right.
        else return x.val; // If it is equal, return the value
    }

    /**
     * Insert key-value pair into the tree
     * @param key the key
     * @param val the data associated with the key
     */
    public void put(Key key, Value val) {
        // Check if the value is empty, if it is, delete the key to keep the bst clean.
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }

    // Recursion method
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1); // if current node is empty, create a new node
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val); // if the given node is less than the current node, place it on the left
        else if (cmp > 0) x.right = put(x.right, key, val); // if the given node is create than the current node, place it on the right
        x.size = 1 + size(x.left) + size(x.right); // Increase the size
        return x;
    }

    /**
     * Deletes the minimum key-value pai
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    // Recursion method
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right; // if the left leaf is null, remove the right leaf
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Delete the value associated with the key
     * @param key the key to delete
     */
    public void delete(Key key) {
        root = delete(root, key);
    }

    // Recursion method
    private Node delete(Node x, Key key) {
        if(x == null) return null;
        // search for key
        int cmp = key.compareTo(x.key);
        if     (cmp < 0) x.left  = delete(x.left,  key);
        else if(cmp > 0) x.right = delete(x.right, key);
        else {
            if(x.right == null) return x.left;  // no right child
            if(x.left  == null) return x.right; // no left child
            // replace with successor
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        // update subtree counts
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Returns the smallest key found in the tree
     * @return the smallest key
     */
    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Returns the largest key found in the tree
     * @return the largest key
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    /**
     * Return the amount of keys in the tree strictly less than the given key
     * @param key the key
     * @return amount of keys less than key
     */
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0; // return null if empty
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    /**
     * Iteration for in-order traversal of tree
     * @return all keys in the tree in-order (ascending order)
     */
    public Iterable<Key> inorderTraversal() {
        if (isEmpty()) return new Queue<Key>();
        return inorderTraversal(min(), max());
    }

    private Iterable<Key> inorderTraversal(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        inorderTraversal(root, queue, lo, hi);
        return queue;
    }

    private void inorderTraversal(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) inorderTraversal(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) inorderTraversal(x.right, queue, lo, hi);
    }

    /**
     * Return the size of the tree in the given range
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in the tree in the given interval
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

}