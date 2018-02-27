import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int size;
    
    private class Node {
        Node prev;
        Node next;
        Item data;
        
        public Node(Item item) {
            prev = null;
            next = null;
            data = item;
        }
    }
    
    private class DequeIterator implements Iterator<Item> {
        Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            Item item = current.data;
            current = current.next;
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        
        Node node = new Node(item); 
        
        if (isEmpty()) {
            last = node;
        } else {
            node.next = first;
            first.prev = node;
        }
        
        first = node;
        size++;
    }
    
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        
        Node node = new Node(item);
        
        if (isEmpty()) {
            first = node;
        } else {
            node.prev = last;
            last.next = node;
        }
        
        last = node;
        size++;
    }
    
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        
        Item removed = first.data;
        
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        
        size--;
        return removed;
    }
    
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        
        Item removed = last.data;
        
        if (size == 1) {
            first = null;
            last = null;      
        } else {
            last = last.prev;
            last.next = null;
        }
        
        size--;
        return removed;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
}