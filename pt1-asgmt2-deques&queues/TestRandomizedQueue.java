public class TestRandomizedQueue {

    public static void enqueue() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(8);
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }
    
    public static void random() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println(queue.dequeue());
        System.out.println("size " + queue.size());
        queue.enqueue(8);
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        queue.enqueue(3);
        System.out.println(queue.dequeue());
        System.out.println("size " + queue.size());
    }
    
    public static void testNull() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        try {
            queue.enqueue(null);
        } catch (Exception e) {
            System.out.println("It's null idiot");
        }
    }
    
    public static void empty() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        try {
            queue.dequeue();
        } catch (Exception e) {
            System.out.println("It's empty idiot");
        }
    }
    
    public static void iterator() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(8);
        queue.enqueue(6);
        queue.enqueue(1);
        queue.enqueue(9);
        
        for (Integer i : queue) {
            System.out.println(i);
        }
    }
    
    public static void main(String[] args) {
        TestRandomizedQueue.enqueue();
        TestRandomizedQueue.random();
        TestRandomizedQueue.testNull();
        TestRandomizedQueue.empty();
        TestRandomizedQueue.iterator();
    }

}