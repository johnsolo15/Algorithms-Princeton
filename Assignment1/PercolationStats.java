import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.Math;
import java.lang.IllegalArgumentException;

public class PercolationStats {

    private double[] thresholds;
    private int runs;
    
    public PercolationStats(int n, int T) {
        runs = T;
        thresholds = new double[T];
        
        for (int i = 0; i < T; i ++) {
            thresholds[i] = runTest(n);
        }
    }
    
    private double runTest(int n) {
        Percolation perc = new Percolation(n);

        while (!perc.percolates()) {
            int x = StdRandom.uniform(1, n+1);
            int y = StdRandom.uniform(1, n+1);
            
            if (!perc.isOpen(x, y)) {
                perc.open(x, y);
            }
        }
        
        return perc.numberOfOpenSites() / (double) (n * n);
    }
    
    public double mean() {
        return StdStats.mean(thresholds);
    }
    
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(runs);
    }
    
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(runs);
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        if (n <= 0 || T <= 0) {
            throw new IllegalArgumentException("Arguments cannout be zero or less");
        }
        
        PercolationStats stats = new PercolationStats(n, T);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }

}