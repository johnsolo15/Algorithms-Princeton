import java.lang.Math;
import edu.princeton.cs.algs4.Queue;

public class Board {
    
    private final int[][] tiles;
    private final int n;
    
    public Board(int[][] blocks) {
        n = blocks.length;
        tiles = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
    }
    
    public int dimension() {
        return n;
    }
    
    public int hamming() {
        int score = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                if (tiles[i][j] != (i*n) + (j + 1)) {
                    score++;
                }
            }
        }
        
        return score;
    }
    
    public int manhattan() {
        int score = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                   continue;
                } else {
                    score += Math.abs(i - (tiles[i][j] - 1)/n);
                    score += Math.abs(j - (tiles[i][j] - 1)%n); 
                }
            }
        }
        
        return score;
    }
    
    public boolean isGoal() {
        return hamming() == 0;    
    }
    
    public Board twin() {
        Board twin = new Board(tiles);
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (twin.tiles[i][j] != 0 && twin.tiles[i][j + 1] != 0) {
                    int temp = twin.tiles[i][j];
                    twin.tiles[i][j] = twin.tiles[i][j + 1];
                    twin.tiles[i][j + 1] = temp;
                    return twin;
                }
            }
        }
        
        return null;
    }
    
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        
        Board that = (Board) y;
        if (that.n != this.n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<Board>();
        Board neighbor;
        int emptyRow = 0;
        int emptyCol = 0;
        
        search:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break search;
                }
            }
        }
        
        if (emptyRow > 0) {
            neighbor = new Board(tiles);
            neighbor.tiles[emptyRow][emptyCol] = neighbor.tiles[emptyRow - 1][emptyCol];
            neighbor.tiles[emptyRow - 1][emptyCol] = 0;
            queue.enqueue(neighbor);
        }
        if (emptyRow < n - 1) {
            neighbor = new Board(tiles);
            neighbor.tiles[emptyRow][emptyCol] = neighbor.tiles[emptyRow + 1][emptyCol];
            neighbor.tiles[emptyRow + 1][emptyCol] = 0;
            queue.enqueue(neighbor);
        }
        if (emptyCol > 0) {
            neighbor = new Board(tiles);
            neighbor.tiles[emptyRow][emptyCol] = neighbor.tiles[emptyRow][emptyCol - 1];
            neighbor.tiles[emptyRow][emptyCol - 1] = 0;
            queue.enqueue(neighbor);
        }
        if (emptyCol < n - 1) {
            neighbor = new Board(tiles);
            neighbor.tiles[emptyRow][emptyCol] = neighbor.tiles[emptyRow][emptyCol + 1];
            neighbor.tiles[emptyRow][emptyCol + 1] = 0;
            queue.enqueue(neighbor);
        }
        
        return queue;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        
        return s.toString();
    }

    public static void main(String[] args) {
        int n = 3;
        int[] contents = {1, 2, 4, 4, 5, 6, 7, 8, 0};
        int[] contents2 = {1, 2, 4, 4, 5, 6, 7, 8, 0};
        int[][] array = new int[n][n];
        int[][] array2 = new int[n][n];
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = contents[count];
                count++;
            }
        }
        
        count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array2[i][j] = contents2[count];
                count++;
            }
        }

        Board board = new Board(array);
        Board board2 = new Board(array2);
        Board twin = board.twin(); 
        
        System.out.println(board);
        System.out.println("hamming: " + board.hamming());
        System.out.println("manhattan: " + board.manhattan());
        System.out.println("goal: " + board.isGoal());
        
        System.out.println("twin:");
        System.out.println(twin);
        System.out.println("original and twin equal? " + board.equals(twin));
        
        System.out.println("board2:");
        System.out.println(board2);
        System.out.println("original and board2 equal? " + board.equals(board2));
        
        Iterable<Board> neighbors = board.neighbors();
        System.out.println("neighbors");
        for (Board b : neighbors) {
            System.out.println(b);
        }
    }

}