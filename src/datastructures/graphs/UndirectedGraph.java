package datastructures.graphs;

/** UndirectedGraph Class: implementation of an Undirected graph
 *  (Weighted or not) using Adjacency Lists:<br>
 *  an Undirected graph IS A Directed Graph such that if the Edge (i, j)  
 *  is present in the Adjacency List of i then the Edge (j, i) is also
 *  in the list of j
 */
public class UndirectedGraph extends DirectedGraph {
    
    /** Creates an empty Undirected graph with numV vertices. 
      * 
      * @param numV  Number of vertices
     */
    public UndirectedGraph(int numV) { 
        super(numV);
        isDirected = false;
    }
    
    /** If it's not present, inserts the edge (i, j) in an Undirected  
      * and Unweighted graph; therefore, it also inserts the edge (j, i).
      * 
      *  @param i   Source vertex
      *  @param j   Target vertex
     */ 
    public void addEdge(int i, int j) {
        if (!hasEdge(i, j)) {
            adjacencyList[i].insert(new AdjacentVertex(j, 1)); 
            adjacencyList[j].insert(new AdjacentVertex(i, 1));
            edgeCount++; 
        }
    }
    
    /** If it's not present, inserts the edge (i, j) of weight p in an  
      * Undirected and Weighted graph; therefore, it also inserts the edge 
      *  (j, i) of weight p.
      *  
      * @param i   Source vertex
      * @param j   Target vertex
      * @param p   Weight of (i, j)
     */ 
    public void addEdge(int i, int j, double p) {
        if (!hasEdge(i, j)) {
            adjacencyList[i].insert(new AdjacentVertex(j, p)); 
            adjacencyList[j].insert(new AdjacentVertex(i, p));
            edgeCount++; 
        }
    }
}
