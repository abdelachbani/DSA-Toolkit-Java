package datastructures.linear;

import datastructures.interfaces.Queue;

/** Implements the Queue interface using an array
 * @param <E> data type of the structure
 */
public class CircularArrayQueue<E> implements Queue<E> {
    // Class Attributes   
    protected static final int DEFAULT_CAPACITY = 50;
    // Instance Attributes
    protected E[] array;
    protected int rear, front, size;
  
    // Constructor
    @SuppressWarnings("unchecked")
    public CircularArrayQueue() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0; front = 0; rear = -1;
    }
    
    /* inserts x at the end of the Queue */
    public void enqueue(E x) {
        if (size == array.length) { resize(); }
        rear = increment(rear);
        array[rear] = x; 
        size++;
    }
    
    /* PRECONDITION: !isEmpty(): removes the first data of the Queue */
    public E dequeue() {
        E firstElement = array[front];
        front = increment(front); 
        size--;
        return firstElement;
    }
    
    /* PRECONDITION: !isEmpty(): returns the first data of the Queue */
    public E front() {
        return array[front];
    }

    /* checks if the Queue is empty or not */
    public boolean isEmpty() {
        return (size == 0);
    }
  
    /* increments the position in a circular way */
    private int increment(int index) {
        return (index + 1) % array.length;
    }
    
    /** 
     * doubles the capacity of the array
     */ 
    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newArray = (E[]) new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[front];
            front = increment(front);
        }
        array = newArray;
        front = 0;
        rear = size - 1;
    }
}
