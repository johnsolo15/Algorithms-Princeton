import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rQueue.enqueue(s);
        }
               
        Iterator<String> iterator = rQueue.iterator();       
        for (int i = 0; i < k; i++) {
            String item = iterator.next();
            System.out.println(item);
        }
    }

}