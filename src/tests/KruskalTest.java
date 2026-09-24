package tests;

import java.util.Arrays;
import datastructures.trees.DisjointSetForest;
import datastructures.trees.BinaryHeap;
import datastructures.interfaces.PriorityQueue;
import datastructures.interfaces.UnionFind;
import datastructures.graphs.Graph;
import datastructures.graphs.UndirectedGraph;
import datastructures.graphs.Edge;

public class KruskalTest {
    private static final int NUMV = 7;

    public KruskalTest() {
    }

    public static void main(String[] args) {
        if (validate("Validating the Edge class for Kruskal", testEdge())) {
            if (validate("Validating Kruskal for a DISCONNECTED Graph", testKruskalDisconnected())) {
                if (validate("Validating Kruskal for a CONNECTED Graph", testKruskal())) {
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
        double sumWeights = 0.0;
        PriorityQueue<Edge> pq = new BinaryHeap<>();
        Edge[] data = new Edge[]{new Edge(0, 2, 12.5), new Edge(1, 3, 6.5), new Edge(2, 3, 4.3), new Edge(0, 3, 14.3), new Edge(0, 1, 6.2)};
        String a1 = data[1].toString();
        String a1OK = "(1, 3, 6.5)";
        if (!a1.equals(a1OK)) {
            return "BECAUSE the toString() method of your Edge class is NOT correct...\n  Check first that it overrides Object's and, then, that the\n  format of the resulting String is the specified one!!";
        } else {
            for(int i = 0; i < data.length; ++i) {
                pq.insert(data[i]);
            }

            int[] order = new int[]{2, 4, 1, 0, 3};
            int i = 0;

            while(i < data.length) {
                Edge a = pq.removeMin();
                int aSource = a.getSource();
                int aDestination = a.getDestination();
                double aWeight = a.getWeight();
                sumWeights += aWeight;
                Edge s = data[order[i]];
                int sSource = s.getSource();
                int sDestination = s.getDestination();
                double sWeight = s.getWeight();
                if (aSource != aDestination && sSource != sDestination) {
                    if (aSource == sSource && aDestination == sDestination && aWeight == sWeight) {
                        ++i;
                        continue;
                    }

                    return "BECAUSE the edges are not compared properly...\n  Check the compareTo method of your Edge class!!";
                }

                return "BECAUSE the source and destination vertices of an edge CANNOT match...\n  Check the getSource and getDestination methods of your Edge class!!";
            }

            if (Math.abs(sumWeights - 43.8) >= 1.0E-8) {
                return "BECAUSE the weights of the edges are not correct...\n  Check the getter methods of your Edge class!!";
            } else {
                return null;
            }
        }
    }

    public static String testKruskalDisconnected() {
        UndirectedGraph g = new UndirectedGraph(7);
        g.addEdge(2, 3, 4.0);
        g.addEdge(4, 5, 4.0);
        g.addEdge(0, 1, 6.0);
        g.addEdge(1, 3, 6.0);
        g.addEdge(0, 2, 12.0);
        g.addEdge(0, 3, 14.0);
        g.addEdge(5, 6, 15.0);
        g.addEdge(4, 6, 20.0);
        return g.kruskal() == null ? null : "Your kruskal method DOES NOT return null for a Graph such as, for example,\n" + g.toString() + "\n";
    }

    public static String testKruskal() {
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
        String trace = "Your method DOES NOT get the correct MST for a Graph such as, for example,\n" + g.toString() + "\n";
        Edge[] mST = g.kruskal();
        boolean okNumA = mST != null && mST.length == 6;
        if (!okNumA) {
            return mST == null ? "BECAUSE you return a NULL edge set...\n  Check the Edges you insert in the Priority Queue!!" : "BECAUSE you return a set of " + mST.length + " edges(!!), and it should have |V| - 1 = 6";
        } else {
            String comparison = " Edge\t   from your ST\t   from the correct MST\n";
            double cost = 0.0;

            for(int i = 0; i < mST.length; ++i) {
                cost += mST[i].getWeight();
            }

            int mstFailure = isKruskal(g, mST);
            double diff = Math.abs(cost - 44.0);
            boolean okCost = diff < 1.0E-8;
            if (mstFailure == -1) {
                return okCost ? null : trace + "BECAUSE its cost is NOT correct...\n\n\tIf its Edges are:\n" + Arrays.toString(mST) + "\tits cost should be 44.0 BUT, nevertheless it is " + cost + "\n";
            } else {
                Edge a = mST[mstFailure];
                return g.hasEdge(a.getSource(), a.getDestination()) && g.edgeWeight(a.getSource(), a.getDestination()) == a.getWeight() ? String.format("%sBECAUSE its Edge #%d generates a cycle:\n%s", trace, mstFailure, arr2str(mST)) : String.format("%sBECAUSE Edge %s does not exist\n", trace, a);
            }
        }
    }

    protected static int isKruskal(Graph g, Edge[] kruskal) {
        int vertices = kruskal.length + 1;
        UnionFind cc = new DisjointSetForest(vertices);

        for(int i = 0; i < kruskal.length; ++i) {
            Edge a = kruskal[i];
            int source = a.getSource();
            int destination = a.getDestination();
            if (cc.find(source) == cc.find(destination)) {
                return i;
            }

            if (!g.hasEdge(source, destination) || g.edgeWeight(source, destination) != a.getWeight()) {
                return i;
            }

            cc.union(cc.find(source), cc.find(destination));
        }

        return -1;
    }

    protected static String arr2str(Object[] arr) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < arr.length; ++i) {
            builder.append(i).append('\t').append(arr[i]).append('\n');
        }

        return builder.toString();
    }
}
