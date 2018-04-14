import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class AncestorBFS {
    
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] vMarked;
    private boolean[] wMarked;
    private int[] vDistTo;
    private int[] wDistTo;
    private int ancestor;
    
    public AncestorBFS(Digraph graph, int v, int w) {
        vMarked = new boolean[graph.V()];
        wMarked = new boolean[graph.V()];
        vDistTo = new int[graph.V()];
        wDistTo = new int[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            vDistTo[i] = INFINITY;
            wDistTo[i] = INFINITY;
        }
        ancestor = -1;
        bfs(graph, v, w);
    }
    
    public AncestorBFS(Digraph graph, Iterable<Integer> v, Iterable<Integer> w) {
        vMarked = new boolean[graph.V()];
        wMarked = new boolean[graph.V()];
        vDistTo = new int[graph.V()];
        wDistTo = new int[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            vDistTo[i] = INFINITY;
            wDistTo[i] = INFINITY;
        }
        ancestor = -1;
        bfs(graph, v, w);
    }

    private void bfs(Digraph graph, int v, int w) {
        Queue<Integer> q = new Queue<Integer>();
        vMarked[v] = true;
        wMarked[w] = true;
        vDistTo[v] = 0;
        wDistTo[w] = 0;
        q.enqueue(v);
        q.enqueue(w);
        while (!q.isEmpty()) {
            int x = q.dequeue();
            if (vMarked[x] == true && wMarked[x] == true) {
                if (ancestor == -1) {
                    ancestor = x;
                } else if (dist(x) < dist(ancestor)) {
                    ancestor = x;
                }
            } 
            if (vMarked[x] == true) {
                for (int y : graph.adj(x)) {
                    if (!vMarked[y]) {
                        vMarked[y] = true;
                        vDistTo[y] = vDistTo[x] + 1;
                        q.enqueue(y);
                    }
                }
            }
            if (wMarked[x] == true) {
                for (int y : graph.adj(x)) {
                    if (!wMarked[y]) {
                        wMarked[y] = true;
                        wDistTo[y] = wDistTo[x] + 1;
                        q.enqueue(y);
                    }
                }
            }
        }
    }
    
    private void bfs(Digraph graph, Iterable<Integer> vs, Iterable<Integer> ws) {
        Queue<Integer> q = new Queue<Integer>();
        for (int v : vs) {
            vMarked[v] = true;
            vDistTo[v] = 0;
            q.enqueue(v);
        }
        for (int w : ws) {
            wMarked[w] = true;
            wDistTo[w] = 0;
            q.enqueue(w);
        }
        while (!q.isEmpty()) {
            int x = q.dequeue();
            if (vMarked[x] == true && wMarked[x] == true) {
                if (ancestor == -1) {
                    ancestor = x;
                } else if (dist(x) < dist(ancestor)) {
                    ancestor = x;
                }
            } 
            if (vMarked[x] == true) {
                for (int y : graph.adj(x)) {  
                    if (!vMarked[y]) {
                        vMarked[y] = true;
                        vDistTo[y] = vDistTo[x] + 1;
                        q.enqueue(y);
                    }
                }
            }
            if (wMarked[x] == true) {
                for (int y : graph.adj(x)) {  
                    if (!wMarked[y]) {
                        wMarked[y] = true;
                        wDistTo[y] = wDistTo[x] + 1;
                        q.enqueue(y);
                    }
                }
            }
        }
    }
    
    public int ancestor() {
        return ancestor;
    }
    
    public int distTo() {
        return dist(ancestor);
    }
    
    private int dist(int x) {
        if (x > -1) {
            return wDistTo[x] + vDistTo[x];
        } else {
            return -1;
        }
    }
    
}