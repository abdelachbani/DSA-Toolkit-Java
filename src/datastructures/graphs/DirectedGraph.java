package datastructures.graphs;

import datastructures.interfaces.CursorList; 
import datastructures.linear.LinkedCursorList;

/** Implementation of a Directed graph (Weighted or not)  
 *  using Adjacency Lists.
 */
public class DirectedGraph extends Graph { 
    
    protected int vertexCount, edgeCount;
    protected CursorList<AdjacentVertex>[] adjacencyList;
    
    /** Creates an empty Directed graph with nV vertices. 
     *  @param nV  Number of vertices of the graph
     */
    @SuppressWarnings("unchecked")
    public DirectedGraph(int nV) {
        super(true);
        vertexCount = nV; edgeCount = 0;
        adjacencyList = new CursorList[vertexCount]; 
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList[i] = new LinkedCursorList<AdjacentVertex>();
        }
    }
    
    /** Returns the number of vertices of a graph. 
      * @return  int Number of vertices
     */
    public int numVertices() { return vertexCount; }

    /** Returns the number of edges of a graph
      * @return     Number of edges 
     */
    public int numEdges() { return edgeCount; }
    
    /** Checks if the edge (i,j) is in a graph.
      * @param i   Source vertex
      * @param j   Target vertex
      * @return    boolean true if (i,j) is in and false otherwise
     */    
    public boolean hasEdge(int i, int j) {
        CursorList<AdjacentVertex> l = adjacencyList[i]; 
        boolean found = false;
        for (l.begin(); !l.isEnd() && !found; l.next()) {
            if (l.get().target == j) { found = true; }
        }
        return found;   
    }
    
    /** Returns the weight of the edge (i,j) of a graph, 0 if such edge 
      * is not in the graph.
      * @return   double Weight of the edge (i,j), 0 if it doesn't exist
     */
    public double edgeWeight(int i, int j) {
        CursorList<AdjacentVertex> l = adjacencyList[i];
        for (l.begin(); !l.isEnd(); l.next()) {
            if (l.get().target == j) {
                return l.get().weight;
            }
        }
        return 0.0;
    }
    
    /** If it's not present, inserts the edge (i, j) in an Unweighted graph 
      * (at the end of the List of adjacents to i).
      * @param i   Source vertex
      * @param j   Target vertex
     */       
    public void addEdge(int i, int j) {
        if (!hasEdge(i, j)) {
            adjacencyList[i].insert(new AdjacentVertex(j, 1)); 
            edgeCount++; 
        }
    }

    /** If it's not present, inserts the edge (i, j) with weight p in a Weighted graph 
      * (at the end of the List of adjacents to i).
      * @param i   Source vertex
      * @param j   Target vertex
      * @param p   Weight of (i, j)
     */  
    public void addEdge(int i, int j, double p) {
        if (!hasEdge(i, j)) {
            adjacencyList[i].insert(new AdjacentVertex(j, p)); 
            edgeCount++; 
        }
    }
        
    /** Returns a CursorList that contains the adjacents to vertex i.
      * @param i   Vertex from which adjacents are obtained
      * @return    CursorList with the vertices adjacent to i
     */ 
    public CursorList<AdjacentVertex> adjacentsOf(int i) {
        return adjacencyList[i];
    }
}
