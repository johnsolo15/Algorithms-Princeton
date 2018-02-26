import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;

public class Percolation {
    
    private int size;
    private int top;
    private int bottom;
    private int openSites;
    private boolean[][] grid;
    private WeightedQuickUnionUF UF;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size must be bigger than 0");
        }
        
        size = n;
        top = 0;
        bottom = n * n + 1;
        openSites = 0;
        grid = new boolean[n][n]; 
        UF = new WeightedQuickUnionUF(n * n + 2);
    }
    
    public void open(int row, int col) {
        if (!legalArguments(row, col)) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        
        if (row == 1) {
            UF.union(convertXY(row, col), top);
        }
        
        if (row == size) {
            UF.union(convertXY(row, col), bottom);
        }
        
        if (row > 1 && isOpen(row-1, col)) {
            UF.union(convertXY(row, col), convertXY(row-1, col));
        }
        
        if (row < size && isOpen(row+1, col)) {
            UF.union(convertXY(row, col), convertXY(row+1, col));
        }
        
        if (col > 1 && isOpen(row, col-1)) {
            UF.union(convertXY(row, col), convertXY(row, col-1));
        }
        
        if (col < size && isOpen(row, col+1)) {
            UF.union(convertXY(row, col), convertXY(row, col+1));
        }
        
        grid[row-1][col-1] = true;
        openSites++;
    }
    
    public boolean isOpen(int row, int col) {
        if (!legalArguments(row, col)) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        
        return grid[row-1][col-1];
    }
    
    public boolean isFull(int row, int col) {
        if (!legalArguments(row, col)) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        
        return UF.connected(top, convertXY(row, col));
    }
    
    public int numberOfOpenSites() {
        return openSites;
    }
    
    public boolean percolates() {
        return UF.connected(top, bottom);
    }
    
    private int convertXY(int x, int y) {
        return size * (x - 1) + y;
    }
    
    private boolean legalArguments(int x, int y) {
        if (x < 0 || y < 0 || x > size || y > size) {
            return false;
        }
        
        return true;
    }
    
}