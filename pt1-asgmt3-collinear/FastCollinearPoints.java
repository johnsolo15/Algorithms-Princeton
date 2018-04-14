import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.IllegalArgumentException;

public class FastCollinearPoints {
    
    private ArrayList<LineSegment> segments;
    
    public FastCollinearPoints(Point[] points) {
        if (points == null || Arrays.asList(points).contains(null)) {
            throw new IllegalArgumentException();
        }
        
        segments = new ArrayList<LineSegment>();
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        ArrayList<Point> coPoints = new ArrayList<Point>();

        for (int p = 0; p < points.length; p++) {
            Arrays.sort(sortedPoints, points[p].slopeOrder());
            
            for (int q = 1; q < sortedPoints.length; q++) {
                if (points[p].compareTo(sortedPoints[q]) == 0) {
                    throw new IllegalArgumentException();
                }
                if (coPoints.isEmpty()) {
                    coPoints.add(sortedPoints[q]);
                } else if (points[p].slopeTo(sortedPoints[q]) == points[p].slopeTo(sortedPoints[q - 1])) {
                    coPoints.add(sortedPoints[q]);
                } else if (coPoints.size() >= 3) {
                    Collections.sort(coPoints);

                    if (points[p].compareTo(coPoints.get(0)) < 0) {
                        segments.add(new LineSegment(points[p], coPoints.get(coPoints.size() - 1)));
                    }
                    
                    coPoints.clear();
                    coPoints.add(sortedPoints[q]);
                } else {
                    coPoints.clear();
                    coPoints.add(sortedPoints[q]);
                }
            }
            if (coPoints.size() >= 3) {
                Collections.sort(coPoints);

                if (points[p].compareTo(coPoints.get(0)) < 0) {
                    segments.add(new LineSegment(points[p], coPoints.get(coPoints.size() - 1)));
                }
                coPoints.clear();
            }
        }
    }
    
    public int numberOfSegments() {
        return segments.size();
    }
    
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    public static void main(String[] args) {
        Point[] points = new Point[14];
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
        points[13] = new Point(1,5);
        
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        
        System.out.println(fcp.numberOfSegments());
        for (LineSegment line : fcp.segments()) {
            System.out.println(line);
        }
    }
    
}