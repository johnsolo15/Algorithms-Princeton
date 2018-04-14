public class Main {
    
    public static void main(String[] args) {
        Percolation perc = new Percolation(4);
        perc.open(1,4);
        System.out.println(perc.percolates());
        perc.open(3,1);
        System.out.println(perc.percolates());
        perc.open(1,3);
        System.out.println(perc.percolates());
        perc.open(1,1);
        System.out.println(perc.percolates());
        perc.open(4,3);
        System.out.println(perc.percolates());
        perc.open(3,4);
        System.out.println(perc.percolates());
    }
    
}