package datastructures.interfaces;

/**
 * Dictionary Model WITHOUT repeated keys, or Map: Dynamic Search 
 * of an Entry for a given Key in a Collection, to recover the 
 * Value associated with it 
 * @param <K> the type of its Keys
 * @param <V> the type of its Values
 */
public interface Map<K, V> {

    /** inserts the Entry (k,v) in a Map and returns null; if
     *  an Entry with Key k already exists in the Map, returns its associated 
     *  value and replaces it with v (or updates the Entry)
     */
    V put(K k, V v);
    
    /** removes the Entry with Key k from a Map and returns its 
     *  associated value, null if an Entry with such key does not exist
     */
    V remove(K k);
    
    /** returns the value associated with key k in a Map,
     *  null if an Entry with such key does not exist
     */
    V get(K k);
    
    /** checks if a Map is empty */
    boolean isEmpty();
    
    /** returns the size, or number of Entries, of a Map */
    int size();
    
    /** returns a CursorList with the size() Keys of a Map */
    CursorList<K> keys();
}
