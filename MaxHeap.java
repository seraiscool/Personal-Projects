import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Sera Biju
 * @version 1.0
 * @userid sbiju3
 * @GTID 903560067
 *
 * Collaborators: Elijah Hopper
 *
 * Resources: https://www.geeksforgeeks.org, https://stackoverflow.com/
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!(T[]) new Object[INITIAL_CAPACITY];
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Using the Down-Heap Recursion to Build Up from subtrees, called by MaxHeap overloaded constructor
     * @param arr T[] that includes all the data point values
     * @param i the index we refer for pointer
     */
    private void downHeapit(T[] arr, int i) {
        int largest = i;
        int left = 2 * i;
        int right = 2 * i + 1;

        if (left <= size && arr[left].compareTo(arr[largest]) > 0) {
            largest = left;
        }
        if (right <= size && arr[right].compareTo(arr[largest]) > 0) {
            largest = right;
        }
        if (largest != i) {
            T interchange = arr[i];
            arr[i] = arr[largest];
            arr[largest] = interchange;
            // Recursively going through the process to build the down-heap from subtrees
            downHeapit(arr, largest);
        }
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data into a MaxHeap.");
        } else {
            int i = 1;
            backingArray = (T[]) new Comparable[2 * data.size() + 1];
            for (T value : data) {
                if (value == null) {
                    throw new IllegalArgumentException("You can't enter data with a null element into the MaxHeap.");
                }
                backingArray[i] = value;
                size++;
                i++;
            }
            int startIdx = (data.size() / 2);
            for (int a = startIdx; a >= 1; a--) {
                downHeapit(backingArray, a);
            }
        }
    }


    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter null data into a MaxHeap.");
        }
        if (backingArray.length == size + 1) {
            T[] newArray = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 1; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        backingArray[++size] = data;
        // Traverse up and fix violated property
        int current = size;
        while (current > 1 && backingArray[current].compareTo(backingArray[current / 2]) > 0) {
            T change;
            change = backingArray[current];
            backingArray[current] = backingArray[current / 2];
            backingArray[current / 2] = change;
            current = current / 2;
        }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The max heap is empty");
        }
        T count = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeapit(backingArray, 1);
        return count;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("The max heap is empty");
        }
        T count = backingArray[1];
        return count;
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
