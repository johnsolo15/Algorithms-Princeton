import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph graph;

   // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        graph = new Digraph(G);
    }

   // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        AncestorBFS bfs = new AncestorBFS(graph, v, w);
        return bfs.distTo();
    }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        AncestorBFS bfs = new AncestorBFS(graph, v, w);
        return bfs.ancestor();
    }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        AncestorBFS bfs = new AncestorBFS(graph, v, w);
        return bfs.distTo();
    }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        AncestorBFS bfs = new AncestorBFS(graph, v, w);
        return bfs.ancestor();
    }
    
    private void validateVertex(int v) {
        if (v < 0 || v >= graph.V()) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateVertex(Iterable<Integer> vs) {
        if (vs == null) {
            throw new IllegalArgumentException();
        }
        for (int v : vs) {
            if (v < 0 || v >= graph.V()) {
                throw new IllegalArgumentException();
            }
        }
    }

   // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}