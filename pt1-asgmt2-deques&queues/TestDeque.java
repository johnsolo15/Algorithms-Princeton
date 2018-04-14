public class TestDeque {
   
    public static void firstInFirstOut() {
        Deque<Integer> deque = new Deque<Integer>();
        
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i);
        }
        
        System.out.println("size: "+deque.size());
        for (int i = 0; i < 10; i++) {
            System.out.println(deque.removeLast());
        }
    }
    
    public static void firstInFirstOut2() {
        Deque<Integer> deque = new Deque<Integer>();
        
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
        }
        
        for (int i = 0; i < 10; i++) {
            System.out.println(deque.removeFirst());
        }
    }
    
    public static void firstInLastOut() {
        Deque<Integer> deque = new Deque<Integer>();
        
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i);
        }
        
        for (int i = 0; i < 10; i++) {
            System.out.println(deque.removeFirst());
        }
    }
    
    public static void addNull() {
        Deque<Integer> deque = new Deque<Integer>();
        
        try {
            deque.addLast(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            deque.addFirst(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void removeFromEmptyDeque() {
        Deque<Integer> deque = new Deque<Integer>();
        
        try {
            deque.removeLast();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            deque.removeFirst();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void addFirst() {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("Hello");
        System.out.println(deque.removeLast());
    }
    
    public static void addLast() {
        Deque<String> deque = new Deque<String>();
        deque.addLast("Hello");
        System.out.println(deque.removeFirst());
    }
    
    public static void testIterator() {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("Hello");
        deque.addLast("World");
        deque.addLast("How");
        deque.addLast("Are");
        deque.addLast("You");
        
        for (String s : deque) {
            System.out.println(s);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Add First - Remove Last");
        TestDeque.firstInFirstOut();
        System.out.println("Add Last - Remove First");
        TestDeque.firstInFirstOut2();
        System.out.println("Add First - Remove First");
        TestDeque.firstInLastOut();
        System.out.println("Remove from empty deque");
        TestDeque.removeFromEmptyDeque();
        System.out.println("Add null to deque");
        TestDeque.addNull();
        System.out.println("Add first - remove last string");
        TestDeque.addFirst();
        System.out.println("Add last - remove first string");
        TestDeque.addLast();
        System.out.println("Iterator");
        TestDeque.testIterator();
    }

}