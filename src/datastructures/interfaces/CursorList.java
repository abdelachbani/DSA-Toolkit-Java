package datastructures.interfaces;

/**
 * Model of a Cursor List (CL), 
 * or Sequential Access to the Elements of a
 * Collection
 *  
 * @param <E> type of the data of List
 */
public interface CursorList<E> {

    // Methods that modify the state of a List
    /** Inserts e into a List before the Element occupied by its Cursor, 
     *  which remains unchanged */
    void insert(E e);
    
    /** PRECONDITION: !isEnd(): 
     *  removes from a List the Element occupied by its Cursor, 
     *  which remains unchanged */
    void remove();
    
    // Methods that modify the state of the Cursor of a List
    /** Places the Cursor of a List at its beginning */
    void begin();
    
    /** PRECONDITION: !isEnd(): 
     *  advances the Cursor of a List */
    void next();
    
    /** Places the Cursor of a List at its end */
    void end();
    
    // Methods that consult the state of a List
    /** PRECONDITION: !isEnd(): 
     *  gets the Element occupied by the Cursor of a List */
    E get();
    
    /** Checks if the Cursor of a List is at its end */
    boolean isEnd();
    
    /** Checks if a Cursor List is empty */
    boolean isEmpty();
    
    /** Returns the size, or number of elements, of a List */
    int size();
}
