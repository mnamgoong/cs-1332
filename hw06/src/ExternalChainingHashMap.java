import java.util.List;
import java.util.Set;

/**
 * Your implementation of a ExternalChainingHashMap.
 *
 * @author Michelle Namgoong
 * @version 1.0
 * @userid mnamgoong3
 * @GTID 903684766
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public ExternalChainingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ExternalChainingHashMap.
     *
     * The backing array should have an initial capacity of capacity.
     *
     * You may assume capacity will always be positive.
     *
     * @param capacity the initial capacity of the backing array
     */
    public ExternalChainingHashMap(int capacity) {
        table = new ExternalChainingMapEntry[capacity];
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
     * added. Resize if the load factor is greater than max LF (it is okay
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
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
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
            throw new IllegalArgumentException("Key and/or value cannot be "
                    + "null.");
        }
        if (table.length != 0
                && ((size + 1.0) / table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable((2 * table.length) + 1);
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            table[index] = new ExternalChainingMapEntry<>(key, value);
            size++;
            return null;
        } else { // element already exists at that index
            ExternalChainingMapEntry<K, V> curr = table[index];
            V oldValue;
            // traverse the existing chain
            while (curr.getNext() != null && !curr.getKey().equals(key)) {
                curr = curr.getNext();
            }
            if (curr.getKey().equals(key)) { // key found in the chain
                oldValue = curr.getValue();
                curr.setValue(value);
                size++;
                return oldValue;
            } else { // key not found in the chain so add to the front
                table[index] = new ExternalChainingMapEntry<>(key, value,
                        table[index]);
                size++;
                return null;
            }
        }
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            throw new java.util.NoSuchElementException("Key not found in the "
                    + "map.");
        }
        ExternalChainingMapEntry<K, V> curr = table[index];
        ExternalChainingMapEntry<K, V> prev = null;
        V oldValue;
        // check the first entry at that index
        if (curr.getKey().equals(key)) {
            oldValue = curr.getValue();
            table[index] = curr.getNext();
            size--;
            return oldValue;
        }
        // traverse the existing chain
        while (curr.getNext() != null && prev == null) {
            if (curr.getNext().getKey().equals(key)) {
                prev = curr;
            }
            curr = curr.getNext();
        }
        if (prev == null) { // key not found in the chain
            throw new java.util.NoSuchElementException("Key not found in "
                    + "the map");
        } else { // removes the key in the chain
            oldValue = prev.getNext().getValue();
            prev.setNext(prev.getNext().getNext());
            size--;
            return oldValue;
        }
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
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        ExternalChainingMapEntry<K, V> curr = table[index];
        if (curr == null) {
            throw new java.util.NoSuchElementException("Key not found in the "
                    + "map.");
        } else { // traverse the existing chain
            while (curr.getNext() != null && !curr.getKey().equals(key)) {
                curr = curr.getNext();
            }
            if (curr.getKey().equals(key)) {
                return curr.getValue();
            } else {
                throw new java.util.NoSuchElementException("Key not found in "
                        + "the map.");
            }
        }
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
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        ExternalChainingMapEntry<K, V> curr = table[index];
        if (curr == null) {
            return false;
        } else { // traverse the existing chain
            while (curr.getNext() != null && !curr.getKey().equals(key)) {
                curr = curr.getNext();
            }
            return (curr.getKey().equals(key));
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        java.util.HashSet<K> keys = new java.util.HashSet<>();
        for (int i = 0; i < table.length; i++) {
            ExternalChainingMapEntry<K, V> curr = table[i];
            while (curr != null) {
                keys.add(table[i].getKey());
                curr = curr.getNext();
            }
        }
        return keys;
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
        java.util.ArrayList<V> values = new java.util.ArrayList<V>();
        for (int i = 0; i < table.length; i++) {
            ExternalChainingMapEntry<K, V> curr = table[i];
            while (curr != null) {
                values.add(table[i].getValue());
                curr = curr.getNext();
            }
        }
        return values;
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
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length cannot be less than "
                    + "the number of items in the hash map.");
        }
        ExternalChainingMapEntry<K, V>[] newTable =
                new ExternalChainingMapEntry[length];
        for (int i = 0; i < table.length; i++) {
            ExternalChainingMapEntry<K, V> curr = table[i];
            while (curr != null) {
                int index = Math.abs(curr.getKey().hashCode() % length);
                newTable[index] =
                        new ExternalChainingMapEntry<>(curr.getKey(),
                                curr.getValue(), newTable[index]);
                curr = curr.getNext();
            }
        }
        table = newTable;
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     */
    public void clear() {
        table = new ExternalChainingMapEntry[INITIAL_CAPACITY];
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
    public ExternalChainingMapEntry<K, V>[] getTable() {
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
        return size;
    }
}
