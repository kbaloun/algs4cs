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
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private int nos = 0;
    private int maxNos = 100;
    private LineSegment[] lines = new LineSegment[maxNos];
    private int maxPs = 1000;
    private String[] pStr = new String[maxPs];
    //private LineSegment[] lines = new LineSegment[1];

    /**
     * Initializes a new CollinearPoints computer
     *
     * @param  Point[] array of point objects
     * @param  first argument in main is the data file containing points
     * @throws NullPointerException when either the argument to the constructor is null or if any point in the array is null
     */
    public BruteCollinearPoints(Point[] points)  {
        // finds all line segments containing 4 points
        
        if (points == null) throw new NullPointerException("null points argument");
        int numP = 0;
        int ps = 0;
        for (Point p : points) {
          if (p == null) throw new NullPointerException("null point within input file");
          numP += 1;   

        }
        //System.out.println(numP);
        
        // compare each point only with forward points, so that no duplicate orders or permutations.
        int pos = 0;
        for (int i = 0; i < numP; i++) {
            for (int j = i; j < numP; j++) {
                for (int k = j; k < numP; k++) {
                    for (int m = k; m < numP; m++) {
                        // skip self referential comparisons
                        if (i == j || i == k || i == m) continue;
                        
                        // find collinear points
                        if ((points[i].slopeTo(points[j]) == (points[j].slopeTo(points[k])) &&
                             points[j].slopeTo(points[k]) == (points[k].slopeTo(points[m])))) {
                            if (points[i].slopeTo(points[m]) == (points[j].slopeTo(points[m])) && 
                                points[i].slopeTo(points[k]) == (points[j].slopeTo(points[m]))) {
                                
                                //System.out.println(points[i].toString());
                                for (int z = 0; z < maxPs; z++) {
                                     if (pStr[z] == points[i].toString()) throw new IllegalArgumentException("dup point");
                                     if (pStr[z] == null) break;
                                }
                                pStr[pos] = points[i].toString();
                                pos++;
                                
                                
                                // find first and last end point, from the set of 4.
                                // this is terrible spaghetti code :-(
                                
                                Point biggest = null;
                                Point smallest = null;
                                boolean mIsBiggest = false;
                                boolean iIsSmallest = false;
                                boolean jIsSmallest = false;
                                if (((points[m].compareTo(points[i])) > 0) && ((points[m].compareTo(points[j])) > 0) &&
                                    ((points[m].compareTo(points[k])) > 0)) { mIsBiggest = true; }
                                if (((points[i].compareTo(points[j])) < 0) && ((points[i].compareTo(points[k])) < 0) &&
                                    ((points[i].compareTo(points[m])) < 0)) { iIsSmallest = true; }
                                if (((points[j].compareTo(points[i])) < 0) && ((points[j].compareTo(points[k])) < 0) &&
                                    ((points[j].compareTo(points[m])) < 0)) { jIsSmallest = true; }
                                
                                    
                                if ((points[j].compareTo(points[i])) > 0) {
                                    // j is bigger than i
                                    if ((points[k].compareTo(points[j])) > 0) {
                                        // k is bigger than j & i
                                        if (mIsBiggest && iIsSmallest) {
                                            // m is biggest!  and i is smallest
                                            biggest = points[m];
                                            smallest = points[i];
                                        } else if (iIsSmallest) {
                                            // k is biggest!  (and i or m is smallest)
                                            biggest = points[k];
                                            smallest = points[i];
                                        } else {
                                            //m is smallest
                                            biggest = points[k];
                                            smallest = points[m];
                                        }
                                    } else {
                                        // j is bigger than k & i
                                        if (mIsBiggest && iIsSmallest) {
                                            // m is biggest!  and i is smallest
                                            biggest = points[m];
                                            smallest = points[i];
                                        } else if (iIsSmallest) {
                                            // j is biggest (and i or m are smallest)
                                            biggest = points[j];
                                            smallest = points[i];
                                        } else {
                                            biggest = points[j];
                                            smallest = points[m];
                                        }
                                    }
                                } else {
                                    // i is bigger than j
                                    if ((points[k].compareTo(points[i])) > 0) {
                                        // k is bigger than j & i
                                        if (mIsBiggest && jIsSmallest) {
                                            // m is biggest!  and j is smallest
                                            biggest = points[m];
                                            smallest = points[j];
                                        } else if (jIsSmallest) {
                                            // k is biggest!  
                                            biggest = points[k];
                                            smallest = points[j];
                                        } else {
                                            //m is smallest
                                            biggest = points[k];
                                            smallest = points[m];
                                        }
                                    } else {
                                        // i is bigger than j & k
                                        if (mIsBiggest && jIsSmallest) {
                                            // m is biggest!  and j is smallest
                                            biggest = points[m];
                                            smallest = points[j];
                                        } else if (jIsSmallest) {
                                            // i is biggest!  (and i or m is still smallest)
                                            biggest = points[i];
                                            smallest = points[j];
                                        } else {
                                            //m is smallest
                                            biggest = points[i];
                                            smallest = points[m];
                                        }
                                    }
                                }

                                lines[nos] = new LineSegment(smallest, biggest);
                                nos++;
                                //System.out.println(smallest + " < " + biggest);
                                
                                /*
                                double s1 = points[i].slopeTo(points[j]);
                                double s2 = points[j].slopeTo(points[k]);
                                double s3 = points[j].slopeTo(points[m]);
                                System.out.println(s1 + " " + s2 + " " +s3 + " " + points[i].toString() + " " +
                                    points[j].toString() + " " + points[k].toString() + " " + points[m].toString());
                                */

                            }
                        }
                    }
                }
            }
        }

        
        LineSegment[] tmplines = new LineSegment[nos];
        for (int i = 0; i < nos; i++) {
            if (lines[i] != null) tmplines[i] = lines[i];
        }
        lines = new LineSegment[nos];
        for (int i = 0; i < nos; i++) {
            if (tmplines[i] != null) lines[i] = tmplines[i];
        }

    }
    
    public   int numberOfSegments()  {
        // the number of line segments
        return nos;
    }
    
    public LineSegment[] segments()   {
        // the line segments
   
        /*
        for (LineSegment l : lines) {
            if (l != null) System.out.println(l.toString());
            //if (l.toString() == null) System.out.println("null string");
            System.out.println("null string?");
        }
        */
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
    
}

/* two easy opportunities. First, you can iterate through all combinations of 
 * 4 points (N choose 4) instead of all 4 tuples (N^4), saving a factor of 4! = 24. 
 * Second, you don't need to consider whether 4 points are collinear if you already 
 * know that the first 3 are not collinear; this can save you a factor of N on typical inputs. */

/*
Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null
  or if any point in the array is null. 
  Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.

Performance requirement. The order of growth of the running time of your program should be N4 in the worst case
 and it should use space proportional to N plus the number of line segments returned. 
*/

