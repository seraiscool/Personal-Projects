import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Sera Biju
 * @version 1.0
 * @userid sbiju3
 * @GTID 903560067
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data into a AVL.");
        } else {
            for (T value : data) {
                if (value == null) {
                    throw new IllegalArgumentException("You can't enter data with a null element.");
                }
                root = newRotateAdd(root, value);
            }
        }
    }

    /**
     * Checks for rotations as necessary within the nodes.
     * @param current starting node to rotate from
     * @return the entire AVL node with subtrees
     */
    private AVLNode<T> rotationCheck(AVLNode<T> current) {
        updateHeight(current);
        updateBF(current);
        int balance = current.getBalanceFactor();
        if (balance > 1 && current.getLeft().getBalanceFactor() < 0) {
            current.setLeft(leftRotate(current.getLeft()));
            return rightRotate(current);
        } else if (balance < -1 && current.getRight().getBalanceFactor() > 0) {
            current.setRight(rightRotate(current.getRight()));
            return leftRotate(current);
        } else if (balance > 1) {
            return rightRotate(current);
        } else if (balance < -1) {
            return leftRotate(current);
        }
        return current;
    }
    /**
     * Private method which recursively goes through the method to add new nodes, and has two parameters.
     *
     * @param current is the AVL node to traverse through
     * @param data  the generic value meant to be added to the AVL.
     * @return the current node BST node added to the Tree.
     */
    private AVLNode<T> newRotateAdd(AVLNode<T> current, T data) {
        if (current == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(newRotateAdd(current.getLeft(), data));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(newRotateAdd(current.getRight(), data));
        }
        return rotationCheck(current);
    }
    /**
     * Compares two int values and return the max between them
     * @param value1 the first value
     * @param value2 the second value
     * @return the maximum value between the two values
     */
    private int maxValue(int value1, int value2) {
        return value1 > value2 ? value1 : value2;
    }

    /**
     * Updates height after rotations to the new height
     * @param node the node that needs to update its height.
     */
    private void updateHeight(AVLNode<T> node) {
        if (node == null) {
            node.setHeight(0);
        }
        node.setHeight(maxValue(getNewHeight(node.getLeft()), getNewHeight(node.getRight())) + 1);
    }

    /**
     * Goes through the tree to find new height of  node.
     * @param node for which we need to get new height
     * @return the height of node
     */
    private int getNewHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     * Updates balance factor after rotations to the new balance factor
     * @param node that needs to update its balance factor
     */
    private void updateBF(AVLNode<T> node) {
        if (node != null) {
            node.setBalanceFactor(getNewHeight(node.getLeft()) - getNewHeight(node.getRight()));
        }
    }
    /**
     * Private method which recursively goes through the method to rotate nodes left due to their balance factor.
     * The balance factor must be -1,0, or 1, if not rotate and return the new parent node.
     * @param current is the AVL node to traverse through
     * @return the new parent AVL node in to the Tree.
     */
    private AVLNode<T> leftRotate(AVLNode<T> current) {
        AVLNode<T> newParent = current.getRight();
        current.setRight(newParent.getLeft());
        newParent.setLeft(current);
        updateHeight(current);
        updateHeight(newParent);
        updateBF(current);
        updateBF(newParent);
        return newParent;
    }
    /**
     * Private method which recursively goes through the method to rotate nodes right due to their balance factor.
     * The balance factor must be -1,0, or 1, if not rotate and return the new parent node.
     * @param current is the AVL node to traverse through
     * @return the new parent AVL node in to the Tree.
     */
    private AVLNode<T> rightRotate(AVLNode<T> current) {
        AVLNode<T> newParent = current.getLeft();
        current.setLeft(newParent.getRight());
        newParent.setRight(current);
        updateHeight(current);
        updateHeight(newParent);
        updateBF(current);
        updateBF(newParent);
        return newParent;
    }
    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data or data with a null element.");
        }
        root = newRotateAdd(root, data);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data or data with a null element.");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = newRemove(root, data, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException(data + " isn't found in the tree");
        }
        size--;
        return dummy.getData();
    }
    /**
     * Returns the predecessor node.
     * @param root the node through which to traverse to find the predecessor element
     * @param dummy2 is the tracker node created to keep track of the parent node through the traversal
     * @return return the closet predecessor value
     */
    private AVLNode<T> predecessorElement(AVLNode<T> root, AVLNode<T> dummy2) {
        if (root.getRight() == null) {
            dummy2.setData(root.getData());
            return root.getLeft();
        } else {
            root.setRight(predecessorElement(root.getRight(), dummy2));
            return rotationCheck(root);
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
    private AVLNode<T> newRemove(AVLNode<T> current, T data, AVLNode<T> dummy) {
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
                AVLNode<T> dummy2 = new AVLNode<T>(null);
                current.setLeft(predecessorElement(current.getLeft(), dummy2));
                current.setData(dummy2.getData());
            }
        }
        return rotationCheck(current);
    }
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data or data with a null element.");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
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
    private AVLNode<T> getRemove(AVLNode<T> current, T data, AVLNode<T> dummy) {
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
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data or data with a null element.");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = getRemove(root, data, dummy);
        if (dummy.getData() != null && dummy.getData().equals(data)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return getNewHeight(root);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not contain duplicate data, and the data of a branch
     * should be listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        ArrayList<T> pos = new ArrayList<T>();
        preOrder(root, pos);
        return pos;
    }
    /**
     * Returns the preorder traversal of the branches in an AVL tree
     * @param node the node taken in run the pre order of the tree.
     * @param post is the arraylist to add the values to
     */
    private void preOrder(AVLNode<T> node, ArrayList<T> post) {
        if (node != null) {
            if (node.getBalanceFactor() > 0) {
                post.add(node.getData());
                preOrder(node.getLeft(), post);
            } else if (node.getBalanceFactor() < 0) {
                post.add(node.getData());
                preOrder(node.getRight(), post);
            } else {
                post.add(node.getData());
                preOrder(node.getLeft(), post);
                preOrder(node.getRight(), post);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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