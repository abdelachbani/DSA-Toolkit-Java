package datastructures.trees;

import datastructures.interfaces.Queue;
import datastructures.linear.CircularArrayQueue;
import algorithms.Sorting;

/** BinarySearchTree<E> class that represents a Binary Search Tree
 *  using a link to its root. Its characteristics are the following: 
 *  1.- The type of its elements is E extends Comparable<E>
 *  2.- ATTRIBUTES (protected for inheritance): 
 *      HAS A BSTNode<E> root
 **/
public class BinarySearchTree<E extends Comparable<E>> {
    // Attribute of the BinarySearchTree class
    protected BSTNode<E> root;

    /** Constructor of an empty BST 
     */
    public BinarySearchTree() { root = null; }
    
    /** Constructor of a BST with the elements of the given array.
     *  The resulting BST must be balanced.
     *  @param v    Array with the elements to insert in the BST
     */
    public BinarySearchTree(E[] v) {        
        this.root = buildBalanced(v, 0, v.length - 1);
    }
    
    /** Builds a balanced BST with the elements of the given array.
     *  @param v     Array with the elements to insert in the BST
     *  @param ini   Start of the interval to consider in the array
     *  @param fin   End of the interval to consider in the array
     *  @return      Root of the subtree
     */
    protected BSTNode<E> buildBalanced(E[] v, int ini, int fin) {
        if(ini > fin) { return null; }
        int mid = (ini + fin) / 2;
        BSTNode<E> left = buildBalanced(v, ini, mid - 1);
        BSTNode<E> right = buildBalanced(v, mid + 1, fin);
        return new BSTNode<>(v[mid], left, right);
    }
    
    /** Rebuilds the BST, with the same data, so that it becomes balanced        
     */
    public void rebalance() {        
        E[] v = toArrayInOrder();
        this.root = buildBalanced(v, 0, v.length - 1);
    }
    
    /** Returns the successor of an element in the BST
     *  @param e   Element whose successor is to be searched
     *  @return    Successor of "e", or null if there is no successor
     */
    public E successor(E e) {
        BSTNode<E> res = successor(e, root);
        if (res == null) return null;  
        return res.data; 
    }
    
    /** PRECONDITION current != null: returns the node from current that contains 
     *  the successor of "e", or null if it does not exist
     *  @param e         Element whose successor is to be searched
     *  @param current   Current node in the search
     *  @return          Successor of "e" in the current node, or null if it doesn't exist
     */
    protected BSTNode<E> successor(E e, BSTNode<E> current) {
        BSTNode<E> res = null;
        if (current != null) {
            int resC = current.data.compareTo(e);
            if (resC > 0) {
                res = successor(e, current.left);
                if (res == null) res = current;
            } else {
                res = successor(e, current.right);
            }
        }
        return res;
    }
    
    /** Searches for the given value in the BST
     *  @param e   Element to search
     *  @return    Data in the BST that matches e, null if there isn't          
     */
    public E get(E e) {
        BSTNode<E> res = get(e, root);
        if (res == null) return null;
        return res.data;
    }
    
    /** Searches for the given value starting from the current node
     *  @param e        Element to search
     *  @param current  Current node in the search
     *  @return         Node in the BST that contains e, null if there isn't              
     */
    protected BSTNode<E> get(E e, BSTNode<E> current) {
        if (current == null) return null;
        int cmp = e.compareTo(current.data);
        if (cmp < 0) return get(e, current.left); 
        if (cmp > 0) return get(e, current.right);
        return current;
    }
    
    /** Updates the data e in the BST, if it's not present, inserts it 
     *  @param e   Element to insert/update
     */
    public void insert(E e) { root = insert(e, root); }
    
    /** Updates the data e starting from the current node. If not present, inserts it 
     *  @param e        Element to insert/update
     *  @param current  Current node in the search
     *  @return         Root node of the subtree whose current root is the current node 
     */
    protected BSTNode<E> insert(E e, BSTNode<E> current) {
        if (current == null) return new BSTNode<E>(e); 
        int cmp = e.compareTo(current.data);
        if (cmp < 0)      current.left = insert(e, current.left); 
        else if (cmp > 0) current.right = insert(e, current.right); 
        else              current.data = e; 
        current.size = 1 + size(current.left) + size(current.right);
        return current;
    }
    
    /** Returns the number of elements in the BST
     *  @return   Size of the BST
     */
    public int size() { return size(root); }

    /** Returns the size of the current node
     *  @param current   Current node
     *  @return          Size of the current node
     */
    protected int size(BSTNode<E> current) {
        if (current == null) return 0; 
        return current.size;
    }
  
    /** PRECONDITION: !isEmpty(): returns the minimum element of the BST
      * @return   Minimum element    
      */
    public E getMin() { return getMin(root).data; }
    
    /** Returns the minimum element starting from the current node 
     *  @param current   Current node in the search
     *  @return          Node that contains the minimum element 
     */
    protected BSTNode<E> getMin(BSTNode<E> current) {
        if (current.left == null) return current; 
        return getMin(current.left); 
    }

    /** PRECONDITION: !isEmpty(): removes the minimum from the BST
     *  @return   Minimum element of the BST (null if it's empty)
     */
    public E removeMin() {
        E min = getMin();
        root = removeMin(root);
        return min;
    }
 
