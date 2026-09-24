package datastructures.linear;

import datastructures.interfaces.CursorList;

/** Implements the CursorList interface using a Linked List ...
 *  (a) With dummy header node.
 *  (b) A reference to the first Node.
 *  (c) A reference to the last Node.
 *  (d) To represent the Cursor, a reference to the Node 
 *       before the one occupied by the cursor.
 *  (e) An int size that represents the size of the List.
 *
 * @param <E> data type of the structure
 */
public class LinkedCursorList<E> implements CursorList<E> {

    protected ListNode<E> header, predecessor, last;
    protected int size;

    /** builds an empty Cursor List **/
    public LinkedCursorList() {
        header = last = predecessor = new ListNode<E>(null);
        size = 0;
    }
    
    /** inserts e into a List before the Element occupied by its Cursor, 
     * which remains unchanged.
     *
     * @param e Element to insert.
     **/
    public void insert(E e) { 
        ListNode<E> newNode = new ListNode<E>(e, predecessor.next);
        predecessor.next = newNode;
        if (newNode.next == null) last = newNode;
        predecessor = predecessor.next;
        size++;
    } 

    /** PRECONDITION: !isEnd(): removes from a List the Element occupied 
     * by its Cursor which remains unchanged. 
     **/  
    public void remove() {
        size--;
        if (predecessor.next == last) last = predecessor;
        predecessor.next = predecessor.next.next;
    }

    /** places the Cursor of a List at its beginning, over its first 
     * element if it's not empty.
     **/
    public void begin() { predecessor = header; }

    /** PRECONDITION: !isEnd(): places the Cursor of a List over 
     * the next Element.  
     **/
    public void next() { predecessor = predecessor.next; }

    /** places the Cursor of a List at its end, after its last 
     * Element if it's not empty. 
     **/
    public void end() { predecessor = last; }

    /** PRECONDITION: !isEnd(): gets the Element occupied by the Cursor of 
     * a List.
     *
     * @return E, the Element occupied by the Cursor of a List.
     */
    public E get() { 
        return predecessor.next.data;
    }

    /** checks if the Cursor of a List is at its end.
     *
     * @return true if the Cursor of a List is at its end and 
     * false otherwise
     **/
    public boolean isEnd() { 
        return predecessor == last;
    }

    /** checks if a Cursor List is empty
     *
     * @return true if a Cursor List is empty and 
     * false otherwise
     **/
    public boolean isEmpty() { 
        return header == last;
    }

    /** returns the size of a Cursor List, i.e. its 
     * number of elements.
     *
     * @return int, the number of Elements of a Cursor List.
     **/
    public int size() { return size; }

    /** returns the String with the Elements of a Cursor List 
     * in order of insertion.
     * 
     * @return String that contains the Elements of a Cursor 
     * List, in the same format used by the Java standard for 
     * arrays.
     **/
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        ListNode<E> aux = header.next;         
        for (int i = 1; i < size; i++, aux = aux.next) {            
            s.append(aux.data.toString() + ", ");
        }
        if (size != 0) {
            s.append(aux.data.toString() + "]"); 
        } else { s.append("]"); }
        return s.toString();
    }
}
