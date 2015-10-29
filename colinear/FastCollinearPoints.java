/*************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java. LineSegment.java
 *
 *  The opimized algorithm for finding collinear points
 *
 *************************************************************************/

public class FastCollinearPoints {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Initializes a new line segment.
     *
     * @param  p one endpoint
     * @param  q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *         is <tt>null</tt>
     */
    public FastCollinearPoints(Point[] points)  {
        // finds all line segments containing 4 or more points
    }
    
    public           int numberOfSegments()   {
        // the number of line segments
    }
    public LineSegment[] segments()   {
        // the line segments
    }
}


/*

Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null or if any point in the array is null. Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.

Performance requirement. The order of growth of the running time of your program should be N2 log N in the worst case and it should use space proportional to N plus the number of line segments returned. FastCollinearPoints should work properly even if the input has 5 or more collinear points. 

*/

