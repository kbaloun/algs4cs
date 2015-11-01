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
    private int maxNos = 10001;
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
        
        // prepare the array
        
        // sort the array.  is this insertion sort? why?
        // all lines will point up and to the right.
        Object[] opnts = new Object[numP];
        for (int i = 0; i < numP; i++) { opnts[i] = (Object) points[i]; }
        Arrays.sort(opnts);
        //for (int i = 0; i < numP; i++) { System.out.println(i + " " + opnts[i]); }
        for (int i = 0; i < numP; i++) { points[i] = (Point) opnts[i]; }
        
        Point[] slopePts = new Point[numP];
        for (int i = 0; i < numP; i++) { slopePts[i] = points[i]; }
        Point[] donePts = new Point[numP]; // to avoid duplicate entries.  can be smaller?
        int dos = 0;

        
        // main search for collinear points
        for (int i = 0; i < numP; i++) {  
            
            // check for duplicate points
            if (i > 0 && points[i].compareTo(points[i-1]) == 0) throw new IllegalArgumentException("duplicate point");
            //System.out.println(i + " " + points[i]);

            // need to sort ALL other forward&backward points by slope to this point p.i
            Arrays.sort(slopePts, points[i].slopeOrder());
            
            Point[] foundPts = new Point[100];
            int fos = 0;
            for (int j = 0; j < numP-1; j++) { 
                //System.out.println("s" + j + " " + slopePts[j] + " " + points[i].slopeTo(slopePts[j]));
                int k = j + 1;
                int w = 0;
                while (points[i].slopeTo(slopePts[j]) == points[i].slopeTo(slopePts[k])) {
                    // find the highest collinear point
                    if (k > numP-2) break; // avoid array out of bounds exception
                    w++;
                    k++;
                }
                if (w > 1) {   // at least 2 equal slopes, cuz back to pt i makes the 3rd.
                    boolean addIt = true;
                    for (int m = 0; m < dos; m++) {
                        // don't add duplicated sub segments
                        if (donePts[m] != null && points[i].slopeTo(slopePts[j]) == points[i].slopeTo(donePts[m])) { 
                            addIt = false; 
                            //System.out.println("skipping duplicate" + slopePts[j] + " already with " + donePts[m]);
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
                        //System.out.println("adding as smallest for" + j + " " + points[i] + " with k,w of " + k + "," + w); 
                        Point biggest = foundPts[fos-1];
                        //System.out.println("adding as biggest for" + j + " " + foundPts[fos-1] + " with k,w of " + k + "," + w);
                        // we have 4+ collinear points, but don't know the far endpoint, because slopes are just same
                        lines[nos++] = new LineSegment(smallest, biggest);
                        donePts[++dos] = smallest;
                        donePts[++dos] = biggest;
                                                /**/
                    }
                } 
              
                // end of loop through sorted slopes by sorted points
            }
        }
/*
            if ((points[i].slopeTo(points[i+1]) == 0) && (points[i].slopeTo(points[i+2]) == 0) 
                    && (points[i].slopeTo(points[i+2]) == 0) && (points[i].slopeTo(points[i+3]) == 0)) {
                // we have 4 collinear points!

                double s1 = points[i].slopeTo(points[j]);
                                double s2 = points[j].slopeTo(points[k]);
                                double s3 = points[j].slopeTo(points[m]);
                                System.out.println(s1 + " " + s2 + " " +s3 + " " + points[i].toString() + " " +
                                    points[j].toString() + " " + points[k].toString() + " " + points[m].toString());

                // any more points, for 5+ collinear points?  works up to 7 due to the bound check
                // first check if space in array to avoid exceptions
                if (i > numP-7 && points[i].slopeTo(points[i+4]) == 0) {
                    if (i > numP-7 && points[i].slopeTo(points[i+5]) == 0) {
                        if (i > numP-7 && points[i].slopeTo(points[i+6]) == 0) {
                            //7pt line
                            lines[nos] = new LineSegment(points[i], points[i+6]);
                        } else {
                            // 6pt line
                            lines[nos] = new LineSegment(points[i], points[i+5]);
                        }
                    } else {
                        // 5pt line
                        lines[nos] = new LineSegment(points[i], points[i+4]);
                    }
                } else {
                    // insert the 4-point segment
                    lines[nos] = new LineSegment(points[i], points[i+3]);  
                }
                nos++;
            }
            */
          
            
            // for each point p create a compareTo-sorted array of slopes to p
            
            // run through this array looking for chains of 4 or more lines
            
          
            // take the first point and last point, and add it to return lines
            // if the original sort was *stable*, each of the points should already be in order.
            // (if not, compareTo-sort the chained points)
            
            
            
          //p.compareTo(
          //Point[] pts = points[i].slopeOrder<points[i]>;
          //Arrays.sort(points, new slopeOrder); //points[i].slopeOrder<points[i]>);
          //Arrays.sort(points, slopeOrder);
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

