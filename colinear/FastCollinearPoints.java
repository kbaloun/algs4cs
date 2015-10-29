/*************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java. LineSegment.java
 *
 *  The opimized algorithm for finding collinear points
 *
 *************************************************************************/

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
    }
    
    public           int numberOfSegments()   {
        return nos;
    }
    public LineSegment[] segments()   {
        return lines;
    }
}


/*

Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null or if any point in the array is null. Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.

Performance requirement. The order of growth of the running time of your program should be N2 log N in the worst case and it should use space proportional to N plus the number of line segments returned. FastCollinearPoints should work properly even if the input has 5 or more collinear points. 

*/

