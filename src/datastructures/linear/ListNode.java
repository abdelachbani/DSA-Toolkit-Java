package datastructures.linear;

class ListNode<E> {
    E data;
    ListNode<E> next;
   
    ListNode(E e, ListNode<E> n) {
        data = e; next = n;
    }

    ListNode(E data) { this(data, null); } 
}
