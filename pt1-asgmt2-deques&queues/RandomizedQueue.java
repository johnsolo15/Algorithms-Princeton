import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] queue;
    private int tail;
    
    private class QueueIterator implements Iterator<Item> {
        Item[] iteratorQueue;
        int counter;
        
        private QueueIterator() {
            iteratorQueue = (Item[]) new Object[tail];
            counter = 0;
            
            for (int i = 0; i < tail; i++) {
                iteratorQueue[i] = queue[i];
            }
            StdRandom.shuffle(iteratorQueue);
        }
       
        public boolean hasNext() {
            return counter < tail;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            return iteratorQueue[counter++];
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        tail = 0;
    }
    
    public boolean isEmpty() {
        return tail == 0;
    }
    
    public int size() {
        return tail;
    }
    
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        
        if (tail == queue.length) {
            resizeLarger();
        }
        
        queue[tail++] = item;
    }
    
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        
        if (tail > 0 && tail == queue.length / 4) {
            resizeSmaller();
        }
        
        int i = StdRandom.uniform(tail);
        Item item = queue[i];
        queue[i] = queue[--tail];
        queue[tail] = null;
        
        return item;
    }
    
    public Item sample() {
        int i = StdRandom.uniform(tail);
        Item item = queue[i];
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }
    
    private void resizeLarger() {
        Item[] newQueue = (Item[]) new Object[queue.length * 2];
        
        for (int i = 0; i < queue.length; i++) {
            newQueue[i] = queue[i];
        }
        
        queue = newQueue;
    }
    
    private void resizeSmaller() {
        Item[] newQueue = (Item[]) new Object[queue.length / 2];
        
        for (int i = 0; i < newQueue.length; i++) {
            newQueue[i] = queue[i];
        }
        
        queue = newQueue;
    }

}