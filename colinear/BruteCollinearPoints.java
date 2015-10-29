/*************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java, LineSegment.java
 *
 *  Finds Collinear line segments, n^4 time
 *
 *
 *************************************************************************/

public class BruteCollinearPoints {
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
    public BruteCollinearPoints(Point[] points)  {
        // finds all line segments containing 4 points
    }
    
    public           int numberOfSegments()  {
        // the number of line segments
       
    }
    
    public LineSegment[] segments()   {
        // the line segments
    }
}

/* two easy opportunities. First, you can iterate through all combinations of 4 points (N choose 4) instead of all 4 tuples (N^4), saving a factor of 4! = 24. Second, you don't need to consider whether 4 points are collinear if you already know that the first 3 are not collinear; this can save you a factor of N on typical inputs. */

/*
Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null or if any point in the array is null. Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.

Performance requirement. The order of growth of the running time of your program should be N4 in the worst case and it should use space proportional to N plus the number of line segments returned. 
*/

