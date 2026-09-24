package datastructures.graphs;

/** Edge Class: represents an edge of a graph.
 */
public class Edge implements Comparable<Edge> {
    
    // AN Edge HAS A source vertex and A target vertex:
    private int source;
    private int target;
	
    // AN Edge HAS A weight:
    private double weight;
    
    /** Creates an edge (v, w) with weight p.
      *
      * @param v  Source vertex
      * @param w  Target vertex
      * @param p  Weight
     */
    public Edge(int v, int w, double p) {
        source = v;
        target = w;
        weight = p;
    }

    /** Returns the source vertex of an edge.
      *
      * @return int source vertex
     */
    public int getSource() {
        return source;
    }
    
    /** Returns the target vertex of an edge.
      *
      * @return int target vertex
     */
    public int getTarget() {  
        return target;
    }
    
    /** Returns the weight of an edge.
      *
      * @return double Weight of the edge
     */
    public double getWeight() {
        return weight;
    }
    
    /** Returns a String that represents an edge
      * in (source, target, weight) format
      *
      * @return  String that represents the edge
     */
    public String toString() {
        return "(" + source + ", " + target + ", " + weight + ")";
    }

    public int compareTo(Edge a) {
        if (this.weight < a.getWeight()) {
            return -1;
        } else if (this.weight > a.getWeight()) {
            return 1;
        } else {
            return 0;
        }
    }
}
