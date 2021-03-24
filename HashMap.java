import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;
/**
 * Your implementation of a HashMap.
 *
 * @author Sera Biju
 * @version 1.0
 * @userid sbiju3
 * @GTID 903560067
 *
 * Collaborators: Tanay Sonthalia, Rosa Rosado
 *
 * Resources: https://www.geeksforgeeks.org/,https://stackoverflow.com/
 */
public class HashMap<K, V> {

    /*
     * The initial capacity of the HashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the HashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new HashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new HashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        size = 0;
        table = new MapEntry[initialCapacity];
    }

    /**
     * Using the hashFunc, we try to find the integer version of the hash value of the key.
     * @param key the object key to find the value
     * @return the integer version of said key.
     */
    private int hashFunc(Object key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is okay
     * if the load factor is equal to max LF). For example, let's say the
     * array is of length 5 and the current size is 3 (LF = 0.6). For this
     * example, assume that no elements are removed in between steps. If
     * another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The HashMap entry values either have a key or value which is null.");
        }
        if (((size + 1.0) / table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        V reference = null;
        int hash = hashFunc(key);
        MapEntry<K, V> entry = table[hash];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                reference = entry.getValue();
                entry.setValue(value);
                return reference;
            }
            entry = entry.getNext();
        }
        MapEntry<K, V> pointer = table[hash];
        table[hash] = new MapEntry(key, value);
        table[hash].setNext(pointer);
        size++;
        return reference;
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key entered into the method is null");
        }
        MapEntry<K, V> entry = table[hashFunc(key)];
        MapEntry<K, V> pointer = null;
        V reference = null;
        while (entry != null) {
            if (key.equals(entry.getKey())) {
                size--;
                reference = entry.getValue();
                if (entry.equals(table[hashFunc(key)])) {
                    table[hashFunc(key)] = entry.getNext();
                } else {
                    pointer.setNext(entry.getNext());
                }
                return reference;
            }
            pointer = entry;
            entry = entry.getNext();
        }
        if (reference == null) {
            throw new NoSuchElementException("The key entered into the method doesn't exist.");
        }
        return reference;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key entered into the method is null");
        }
        MapEntry<K, V> entry = table[hashFunc(key)];
        V reference = null;
        while (entry != null) {
            if (key.equals(entry.getKey())) {
                reference = entry.getValue();
                return reference;
            }
            entry = entry.getNext();
        }
        if (reference == null) {
            throw new NoSuchElementException("The key entered into the method doesn't exist.");
        }
        return reference;
    }
    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key entered into the method is null");
        }
        MapEntry<K, V> entry = table[hashFunc(key)];
        while (entry != null) {
            if (key.equals(entry.getKey())) {
                return true;
            }
            entry = entry.getNext();
        }
        return false;
    }


    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> key = new HashSet();
        for (int index = 0; index < table.length; index++) {
            MapEntry<K, V> entry = table[index];
            while (entry != null) {
                key.add(entry.getKey());
                entry = entry.getNext();
            }
            if (key.size() == size) {
                return key;
            }
        }
        return key;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> value = new LinkedList();
        for (int index = 0; index < table.length; index++) {
            MapEntry<K, V> entry = table[index];
            while (entry != null) {
                value.add(entry.getValue());
                entry = entry.getNext();
            }
            if (value.size() == size) {
                return value;
            }
        }
        return value;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("The length is less than the items in the Hash Map");
        }
        MapEntry<K, V>[] reference = table;
        table = new MapEntry[length];
        int hash = 0;
        for (int index = 0; index < reference.length; index++) {
            MapEntry<K, V> entry = reference[index];
            while (entry != null) {
                hash = hashFunc(entry.getKey());
                table[hash] = new MapEntry(entry.getKey(), entry.getValue(), table[hash]);
                entry = entry.getNext();
            }
        }

    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     */
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
