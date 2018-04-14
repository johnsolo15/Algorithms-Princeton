import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;
import java.lang.IllegalArgumentException;
    
public class PointSET {
    private TreeSet<Point2D> set;
    
    public PointSET() {
        set = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    public int size() {
        return set.size();
    }
    
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        set.add(p);
    }
    
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        return set.contains(p);
    }
    
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        
        for (Point2D p : set) {
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                queue.enqueue(p);
            }
        }
        
        return queue;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        if (set.isEmpty()) {
            return null;
        }
        
        Point2D nearest = null;
        for (Point2D point : set) {
            if (nearest == null || p.distanceSquaredTo(point) < p.distanceSquaredTo(nearest)) {
                nearest = point;
            }
        }
        
        return nearest;
    }
    
    public static void main(String[] args) {
        PointSET points = new PointSET();
        points.insert(new Point2D(.1, .1));
        points.insert(new Point2D(.3, .3));
        points.insert(new Point2D(.3, .4));
        points.insert(new Point2D(.5, .3));
        points.insert(new Point2D(.9, .7));
        
        Point2D nearest = points.nearest(new Point2D(.4, .1));
        System.out.println(nearest);
        
        for (Point2D p: points.range(new RectHV(.3, .3, .6, .5))) {
            System.out.println(p);
        }
    }
    
}