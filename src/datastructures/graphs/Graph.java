package datastructures.graphs;

import datastructures.trees.DisjointSetForest;
import datastructures.trees.BinaryHeap;
import datastructures.interfaces.Queue;
import datastructures.interfaces.PriorityQueue;
import datastructures.interfaces.CursorList;
import datastructures.linear.LinkedCursorList;
import datastructures.linear.CircularArrayQueue;
import datastructures.interfaces.UnionFind;

/** Abstract Graph Class: Base of the Graph hierarchy, which defines the 
 *  behavior of a graph.
 *
 *  It is not an interface because it includes the code of the operations of a 
 *  graph that are independent of both its type and its implementation.
 */
public abstract class Graph {

    protected boolean isDirected; // Indicates if a graph is Directed or not
    protected int[] visited;    // Visited nodes in a Traversal
    protected int visitOrder;    // Visit order of nodes in a Traversal
    protected Queue<Integer> q;    // Queue for a BFS Traversal
    
    /** Creates an empty graph, Directed if directed is true
      * or Undirected otherwise.
      * 
      * @param directed Indicates the type of the graph, Directed or Not
     */
    public Graph(boolean directed) { isDirected = directed; }
    
    /** Checks if a graph is Directed or not.
      *
      * @return boolean true if the graph is Directed and false if it is Undirected
     */
    public boolean isDirected() { return isDirected; }
    
    /** Returns the number of vertices of a graph.
      * 
      * @return int number of vertices
      */
    public abstract int numVertices();
    
    /** Returns the number of edges of a graph.
      * 
      * @return int number of edges
     */
    public abstract int numEdges();
    
    /** Checks if the edge (i,j) is in a graph.
      * 
      * @param i    Source vertex
      * @param j    Target vertex
      * @return boolean true if (i,j) is in the graph and false otherwise
     */
    public abstract boolean edgeExists(int i, int j);
    
    /** Returns the weight of the edge (i,j) of a graph, 0 if such edge 
      * is not in the graph.
      * 
      * @param i    Source vertex
      * @param j    Target vertex
      * @return double Weight of the edge (i,j), 0 if it does not exist.
      */
    public abstract double edgeWeight(int i, int j);
    
    /** If it's not present, inserts the edge (i,j) in an Unweighted graph.
      * 
      * @param i    Source vertex
      * @param j    Target vertex
     */
    public abstract void insertEdge(int i, int j);
    
    /** If it's not present, inserts the edge (i, j) with weight p in a Weighted graph.
      * 
      * @param i    Source vertex
      * @param j    Target vertex
      * @param p    Weight of the edge (i, j)
     */
    public abstract void insertEdge(int i, int j, double p);

    /** Returns a CursorList that contains the adjacents to vertex i.
      * 
      * @param i Vertex from which adjacents are obtained
      * @return CursorList with the vertices adjacent to i
     */
    public abstract CursorList<AdjacentVertex> adjacentsOf(int i);
    
    /** Returns a String with each of the vertices of a graph and their 
      * adjacents, in order of insertion.
      * 
      * @return  String that represents a graph
     */               
    public String toString() {
        String res = "";  
        for (int  i = 0; i < numVertices(); i++) {
            res += "Vertex: " + i;
            CursorList<AdjacentVertex> l = adjacentsOf(i);
            if (l.isEmpty()) res += " without Adjacents "; 
            else             res += " with Adjacents "; 
            for (l.begin(); !l.isEnd(); l.next()) {
                res +=  l.get() + " ";  
            }
            res += "\n";  
        }
        return res;      
    }
    
    /** Returns an array with each of the vertices of a graph and their 
      * adjacents, in BFS order.
      * 
      * @return  Array of visited vertices in the BFS traversal
     */   
    public int[] bfsTraversal() {
        int[] res = new int[numVertices()];
        visited = new int[numVertices()]; 
        visitOrder = 0;  
        q = new CircularArrayQueue<Integer>();
        for (int  i = 0; i < numVertices(); i++) {
            if (visited[i] == 0) bfsTraversal(i, res);
        }
        return res;
    }
    // BFS Traversal of the source vertex, which stores in res
    // its result
    protected void bfsTraversal(int source, int[] res) { 
        res[visitOrder++] = source;
        visited[source] = 1;
        q.enqueue(source);
        while (!q.isEmpty()) {
            int u = q.dequeue(); 
            CursorList<AdjacentVertex> l = adjacentsOf(u); 
            for (l.begin(); !l.isEnd(); l.next()) {
                AdjacentVertex a = l.get(); 
                if (visited[a.target] == 0) {
                    res[visitOrder++] = a.target;
                    visited[a.target] = 1;
                    q.enqueue(a.target);
                }
            }  
        }
    }
    
    /** PRECONDITION: !this.isDirected()
      * Returns a subset of edges that connect all the vertices
      * of an Undirected and Connected graph, or null if the graph is not Connected.
      *  
      * @return Edge[], array with the numV - 1 edges that connect  
      *                   the numV vertices of the graph, or null if the graph 
      *                   is not Connected
     */ 
    public Edge[] bfsSpanningTree() {
        Edge[] res = new Edge[numVertices() - 1];
        visited = new int[numVertices()];
        q = new CircularArrayQueue<Integer>();
        visitOrder = 0;
        visited[0] = 1;
        q.enqueue(0);
        while (!q.isEmpty()) {
            int u = q.dequeue();
            CursorList<AdjacentVertex> l = adjacentsOf(u);
            for (l.begin(); !l.isEnd(); l.next()) {
                AdjacentVertex a = l.get();
                if (visited[a.target] == 0) {
                    res[visitOrder++] = new Edge(u, a.target, a.weight);
                    visited[a.target] = 1;
                    q.enqueue(a.target);
                }
            }
        }
        for (int i = 0; i < numVertices(); i++) {
            if (visited[i] == 0) {
                return null;
            }
        }
        return res;
    }
    
    /** PRECONDITION: !this.isDirected()
      * Returns a subset of edges that, with minimum cost,  
      * connect all the vertices of an Undirected and Connected graph, 
      * or null if the graph is not Connected.
      * 
      * @return Edge[], array with the numV - 1 edges that connect 
      *                   the numV vertices with minimum cost, or null 
      *                   if the graph is not Connected
     */ 
    public Edge[] kruskal() {       
        Edge[] res = new Edge[numVertices() - 1];
        PriorityQueue<Edge> pq = new BinaryHeap<>();
        for (int i = 0; i < numVertices(); i++) {
            CursorList<AdjacentVertex> l = adjacentsOf(i);
            for (l.begin(); !l.isEnd(); l.next()) {
                AdjacentVertex a = l.get();
                if (i < a.target) {
                    pq.insert(new Edge(i, a.target, a.weight));
                }
            }
        }
        int edgesCount = 0;
        UnionFind uf = new DisjointSetForest(numVertices());
        while (!pq.isEmpty() && edgesCount < numVertices() - 1) {
            Edge a = pq.removeMin();
            int origin = uf.find(a.getSource());
            int dest = uf.find(a.getTarget());
            if (origin != dest) {
                res[edgesCount++] = a;
                uf.union(origin, dest);
            }
        }
        if (edgesCount < numVertices() - 1) {
            return null;
        }
        return res;
    }
}
