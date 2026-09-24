package datastructures.interfaces;

/**
 * Queue Model: FIFO access to the elements of a collection
 * @param <E> data type of the structure
 */
public interface Queue<E> {
    
    // Methods that modify the state of a Queue:
    /** Inserts the Element e into a Queue, or places it at its end */
    void enqueue(E e);
    
    /** PRECONDITION: !isEmpty(): 
     *  gets and removes from a Queue the Element that occupies its front */
    E dequeue();
    
    // Methods that consult the state of a Queue:
    /** PRECONDITION: !isEmpty(): gets the Element that occupies the front 
      * of a Queue, the first in order of insertion */
    E front();
    
    /** Checks if a Queue is empty */
    boolean isEmpty();
}
