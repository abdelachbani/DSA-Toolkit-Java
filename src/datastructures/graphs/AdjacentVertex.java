package datastructures.graphs;

/** AdjacentVertex: represents an adjacent vertex to another in a graph and the weight 
 *  of the edge that connects them.
 */
public class AdjacentVertex { 
    
    protected int target;
    protected double weight;
    
    /** Creates the vertex target adjacent to another in a graph and the weight 
      * of the edge that connects them.
      * 
      * @param  v Adjacent vertex to another
      * @param  w Weight of the edge that connects v with 
      *           the vertex to which it is adjacent 
     */
    public AdjacentVertex(int v, double w) { 
        target = v;  
        weight = w; 
    }
    
    /** Returns the adjacent vertex to another in a graph,
      * or target vertex of the edge that connects them.
      * 
      * @return int  Adjacent vertex to another 
     */
    public int getTarget() { return target; }

    /** Returns the weight of the edge that connects a vertex 
      * of a graph with an adjacent one.
      * 
      * @return double  Weight of the edge that connects an adjacent  
      *                 of a vertex of a graph, 1 if the graph 
      *                 is Unweighted
     */
    public double getWeight() { return weight; }
     
    /** Returns a String that represents an adjacent to a 
      * vertex of a graph and the weight of the edge that connects them.
      * 
      * @return  String  that represents an adjacent
     */     
    public String toString() { return target + "(" + weight + ") "; }
}
