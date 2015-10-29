/*************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java, LineSegment.java
 *
 *  Finds Collinear line segments, n^4 time
 *
 *
 *************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private int nos = 0;
    private int maxNos = 100;
    private LineSegment[] lines = new LineSegment[maxNos];

    /**
     * Initializes a new CollinearPoints computer
     *
     * @param  Point[] array of point objects
     * @param  first argument in main is the data file containing points
     * @throws NullPointerException when either the argument to the constructor is null or if any point in the array is null
     */
    public BruteCollinearPoints(Point[] points)  {
        // finds all line segments containing 4 points
        for (Point p : points) {
            System.out.println("looking at point");
        }
    }
    
    public   int numberOfSegments()  {
        // the number of line segments
        return nos;
    }
    
    public LineSegment[] segments()   {
        // the line segments
        return lines;
    }
    
    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
    
}

/* two easy opportunities. First, you can iterate through all combinations of 4 points (N choose 4) instead of all 4 tuples (N^4), saving a factor of 4! = 24. Second, you don't need to consider whether 4 points are collinear if you already know that the first 3 are not collinear; this can save you a factor of N on typical inputs. */

/*
Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null or if any point in the array is null. Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.

Performance requirement. The order of growth of the running time of your program should be N4 in the worst case and it should use space proportional to N plus the number of line segments returned. 
*/

