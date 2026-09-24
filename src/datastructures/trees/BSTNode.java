package datastructures.trees;

/** Class friendly BSTNode<E>: represents a Node of a BST, so it
 *  HAS A: 
 *  1.- E data, which represents the Element that occupies a Node of a BST 
 *  2.- BSTNode<E> left, a link to the Left Child of a Node of a BST
 *  3.- BSTNode<E> right, a link to the Right Child of a Node of a BST
 *  4.- size, an integer that represents the size of a Node of a BST
 *      A BST that has Nodes with this attribute is called a BST with Rank
 *      
 *  @param <E> the type of the data of the BST
 */
class BSTNode<E> {
     // attributes
    protected E data;
    protected int size;
    protected BSTNode<E> left, right;
     
    /** Constructor of a node without children
      * @param e   Data to store in the node
      */
    public BSTNode(E e) { this(e, null, null); }
     
    /**  Constructor of a node with a given left and right child
      *  @param  e     Data to store in the node
      *  @param  left  Left child of the node
      *  @param  right Right child of the node
      */
    public BSTNode(E e, BSTNode<E> left, BSTNode<E> right) {
        data = e; 
        this.left = left;
        this.right = right;
        size = 1;
        if (left != null) size += left.size;
        if (right != null) size += right.size;
    }
}
