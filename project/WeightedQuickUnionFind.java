
public class WeightedQuickUnionFind {

    private int[] parent;
    private int[] size;
    private int count;
    private static final int ROOT = -1;


    public WeightedQuickUnionFind(int count) {
        this.count = count;
        this.parent = new int[count];
        this.size = new int[count];
        for (int i = 0; i < count; i++) {
            parent[i] = ROOT;
            size[i] = 1;
        }
    }


    //unions roots of two elements based on their size.
    //the element's root with a less size will be the child of the other specified element.

    public void union(int p, int q) {

        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ) {
            return;
        }

        if (size[rootP] >= size[rootQ]) {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        } else {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
    }


    //finding the root of the element

    public int find(int p) {
        while (parent[p] != ROOT) {
            p = parent[p];
        }

        return p;
    }


    public int count() {
        return this.count;
    }

}
