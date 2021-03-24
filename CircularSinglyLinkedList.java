/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
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
import java.util.NoSuchElementException;
public class CircularSinglyLinkedList<T> {


    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered into the data structure is null.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index:" + index + " entered is either is < 0 or >" + size
                    + "of the list.");
        } else {
            CircularSinglyLinkedListNode<T> nod = new CircularSinglyLinkedListNode<T>(data, null);
            CircularSinglyLinkedListNode<T> tempCurrent = head;
            CircularSinglyLinkedListNode<T> tempPrevious = null;
            int count = 0;
            if (head == null && index == 0) {
                head = nod;
                head.setNext(head);
                size = 1;
            } else {
                if (head != null && index == 0) {
                    nod.setData(head.getData());
                    head.setData(data);
                    nod.setNext(head.getNext());
                    head.setNext(nod);
                } else {
                    while (count < index) {
                        tempPrevious = tempCurrent;
                        tempCurrent = tempCurrent.getNext();
                        count = count + 1;
                    }
                    nod.setNext(tempCurrent);
                    tempPrevious.setNext(nod);
                }
                size++;
            }
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered into the data structure is null.");
        }
        addAtIndex(0, data);
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered into the data structure is null.");
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + " entered is either is < 0 or >=" + size
                    + "of the list.");
        } else {
            CircularSinglyLinkedListNode<T> node = head;
            CircularSinglyLinkedListNode<T> prev = null;
            T remove = null;
            if (index == 0 && head.getNext() != head) {
                remove = head.getData();
                head.setData(head.getNext().getData());
                head.setNext(head.getNext().getNext());
                size--;
                return remove;
            } else if (index == 0 && head.getNext().equals(head)) {
                remove = head.getData();
                head = null;
                size--;
                return remove;
            } else {
                for (int i = 0; i < index; i++) {
                    prev = node;
                    node = node.getNext();
                }
                remove = node.getData();
                prev.setNext(node.getNext());
                size--;
                return remove;
            }
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("You cannot remove from the front because the list is empty.");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("You cannot remove from the back because the list is empty.");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + " entered is either is < 0 or >=" + size
                    + "of the list.");
        }
        if (index == 0) {
            return head.getData();
        }
        int count = 0;
        CircularSinglyLinkedListNode<T> temp = head;
        while (count < index) {
            temp = temp.getNext();
            count = count + 1;
        }
        return temp.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException if data is not found
     */

    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you entered into the data structure is null.");
        }
        int count = 0;
        CircularSinglyLinkedListNode<T> curr = head;
        CircularSinglyLinkedListNode<T> prev = head;
        CircularSinglyLinkedListNode<T> lastO = null;
        CircularSinglyLinkedListNode<T> next = null;
        T reference = null;
        while (count < size) {
            if (curr.getData().equals(data)) {
                lastO = prev;
                reference = curr.getData();
                next = curr.getNext();
                prev = curr;
                curr = curr.getNext();
            } else {
                prev = curr;
                curr = curr.getNext();
            }
            count++;
        }
        if (reference == null) {
            throw new NoSuchElementException(data.toString() + "could not be found in this list.");
        }
        if (lastO.equals(head)) {
            removeAtIndex(0);
        } else {
            lastO.setNext(next);
            size--;
        }
        return reference;
    }
    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] toArray = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> tempC = head;
        int count = 0;
        while (count < size) {
            toArray[count] = tempC.getData();
            tempC = tempC.getNext();
            count = count + 1;
        }
        return toArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
