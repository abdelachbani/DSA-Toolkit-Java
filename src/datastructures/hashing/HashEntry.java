package datastructures.hashing;

/**
 * Represents an element of the CursorList that implements   
 * a bucket of a HashTable, this is an Entry or pair
 * (key, value)
 * 
 * @param <K> the type of the key
 * @param <V> the type of the value associated to a key
 */
class HashEntry<K, V> {
    
    protected K key;
    protected V value;

    HashEntry(K k, V v) {
        key = k;
        value = v;
    }
    
    public String toString() { 
        return "(" + key + ", " + value + ")"; 
    }
}
