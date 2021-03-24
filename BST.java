import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
/**
 * Your implementation of a BST.
 *
 * @author Sera Biu
 * @version 1.0
 * @userid sbiju3
 * @GTID 903560067
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: https://www.geeksforgeeks.org/, https://java2blog.com/
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;


    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data into a BST.");
        } else {
            for (T value : data) {
                if (value == null) {
                    throw new IllegalArgumentException("You can't enter data with a null element.");
                }
                root = newadd(root, value);
            }
        }
    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data or data with a null element.");
        }
        root = newadd(root, data);
    }

    /**
     * Private method which recursively goes through the method to add new nodes, and has two parameters.
     *
     * @param current is the BST node to traverse through
     * @param data  the generic value meant to be added to the BST.
     * @return the current node BST node added to the Tree.
     */
    private BSTNode<T> newadd(BSTNode<T> current, T data) {
        if (current == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(newadd(current.getLeft(), data));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(newadd(current.getRight(), data));
        }
        return current;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data or data with a null element.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = newRemove(root, data, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException(data + " isn't found in the tree");
        }
        size--;
        return dummy.getData();
    }
    /**
     * Returns the successor node.
     * @param root the node through which to traverse to find the successor element
     * @param dummy2 is the tracker node created to keep track of the parent node through the traversal
     * @return return the closet successor value
     */
    private BSTNode<T> successorElement(BSTNode<T> root, BSTNode<T> dummy2) {
        if (root.getLeft() == null) {
            dummy2.setData(root.getData());
            return root.getRight();
        } else {
            root.setLeft(successorElement(root.getLeft(), dummy2));
            return root;
        }
    }

    /**
     * Private method which recursively goes through the method to remove nodes, and has three parameters.
     *
     * @param current the node through which we are traversing
     * @param data    the data intended to be removed
     * @param dummy   a node meant to keep track of data removal.
     * @return the current node.
     */
    private BSTNode<T> newRemove(BSTNode<T> current, T data, BSTNode<T> dummy) {
        if (current == null) {
            return null;
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(newRemove(current.getLeft(), data, dummy));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(newRemove(current.getRight(), data, dummy));
        } else {
            dummy.setData(current.getData());
            if (current.getRight() == null && current.getLeft() == null) {
                return null;
            } else if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);

                current.setRight(successorElement(current.getRight(), dummy2));
                current.setData(dummy2.getData());
            }
        }
        return current;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data or data with a null element.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = getRemove(root, data, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException(data + " isn't found in the tree");
        }
        return dummy.getData();
    }
    /**
     * Private method which recursively goes through the method to find nodes, and has three parameters.
     * @param current the node through which we are traversing
     * @param data    the data intended to be found
     * @param dummy   a node meant to keep track of data removal.
     * @return the current node.
     */
    private BSTNode<T> getRemove(BSTNode<T> current, T data, BSTNode<T> dummy) {
        if (current == null) {
            return null;
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(getRemove(current.getLeft(), data, dummy));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(getRemove(current.getRight(), data, dummy));
        } else {
            if (data.compareTo(current.getData()) == 0) {
                dummy.setData(current.getData());
            }
        }
        return current;
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data or data with a null element.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = getRemove(root, data, dummy);
        if (dummy.getData() != null && dummy.getData().equals(data)) {
            return true;
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> pos = new ArrayList<T>();
        preOrder(root, pos);
        return (List<T>) pos;
    }
    /**
     * @param node the node taken in run the pre order of the tree.
     * @param post is the arraylist to add the values to
     */
    public void preOrder(BSTNode<T> node, ArrayList<T> post) {
        if (node != null) {
            post.add(node.getData());
            preOrder(node.getLeft(), post);
            preOrder(node.getRight(), post);
        }

    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> pos = new ArrayList<T>();
        inOrder(root, pos);
        return (List<T>) pos;
    }
    /**
     * @param node the node taken in run the in order of the tree.
     * @param post is the arraylist to add the values to
     */
    public void inOrder(BSTNode<T> node, ArrayList<T> post) {
        if (node != null) {
            inOrder(node.getLeft(), post);
            post.add(node.getData());
            inOrder(node.getRight(), post);
        }

    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> pos = new ArrayList<T>();
        postOrder(root, pos);
        return (List<T>) pos;
    }
    /**
     * @param node the node taken in run the post order of the tree.
     * @param post is the arraylist to add the values to
     */
    public void postOrder(BSTNode<T> node, ArrayList<T> post) {
        if (node != null) {
            postOrder(node.getLeft(), post);
            postOrder(node.getRight(), post);
            post.add(node.getData());
        }

    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        ArrayList<T> p = new ArrayList<T>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> node = (BSTNode<T>) q.remove();
            if (node != null) {
                p.add(node.getData());
                if (node.getLeft() != null) {
                    q.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    q.add(node.getRight());
                }
            }
        }
        return p;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return maxHeight(root);
    }
    /**
     * Private method
     * @param node the is root of the BST tree through which we traverse through
     * @return the max height of the root of the tree, -1 if the tree is empty
     */
    private int maxHeight(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int leftLength = maxHeight(node.getLeft());
        int rightLength = maxHeight(node.getRight());
        if (leftLength > rightLength) {
            return leftLength + 1;
        } else {
            return rightLength + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Determines if a binary tree is a valid BST.
     *
     * This must be done recursively. Do NOT modify the tree passed in.
     *
     * If the order property is violated, this method may not need to traverse
     * the entire tree to function properly, so you should only traverse the
     * branches of the tree necessary to find order violations and only do so once.
     * Failure to do so will result in an efficiency penalty.
     *
     * EXAMPLES: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * isBST(50) should return true, since for every node, the node's left
     * subtree is less than the node's data, and the node's right subtree is
     * greater than the node's data.
     *
     *             20
     *           /    \
     *         21      38
     *        /          \
     *       6          50
     *        \
     *         12
     *
     * isBST(20) should return false, since 21 is in 20's left subtree.
     *
     *
     * Should have a worst-case running time of O(n).
     *
     * @param node the root of the binary tree
     * @return true if the tree with node as the root is a valid BST,
     *         false otherwise
     */
    public boolean isBST(BSTNode<T> node) {
        T min = null;
        T max = null;
        return isBST(node, min, max);
    }

    /**
     * Private recursive method to find if the tree is a BST
     * @param node node entered is meant to as the starting point of the BST
     * @param min the lowest possible value for the BST
     * @param max the largest value for BST
     * @return a boolean to recursively see if the tree is a BST
     */
    private boolean isBST(BSTNode<T> node, T min, T max) {
        if (node == null) {
            return true;
        }
        if (min != null && node.getData().compareTo(min) < 0) {
            return false;
        }
        if (max != null && node.getData().compareTo(max) > 0) {
            return false;
        }
        return (isBST(node.getLeft(), min, node.getData()) && isBST(node.getRight(), node.getData(), max));
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;

    }


}
