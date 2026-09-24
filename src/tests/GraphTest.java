package tests;

import datastructures.graphs.Edge;
import datastructures.graphs.UndirectedGraph;
import datastructures.linear.CircularArrayQueue;

public class GraphTest {
    private static final int NUMV = 7;

    public GraphTest() {
    }

    public static void main(String[] args) {
        if (validate("Validating Edge class", testEdge())) {
            if (validate("Validating Spanning Tree for a DISCONNECTED Graph", testSTDisconnected())) {
                if (validate("Validating Spanning Tree for a CONNECTED Graph", testSTConnected())) {
                    ;
                }
            }
        }
    }

    public static boolean validate(String test, String res) {
        System.out.print(test + "... ");
        if (res == null) {
            System.out.println("OK!\n");
            return true;
        } else {
            System.out.println("ERROR");
            System.out.println("* " + res);
            return false;
        }
    }

    public static String testEdge() {
        Edge test = new Edge(0, 2, 12.5);
        if (test.getSource() == 0 && test.getDestination() == 2 && test.getWeight() == 12.5) {
            String sTest = test.toString();
            String sTestOK = "(0, 2, 12.5)";
            if (!sTest.equals(sTestOK)) {
                return "BECAUSE the toString() method of your Edge class is NOT correct...\n  Check first that it overrides Object's, and then that the\n  format of the resulting String is as specified!!";
            } else {
                double weightSum = 0.0;
                CircularArrayQueue<Edge> arrayQueue = new CircularArrayQueue<>();
                Edge[] data = new Edge[]{new Edge(0, 2, 12.5), new Edge(1, 3, 6.5), new Edge(2, 3, 4.3), new Edge(0, 3, 14.3), new Edge(0, 1, 6.2)};

                for(int i = 0; i < data.length; ++i) {
                    arrayQueue.enqueue(data[i]);
                }

                int[] order = new int[]{0, 1, 2, 3, 4};
                int j = 0;

                while(j < data.length) {
                    Edge a = arrayQueue.dequeue();
                    weightSum += a.getWeight();
                    Edge s = data[order[j]];
                    if (a.getSource() != a.getDestination() && s.getSource() != s.getDestination()) {
                        if (a.getSource() == s.getSource() && a.getDestination() == s.getDestination() && a.getWeight() == s.getWeight()) {
                            ++j;
                            continue;
                        }

                        return "BECAUSE the components of your edges are not correct...\n  Check the getter methods of your Edge class!!";
                    }

                    return "BECAUSE the source and destination vertices of an edge CANNOT match...\n  Check the getSource and getDestination methods of your Edge class!!";
                }

                if (Math.abs(weightSum - 43.8) >= 1.0E-8) {
                    return "BECAUSE the edge weights are not correct...\n  Check the getter methods of your Edge class!!";
                } else {
                    return null;
                }
            }
        } else {
            return "BECAUSE the constructor method of your Edge class is NOT correct...\n  Check that the attribute declaration of the class and\n  its initialization in the constructor are as specified!!";
        }
    }

    public static String testSTDisconnected() {
        UndirectedGraph g = new UndirectedGraph(7);
        g.addEdge(2, 3, 4.0);
        g.addEdge(4, 5, 4.0);
        g.addEdge(0, 1, 6.0);
        g.addEdge(1, 3, 6.0);
        g.addEdge(0, 2, 12.0);
        g.addEdge(0, 3, 14.0);
        g.addEdge(5, 6, 15.0);
        g.addEdge(4, 6, 20.0);
        return g.spanningTreeBFS() == null ? null : "Your method DOES NOT return null for a Graph such as, for example,\n" + g.toString() + "\n";
    }

    private static boolean edgeInList(Edge a, Edge[] sol) {
        for(int i = 0; i < sol.length; ++i) {
            Edge s = sol[i];
            if (a.getSource() == s.getSource() && a.getDestination() == s.getDestination() && a.getWeight() == s.getWeight()) {
                return true;
            }
        }

        return false;
    }

    private static String testSTConnected() {
        UndirectedGraph g = new UndirectedGraph(7);
        g.addEdge(2, 3, 4.0);
        g.addEdge(4, 5, 4.0);
        g.addEdge(0, 1, 6.0);
        g.addEdge(1, 3, 6.0);
        g.addEdge(3, 4, 9.0);
        g.addEdge(0, 2, 12.0);
        g.addEdge(2, 4, 12.0);
        g.addEdge(3, 5, 12.0);
        g.addEdge(0, 3, 14.0);
        g.addEdge(5, 6, 15.0);
        g.addEdge(1, 5, 20.0);
        g.addEdge(4, 6, 20.0);
        String trace = "Your method DOES NOT get the correct ST for a Graph such as, for example,\n" + g.toString() + "\n";
        Edge[] sol = new Edge[]{new Edge(0, 1, 6.0), new Edge(0, 2, 12.0), new Edge(0, 3, 14.0), new Edge(1, 5, 20.0), new Edge(2, 4, 12.0), new Edge(5, 6, 15.0)};
        Edge[] sT = g.spanningTreeBFS();
        boolean okNumA = sT != null && sT.length == 6;
        if (okNumA) {
            String comparison = " Incorrect Edges:\n";
            int correctEdgesCount = 0;

            for(int i = 0; i < sT.length; ++i) {
                Edge a = sT[i];
                if (edgeInList(a, sol)) {
                    ++correctEdgesCount;
                } else {
                    comparison = comparison + "\t   " + a.toString() + "\n";
                }
            }

            if (correctEdgesCount == 6) {
                return null;
            } else {
                return trace + "BECAUSE some of its Edges are NOT correct:\n" + comparison + "\n";
            }
        } else {
            return sT == null ? "BECAUSE you return a NULL edge set!!" : "BECAUSE you return a set of " + sT.length + " edges(!!), and it should have |V| - 1 = 6";
        }
    }
}
