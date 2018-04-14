import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import java.lang.IllegalArgumentException;

public class Solver {
    
    private class SearchNode implements Comparable<SearchNode> {
        
        private Board board;
        private SearchNode predecessor;
        private int moves;
        private int hamming;
        private int manhattan;

        
        public SearchNode(Board board, SearchNode predecessor, int moves) {
            this.board = board;
            this.predecessor = predecessor;
            this.moves = moves;
            
            this.hamming = this.board.hamming() + this.moves;
            this.manhattan = this.board.manhattan() + this.moves;
        }
        
        public Iterable<SearchNode> neighbors() {
            Queue<SearchNode> queue = new Queue<SearchNode>();
            SearchNode node;
            
            for (Board b : board.neighbors()) {
                if (predecessor == null || !b.equals(predecessor.board)) {
                    node = new SearchNode(b, this, moves + 1);
                    queue.enqueue(node);
                }
            }
            
            return queue;
        }
        
        public int compareTo(SearchNode other) {
            return this.manhattan - other.manhattan;
        }
        
    }
    
    private boolean solvable;
    private int moves;
    private Stack<Board> solution;
    
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, null, 0));
        twinPq.insert(new SearchNode(initial.twin(), null, 0));
        SearchNode min;
        SearchNode twinMin;
        
        while (true) {
            min = pq.delMin();
            for (SearchNode neighbor : min.neighbors()) {
                pq.insert(neighbor);
            }
            
            twinMin = twinPq.delMin();
            for (SearchNode neighbor : twinMin.neighbors()) {
                twinPq.insert(neighbor);
            }
            
            if (min.board.isGoal()) {
                solvable = true;
                break;
            }
            if (twinMin.board.isGoal()) {
                solvable = false;
                break;
            }  
        }
        
        if (solvable) {
            moves = min.moves;
            solution = new Stack<Board>();
            
            while (min != null) {
                solution.push(min.board);
                min = min.predecessor;
            } 
        } else {
            moves = -1;
            solution = null;
        }
    }
    
    public boolean isSolvable() {
        return solvable;
    }
    
    public int moves() {
        return moves;
    }
    
    public Iterable<Board> solution() {
        return solution;
    }
    
    public static void main(String[] args) {
        int n = 3;
        int[] contents = {1, 2, 3, 0, 7, 6, 5, 4, 8};
        int[][] array = new int[n][n];
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = contents[count];
                count++;
            }
        }
        
        Board initial = new Board(array);
        Solver solver = new Solver(initial);
        System.out.println(solver.isSolvable());
        if (solver.isSolvable()) {
            System.out.println(solver.moves());
            for (Board b : solver.solution()) {
                System.out.println(b);
            }
        }
    }

}