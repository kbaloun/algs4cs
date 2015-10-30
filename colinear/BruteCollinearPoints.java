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
    //private int maxPs = 10000;
    //private String[] pStr = new String[1000];
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
        for (Point p : points) {
          if (p == null) throw new NullPointerException("null point within input file");
          numP += 1;   
          //System.out.println(p.toString());
          //if (pStr[p.toString()] == 1) throw new IllegalArgumentException("a duplicate point is loaded");
          //else pStr[p.toString()] = 1;
        }
        //System.out.println(numP);
        
        // compare each point only with forward points, so that no duplicate orders or permutations.
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
                                
                                /*
                                // find min and max points of the 4 set
                                int[] score = new int[4];
                                score[i] = 0;
                                score[j] = 0;
                                score[k] = 0;
                                score[m] = 0;
                                if ((points[i].compareTo(points[j])) > 0) { 
                                    score[j] += 1; 
                                    if (points[j].compareTo(points[k]) > 0) {
                                        score[k] += 2;
                                        if ((points[k].compareTo(points[m])) > 0) {
                                            score[m] += 3;
                                        } else {
                                            score[k] += 1;
                                        }
                                    } else {
                                        score[j] += 1;
                                        if ((points[j].compareTo(points[m])) > 0) score[m] += 1;
                                        else score[j] += 1;
                                    }
                                } else {
                                    iScore += 1;
                                    if (points[i].compareTo(points[k]) > 0) {  
                                        score[j] += 1;
                                        if (points[i].compareTo(points[k]) > 0) {
                                            score[k] += 1;
                                        } else {
                                            score[i] += 1;
                                        }
                                    } else score[i] += 1;
                                }
                                */

                                
                                lines[nos] = new LineSegment(points[i], points[m]);
                                nos++;
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


        //lines[0] = new LineSegment(points[2],points[3]);
        //lines[1] = new LineSegment(points[3],points[4]);
        //nos += 2;
        
        LineSegment[] tmplines = new LineSegment[nos];
        for (int i = 0; i < nos; i++) {
            if (lines[i] != null) tmplines[i] = lines[i];
        }
        lines = new LineSegment[nos];
        for (int i = 0; i < nos; i++) {
            if (tmplines[i] != null) lines[i] = tmplines[i];
        }
        /*
        //for (Point p : points) {
            for (int i = 0; i < numP-2; i++) {
                
                lines[i] = new LineSegment(points[i],points[i+1]);
                numL++;
            }     
        }
        
    */
        //System.out.println("done");    
        //System.out.println(nos);

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

