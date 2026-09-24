package datastructures.interfaces;

/** Represents the classes -disjoint subsets- that define an equivalence relation
 *  in a set of cardinality n 
 *  The elements of the set are represented by integers in  
 *  the interval [0, n-1], as their concrete type does NOT intervene in 
 *  the definition of the classes
 *   
 *  The elements of a class are represented by ONLY one of its 
 *  members, any of them, as all are equivalent. This 
 *  element is called the class identifier
 *   
 *  The classes are defined dynamically starting from the trivial n, 
 *  each formed by one element of the set
 *      
 *  In addition to Union-Find Set, other names this Model receives are 
 *  Partition Structure (Disjoint-Set) and Merge-Find Set
 */
public interface UnionFind {
    
    /** Returns the identifier of the equivalence class  
     *  -subset- to which i belongs, after performing a 
     *  path compression.
     */
    int find(int i);
    
    /** PRECONDITION: classI != classJ AND classI = find(i) && classJ = find(j)
     *  Unites the equivalence classes -subsets- with 
     *  identifiers classI and classJ by union 
     *  by rank.
     */
    void union(int classI, int classJ);
}
