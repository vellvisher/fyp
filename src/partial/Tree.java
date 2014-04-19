package partial;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree<T> {
    public Node root;
    public int fanOut;
    public class Node {
        public List<Node> children;
        public T value;
        public Integer highest;
        public Integer lowest;

        public Node(T value) {
            this.value = value;
            children = new ArrayList<Node>();
        }
    }

    public Tree(List<T> values, int fanOut) {
        this.fanOut = fanOut;
        this.root = buildTree(values, fanOut);
    }

    public Tree(List<T> nodes) {
        this(nodes, 2);
    }

    private Node buildTree(List<T> values, int fanOut) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException();
        }

        ArrayList<Node> newNodes = new ArrayList();
        for (int i = 0; i < values.size(); i += fanOut) {
            List<Node> children = new ArrayList<Node>();
            for (int j = i; j < i + fanOut && j < values.size(); j++) {
                children.add(new Node(values.get(j)));
            }
            /* parent.children = e */
        }

        return root;
    }

    public int getCount() {
        if (this.root == null) {
            return 0;
        }
        int count = 1;
        /* Queue<Node> nodes = new LinkedList<Node>(root.children); */

        /* while (!nodes.isEmpty()) { */
        /*     Node p = nodes.poll(); */
        /*     if (p != null) { */
        /*         nodes.addAll(p.children); */
        /*         count++; */
        /*     } */
        /* } */
        return count;
    }

    public Object getQuery(int i, int j) {
        return null;
    }
}