    /** Removes the minimum starting from the current node
     *  @param current   Current node in the search
     *  @return          Root node of the subtree whose current root is the current node
     */
    protected BSTNode<E> removeMin(BSTNode<E> current) {
        if (current.left == null) return current.right;
        current.left = removeMin(current.left);
        current.size--;
        return current;
    }
  
    /** Removes the node that contains the data e 
     *  @param e   Data to remove
     */
    public void remove(E e) { root = remove(e, root); }
    
    /** Removes the node that contains the data e starting from the current node 
     *  @param e         Data to remove
     *  @param current   Current node in the search
     *  @return          Root node of the subtree whose current root is the current node
     */
    protected BSTNode<E> remove(E e, BSTNode<E> current) {
        if (current == null) return current; 
        int cmp = e.compareTo(current.data);
        if (cmp < 0)      current.left = remove(e, current.left); 
        else if (cmp > 0) current.right = remove(e, current.right); 
        else {
            if (current.right == null)  return current.left; 
            if (current.left  == null) return current.right; 
            current.data = getMin(current.right).data;
            current.right = removeMin(current.right);
        }
        current.size = 1 + size(current.left) + size(current.right);
        return current;
    }

    /** Returns true if the BST is empty
     *  @return   true if it's empty, false otherwise
     */
    public boolean isEmpty() { return root == null; }
  
    /** InOrder traversal of the BST
     *  @return   String with the elements according to the InOrder traversal
     */
    public String toStringInOrder() {
        StringBuilder sb = new StringBuilder().append("[");
        if (root != null) toStringInOrder(sb, root);
        return sb.append("]").toString();
    }
    
    /** InOrder traversal starting from the current node
     *  @param sb        StringBuilder to build the text string 
     *  @param current   Current node in the traversal
     */
    protected void toStringInOrder(StringBuilder sb, BSTNode<E> current) {
        if (current.left != null) {
            toStringInOrder(sb, current.left);
            sb.append(",");
        }
        sb.append(current.data.toString());
        if (current.right != null) { 
            sb.append(",");
            toStringInOrder(sb, current.right); 
        }
    }

    /** PreOrder traversal of the BST
     *  @return   String with the elements according to the PreOrder traversal
     */
    public String toStringPreOrder() {
        StringBuilder sb = new StringBuilder().append("[");
        if (root != null) toStringPreOrder(sb, root);
        return sb.append("]").toString();
    }
    
    /** PreOrder traversal starting from the current node
     *  @param sb        StringBuilder to build the text string 
     *  @param current   Current node in the traversal
     */
    protected void toStringPreOrder(StringBuilder sb, BSTNode<E> current) {
        sb.append(current.data.toString());
        if (current.left != null) {
            sb.append(",");
            toStringPreOrder(sb, current.left);
        }
        if (current.right != null) {
            sb.append(",");
            toStringPreOrder(sb, current.right);
        }
    }

    /** PostOrder traversal of the BST
     *  @return   String with the elements according to the PostOrder traversal
     */
    public String toStringPostOrder() {
        StringBuilder sb = new StringBuilder().append("[");
        if (root != null) toStringPostOrder(sb, root);
        return sb.append("]").toString();
    }
    
    /** PostOrder traversal starting from the current node
     *  @param sb        StringBuilder to build the text string 
     *  @param current   Current node in the traversal
     */
    protected void toStringPostOrder(StringBuilder sb, BSTNode<E> current) {
        if (current.left != null) {
            toStringPostOrder(sb, current.left);
            sb.append(",");
        }
        if (current.right != null) {
            toStringPostOrder(sb, current.right);
            sb.append(",");
        }
        sb.append(current.data.toString());
    }
    
    /** Level-order traversal of the BST
     *  @return   String with the elements according to the level-order traversal
     */
    public String toStringByLevel() {
        if (root == null) return "[]";
        StringBuilder res = new StringBuilder().append("[");
        Queue<BSTNode<E>> q = new CircularArrayQueue<BSTNode<E>>();
        q.enqueue(root);
        while (!q.isEmpty()) {
            BSTNode<E> current = q.dequeue();
            res.append(current.data.toString());
            res.append(", ");
            if (current.left != null) q.enqueue(current.left);
            if (current.right != null) q.enqueue(current.right); 
        }
        // For efficiency, to delete the last ", "
        // from res, we subtract 2 from its current length
        res.setLength(res.length() - 2);
        return res.append("]").toString();
    }
    
    /** Builds an array sorted in ascending order with all the
     *  elements of the BST, result of its InOrder traversal
     *  @return   Array with the values of the BST according to the InOrder traversal
     */
    @SuppressWarnings("unchecked")
    public E[] toArrayInOrder() {
        E[] v = (E[]) new Comparable[size()];
        toArrayInOrder(v, root, 0);
        return v;
    }
    
    /** Builds an array sorted in ascending order with all the
     *  elements starting from the current node, following the InOrder traversal
     *  @param v         Array with the elements according to the InOrder traversal
     *  @param current   Current node in the traversal
     *  @param pos       Position in the array v
     */ 
    protected void toArrayInOrder(E[] v, BSTNode<E> current, int pos) {
        if (current != null) {
            toArrayInOrder(v, current.left, pos);
            int auxPos = pos + size(current.left);
            v[auxPos++] = current.data;
            toArrayInOrder(v, current.right, auxPos);    
        }
    }
}
