/*************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java. LineSegment.java
 *
 *  The opimized algorithm for finding collinear points
 *
 *************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints {
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
    public FastCollinearPoints(Point[] points)  {
        // finds all line segments containing 4 or more points
        // TODO Arrays.sort(points, Point.BY_SLOPE);
        
        if (points == null) throw new NullPointerException("null points argument");
        int numP = 0;
        for (Point p : points) {
          if (p == null) throw new NullPointerException("null point within input file");
          numP += 1;   
          
        }
        
        // check for dups
        for (int i = 0; i < numP; i++) {
            for (int j = 0; j < numP; j++) {
                if (i == j) continue;
                if (points[i] == null || points[j] == null) continue;
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("dup point");
            }
        }
          
        for (int i = 0; i < numP; i++) {
            
          //p.compareTo(
          //Point[] pts = points[i].slopeOrder<points[i]>;
          //Arrays.sort(points, new slopeOrder); //points[i].slopeOrder<points[i]>);
          //Arrays.sort(points, slopeOrder);
        }
          //Arrays.sort(points, SlopeOrder);
                      
          //System.out.println(p.toString());
          //if (pStr[p.toString()] == 1) throw new IllegalArgumentException("a duplicate point is loaded");
          //else pStr[p.toString()] = 1;

    }
    
    public int numberOfSegments()   {
        return nos;
    }
    public LineSegment[] segments()   {
         
        // pull out nulls, from the padded array
        LineSegment[] tmplines = new LineSegment[nos];
        for (int i = 0; i < nos; i++) {
            if (lines[i] != null) tmplines[i] = lines[i];
        }
        lines = new LineSegment[nos];
        for (int i = 0; i < nos; i++) {
            if (tmplines[i] != null) lines[i] = tmplines[i];
        }
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
    
    /*

Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null
or if any point in the array is null. 
Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.

Performance requirement. The order of growth of the running time of your program should be N2 log N in the worst case 
and it should use space proportional to N plus the number of line segments returned. 
FastCollinearPoints should work properly even if the input has 5 or more collinear points. 

*/
    
}

