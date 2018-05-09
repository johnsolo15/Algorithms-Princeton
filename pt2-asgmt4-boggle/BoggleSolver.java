import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashSet;

public class BoggleSolver {
    private BoggleTrie dict;
    
    public BoggleSolver(String[] dictionary) {
        dict = new BoggleTrie();
        for (String s : dictionary) {
            dict.add(s);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        HashSet<String> wordSet = new HashSet<String>();
        
        for (int i = 0; i < board.rows(); i ++) {
            for (int j = 0; j < board.cols(); j++) {
                search(board, marked, i, j, wordSet, "");
            }
        }
        
        return wordSet;
    }
    
    private void search(BoggleBoard board, boolean[][] marked, int row, int col, HashSet<String> wordSet, String prefix) {
        char c = board.getLetter(row, col);
        String word;
        if (c == 'Q') {
            word = prefix + "QU";
        } else {
            word = prefix + c;
        }
        if (!dict.hasPrefix(word)) {
            return;
        }
        if (word.length() > 2 && dict.contains(word)) {
            wordSet.add(word);
        }
        
        marked[row][col] = true;
        for (int i = Math.max(0, row - 1); i <= Math.min(board.rows() - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(board.cols() - 1, col + 1); j++) {
                if (!marked[i][j]) {
                    search(board, marked, i, j, wordSet, word);
                }
            }
        } 
        marked[row][col] = false;
    }

    public int scoreOf(String word) {
        int length = word.length();
        if (length < 3 || !dict.contains(word)) {
            return 0;
        } else if (length == 3 || length == 4) {
            return 1;
        } else if (length == 5) {
            return 2;
        } else if (length == 6) {
            return 3;
        } else if (length == 7) {
            return 5;
        } else {
            return 11;
        }
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
    
}