import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;
import java.lang.IllegalArgumentException;

public class KdTree {
    
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private int size;
        
        public Node(Point2D p, int size) {
            this.p = p;
            this.size = size;
        }  
    }
    
    private Node root;
    
    public KdTree() {
        root = null;
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public int size() {
        return size(root);
    }
    
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        Node node = new Node(p, 1);
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = insert(root, node, rect, true);
    }
    
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        return get(root, new Node(p, 1), true) != null;
    }
    
    public void draw() {
        draw(root, true);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        Queue<Point2D> queue =  new Queue<Point2D>();
        range(root, rect, queue);
        
        return queue;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        if (isEmpty()) {
            return null;
        }
        return nearest(p, root.p, root, true);
    }
    
    private Node insert(Node r, Node n, RectHV rect, boolean xcmp) {
        if (r == null) {
            n.rect = rect;
            return n;
        }
        if (r.p.equals(n.p)) {
            return r;
        }
        if (xcmp) {
            if (n.p.x() < r.p.x()) {
                rect = new RectHV(r.rect.xmin(), r.rect.ymin(), r.p.x(), r.rect.ymax());
                r.lb = insert(r.lb, n, rect, !xcmp);
            } else {
                rect = new RectHV(r.p.x(), r.rect.ymin(), r.rect.xmax(), r.rect.ymax());
                r.rt = insert(r.rt, n, rect, !xcmp);
            }
        } else {
            if (n.p.y() < r.p.y()) {
                rect = new RectHV(r.rect.xmin(), r.rect.ymin(), r.rect.xmax(), r.p.y());
                r.lb = insert(r.lb, n, rect, !xcmp);
            } else {
                rect = new RectHV(r.rect.xmin(), r.p.y(), r.rect.xmax(), r.rect.ymax());
                r.rt = insert(r.rt, n, rect, !xcmp);
            }
        }
        r.size = 1 + size(r.lb) + size(r.rt);
        
        return r;
    }
    
    private int size(Node n) {
        if (n == null) {
            return 0;
        }
        
        return n.size;
    }
    
    private Node get(Node r, Node n, boolean xcmp) {
        if (r == null) {
            return null;
        }
        if (r.p.equals(n.p)) {
            return r;
        }
        if (xcmp) {
            if (n.p.x() < r.p.x()) {
                return get(r.lb, n, !xcmp);
            } else {
                return get(r.rt, n, !xcmp);
            }
        } else {
            if (n.p.y() < r.p.y()) {
                return get(r.lb, n, !xcmp);
            } else {
                return get(r.rt, n, !xcmp);
            }
        }
    }
    
    private void range(Node n, RectHV rect, Queue<Point2D> queue) {
        if (n == null) {
            return;
        }
        if (!rect.intersects(n.rect)) {
            return;
        }
        if (rect.contains(n.p)) {
            queue.enqueue(n.p);
        }
        range(n.lb, rect, queue);
        range(n.rt, rect, queue);
    }
    
    private Point2D nearest(Point2D search, Point2D nearest, Node n, Boolean xcmp) {
        if (n == null) {
            return nearest;
        }
        if (n.rect.distanceSquaredTo(search) > nearest.distanceSquaredTo(search)) {
            return nearest;
        }
        if (search.distanceSquaredTo(n.p) < search.distanceSquaredTo(nearest)) {
            nearest = n.p;
        }
        if (xcmp) {
            if (search.x() < n.p.x()) {
                nearest = nearest(search, nearest, n.lb, !xcmp);
                nearest = nearest(search, nearest, n.rt, !xcmp);
            } else {
                nearest = nearest(search, nearest, n.rt, !xcmp);
                nearest = nearest(search, nearest, n.lb, !xcmp);
            }
        } else {
            if (search.y() < n.p.y()) {
                nearest = nearest(search, nearest, n.lb, !xcmp);
                nearest = nearest(search, nearest, n.rt, !xcmp);
            } else {
                nearest = nearest(search, nearest, n.rt, !xcmp);
                nearest = nearest(search, nearest, n.lb, !xcmp);
            }
        }
        return nearest;
    }
    
    private void draw(Node node, boolean xcmp) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        if (xcmp) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        draw(node.lb, !xcmp);
        draw(node.rt, !xcmp);
    }
    
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        /*
        tree.insert(new Point2D(.1, .1));
        tree.insert(new Point2D(.3, .3));
        tree.insert(new Point2D(.3, .4));
        tree.insert(new Point2D(.5, .3));
        tree.insert(new Point2D(.9, .7));
        */
        //tree.insert(new Point2D(.5, .5));
        System.out.println(tree.size());
        System.out.println(tree.contains(new Point2D(.5, .5)));

        for (Point2D p : tree.range(new RectHV(0.0, 0.0, 0.9, 0.3))) {
            System.out.println(p);
        }
        
        System.out.println(tree.nearest(new Point2D(.6, .3)));
    }
    
}