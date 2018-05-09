public class BoggleTrie {
    private static final int R = 26;
    private static final int OFFSET = 'A';
    private Node root;
    
    public BoggleTrie() {
    
    }
    
    public void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }
    
    private Node add(Node x, String key, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            x.isString = true;
        } else {
            int c = key.charAt(d) - OFFSET;
            x.next[c] = add(x.next[c], key, d+1);
        }
        return x;
    }
    
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }
    
    private Node get(Node x, String key, int d) {
        if (x == null || d == key.length()) {
            return x;
        }
        int c = key.charAt(d) - OFFSET;
        return get(x.next[c], key, d+1);
    }
    
    public boolean hasPrefix(String prefix) {
        Node x = get(root, prefix, 0);
        return x != null;
    }

    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }
}