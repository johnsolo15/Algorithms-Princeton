import java.util.ArrayList;
import java.lang.IllegalArgumentException;
public class BruteCollinearPoints {
    
    private ArrayList<LineSegment> segments;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        
        segments = new ArrayList<LineSegment>();
        
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j != i && points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
                for (int k = 0; k < points.length; k++) {
                    for (int m = 0; m < points.length; m++) {
                        if (points[i] == null || points[j] == null || points[k] == null || points[m] == null) {
                            throw new IllegalArgumentException();
                        }
                        if (isLineSegment(points[i],points[j],points[k],points[m])) {
                            segments.add(new LineSegment(points[i],points[m]));
                        }
                    }
                }
            }
        }
    }
    
    public int numberOfSegments() {
        return segments.size();
    }
    
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    private boolean isLineSegment(Point a, Point b, Point c, Point d) {
        if (a.slopeTo(b) == Double.NEGATIVE_INFINITY) {
            return false;
        }
        if (a.compareTo(b) != -1 || b.compareTo(c) != -1 || c.compareTo(d) != -1) {
            return false;
        }
        if (a.slopeTo(b) != a.slopeTo(c) || a.slopeTo(b) != a.slopeTo(d)) {
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        Point[] points = new Point[13];
        points[0] = new Point(1,0);
        points[1] = new Point(1,3);
        points[2] = new Point(2,1);
        points[3] = new Point(2,2);
        points[4] = new Point(2,4);
        points[5] = new Point(3,0);
        points[6] = new Point(4,3);
        points[7] = new Point(4,2);
        points[8] = new Point(5,1);
        points[9] = new Point(5,4);
        points[10] = new Point(6,4);
        points[11] = new Point(7,4);
        points[12] = new Point(3,3);
        
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
           
        System.out.println(bcp.numberOfSegments());
        
        for (LineSegment line : bcp.segments()) {
            System.out.println(line);
        }
    }
}