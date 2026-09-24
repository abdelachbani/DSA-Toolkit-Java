package datastructures.interfaces;

/**
 * Model of a Priority Queue, or Dynamic Search 
 * of the Element with maximum priority in a Collection
 * 
 * @param <E> data type of the structure,  
 * RESTRICTED BY Comparable 
 */
public interface PriorityQueue<E extends Comparable<E>> {

    // Methods that modify the state of a Priority Queue (PQ)
    /** According to its priority, inserts the Element e into a PQ */
    void insert(E e);
    
    /** PRECONDITION: !isEmpty(): 
     *  gets and removes the Element with maximum priority from a PQ */
    E removeMin();
    
    // Methods that consult the state of a Priority Queue (PQ)
    /** PRECONDITION: !isEmpty(): 
     *  gets the Element with maximum priority from a PQ */
    E getMin();
    
    /** Checks if a PQ is empty */
    boolean isEmpty();
}
