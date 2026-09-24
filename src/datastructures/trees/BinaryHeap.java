package datastructures.trees;

import datastructures.interfaces.PriorityQueue;

/** Class BinaryHeap: represents a Heap with Root at 1.
 * 
 *  Its characteristics are the following:
 *  @param <E> the type of its elements is E extends Comparable<E>
 *  ATTRIBUTES (protected for inheritance): 
 *      HAS AN array with the elements (E[] array)
 *      HAS A size that indicates the number of elements
 **/
public class BinaryHeap<E extends Comparable<E>> 
    implements PriorityQueue<E> {  
    
    protected static final int DEFAULT_CAPACITY = 11; 
    
    // A Heap is a Complete BT and, therefore,...
    // HAS AN Implicit Representation
    protected E[] array;
    // HAS A size or number of nodes
    protected int size;
    
    /** Creates an empty Priority Queue (PQ) 
     *  with initial capacity DEFAULT_CAPACITY
     */
    public BinaryHeap() { this(DEFAULT_CAPACITY); }
    
    /** Creates an empty Priority Queue (PQ) 
     * with initial capacity n 
     */
    @SuppressWarnings("unchecked")
    public BinaryHeap(int n) { 
        array = (E[]) new Comparable[n];
        size = 0;
    }
    
    /** Checks if a PQ is empty or not 
     *  @return boolean that indicates if the PQ is empty
     */
    public boolean isEmpty() { return size == 0; }
        
    /** PRECONDITION: !isEmpty(): gets the data with maximum priority of the PQ 
     *  @return Element with maximum priority of the PQ
     */
    public E getMin() { return array[1]; }
    
    /** Inserts the data e into a PQ, according to its priority 
     *  @param e  Element to insert  
     */
    public void insert(E e) {
        if (size == array.length - 1) { resize(); }
        int  insPos = ++size;
        while (insPos > 1 && e.compareTo(array[insPos / 2]) < 0) { 
            array[insPos] = array[insPos / 2]; 
            insPos = insPos / 2;
        }
        array[insPos] = e;
    }
   
    // Doubles the capacity of array
    @SuppressWarnings("unchecked")
    protected void resize() {
        E[] newArray = (E[]) new Comparable[array.length * 2];
        System.arraycopy(array, 1, newArray, 1, size);
        array = newArray;
    }  
    
    /** PRECONDITION: !isEmpty(): gets and deletes the data with 
     *  maximum priority of the PQ 
     *  @return Element with maximum priority of the PQ
     */
    public E removeMin() {
        E minElement = array[1];
        array[1] = array[size--];
        siftDown(1);
        return minElement;
    }
    
    //  If necessary, restores the order property 
    //  of a Heap by sifting down the element of array located 
    //  at position pos 
    //  @param pos  Position of the element to sift down
    protected void siftDown(int pos) {
        int currentPos = pos;
        E toSift = array[currentPos]; 
        int child = currentPos * 2; 
        boolean isHeap = false;
        while (child <= size && !isHeap) {
            if (child != size 
                && array[child + 1].compareTo(array[child]) < 0) { 
                child++; 
            }
            if (array[child].compareTo(toSift) < 0) {
                array[currentPos] = array[child];
                currentPos = child; 
                child = currentPos * 2;
            } else { isHeap = true; }
        }
        array[currentPos] = toSift;
    }
}
