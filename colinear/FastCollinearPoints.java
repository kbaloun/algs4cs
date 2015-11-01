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
    private final int maxNos = 10001;
    private final int maxPts = 500;
    private final LineSegment[] tmplines = new LineSegment[maxNos];
    private boolean debug = false;

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
        
        // prepare the array
        
        // sort the array.  is this insertion sort? why?
        // all lines will point up and to the right.
        // this is wrong and not needed. we check the sorted slope on all points, and this breaks "natural order"
        /*
        Object[] opnts = new Object[numP];
        for (int i = 0; i < numP; i++) { opnts[i] = (Object) points[i]; }
        Arrays.sort(opnts);
        //for (int i = 0; i < numP; i++) { System.out.println(i + " " + opnts[i]); }
        for (int i = 0; i < numP; i++) { points[i] = (Point) opnts[i]; }
        */
        
        Point[] slopePts = new Point[numP];
        for (int i = 0; i < numP; i++) { slopePts[i] = points[i]; }
        Point[] donePts = new Point[numP*3]; // to avoid duplicate entries.  can be smaller?
        double[] doneSlopes = new double[numP*3];
        int dos = 0;

        
        // main search for collinear points
        for (int i = 0; i < numP; i++) {  
            if (debug) System.out.println(i + " " + points[i]);

            // need to sort ALL other forward&backward points by slope to this point p.i
            Arrays.sort(slopePts, points[i].slopeOrder());
            

            int dupCnt = 0;
            for (int j = 0; j <= numP-1; j++) { 
                Point[] foundPts = new Point[maxPts];
                int fos = 0;
                if (debug) System.out.println("s" + j + " " + slopePts[j] + " " + points[i].slopeTo(slopePts[j]));
                // check for duplicate points
                if (points[i].compareTo(slopePts[j]) == 0) {
                    dupCnt++; //the first time, i found the point itself.  second time is dup.
                    if (dupCnt > 1) throw new IllegalArgumentException("duplicate point");
                }
                 
                int k = j + 1;
                int w = 0;  //redundant to k, but i need simple counting right now
                double foundSlope = points[i].slopeTo(slopePts[j]);
                while (k < numP && foundSlope == points[i].slopeTo(slopePts[k])) {
                    // find the highest collinear point
                    //if (k > numP-1) break; // avoid array out of bounds exception
                    w++;
                    k++;
                }
                if (w > 1) {   // at least 2 equal slopes, cuz back to pt i makes the 3rd.
                    boolean addIt = true;
                    for (int m = 0; m < dos; m++) {
                        // don't add duplicated sub segments
                        if (donePts[m] == null) break;
                        if (foundSlope == doneSlopes[m] && foundSlope == points[i].slopeTo(donePts[m])) { 
                            // if this point has been included *for this same slope*, don't count it
                            addIt = false; 
                            if (debug) System.out.println("skipping duplicate" + points[i] + " already with " + donePts[m]);
                            break;
                        }
                    }
                    if (addIt) {
                        foundPts[fos++] = points[i];
                        for (int f = 0; f <= w; f++) {
                            foundPts[fos++] = slopePts[j+f];
                            //System.out.println("f" + f + " " + slopePts[j+f]);
                        }
                        Object[] ofp = new Object[fos];
                        for (int f = 0; f < fos; f++) { if (foundPts[f] != null) ofp[f] = (Object) foundPts[f]; }
                        Arrays.sort(ofp); 
                        for (int f = 0; f < fos; f++) {
                            if (ofp[f] != null) foundPts[f] = (Point) ofp[f];
                        }
                        Point smallest = foundPts[0];
                        Point biggest = foundPts[fos-1];
                        if (debug) {
                            System.out.println("adding smallest" + j + " " + points[i] + " with k,w of " + k + "," + w); 
                            System.out.println("adding biggest" + j + " " + foundPts[fos-1] + " with k,w of " + k + "," + w);
                        }
                        // we have 4+ collinear points, but don't know the far endpoint, because slopes are just same
                        tmplines[nos++] = new LineSegment(smallest, biggest);
                        doneSlopes[dos] = foundSlope;
                        donePts[dos++] = smallest;
                        doneSlopes[dos] = foundSlope;
                        donePts[dos++] = biggest; // yes both endpoints needed here, not sure why
                        
                    }
                } 
              
                // end of loop through sorted slopes by sorted points
            }
        }
        if (debug) { 
            for (int z = 0; z < dos; z++) {
            System.out.println("done point " + z + " is " + donePts[z]);
          
            }
        }
            // for each point p create a compareTo-sorted array of slopes to p
            
            // run through this array looking for chains of 4 or more lines
            
          
            // take the first point and last point, and add it to return lines
            // if the original sort was *stable*, each of the points should already be in order.
            // (if not, compareTo-sort the chained points)
            


    }
    
    public int numberOfSegments()   {
        return nos;
    }
    public LineSegment[] segments()   {
         
        // pull out nulls, from the padded array
        /*
        LineSegment[] tmplines = new LineSegment[nos];
        for (int i = 0; i < nos; i++) {
            if (lines[i] != null) tmplines[i] = lines[i];
        }
        */
        final LineSegment[] lines = new LineSegment[nos];
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
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

