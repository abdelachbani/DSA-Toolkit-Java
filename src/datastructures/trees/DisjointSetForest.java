package datastructures.trees;

import datastructures.interfaces.UnionFind;

public class DisjointSetForest implements UnionFind {
    protected int[] parent;

    public DisjointSetForest(int n) {
        this.parent = new int[n];

        for(int i = 0; i < this.parent.length; ++i) {
            this.parent[i] = -1;
        }
    }

    public int find(int i) {
        return this.parent[i] < 0 ? i : (this.parent[i] = this.find(this.parent[i]));
    }

    public void union(int classI, int classJ) {
        if (this.parent[classI] == this.parent[classJ]) {
            this.parent[classI] = classJ;
            this.parent[classJ]--;
        } else if (this.parent[classI] < this.parent[classJ]) {
            this.parent[classJ] = classI;
        } else {
            this.parent[classI] = classJ;
        }
    }

    public String toString() {
        String res = "";

        for(int i = 0; i < this.parent.length; ++i) {
            res = res + String.format("%3d : %3d\n", i, this.parent[i]);
        }

        return res;
    }
}
