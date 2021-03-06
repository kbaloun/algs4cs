/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    
    private double ydiff = 0;
    private double xdiff = 0;
    private double slope = 0;


    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertcal;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        
        ydiff = (that.y - this.y);
        xdiff = (that.x - this.x);
        
        if (ydiff == 0 || xdiff == 0) {
            if (ydiff == 0) {
                if (xdiff == 0) {
                    // what if the points are co-located/collided and in the same position?
                    // "degenerate points" get neg infinity.
                    slope = Double.NEGATIVE_INFINITY;
                }
                else slope = 0; // horizontal line
            }
            if (xdiff == 0) {
                // vertical lines
                if (ydiff != 0) slope = Double.POSITIVE_INFINITY;
            }
        }
        else slope = (ydiff/xdiff);
        return slope;

       /* With integers, this produces a run-time exception. With floating-point numbers, 
        *      1.0/0.0 is positive infinity and -1.0/0.0 is negative infinity. You may also use the constants 
        *      Double.POSITIVE_INFINITY and Double.NEGATIVE_INFINITY. */      
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        int ret = 0;
        if (this.y > that.y) ret = 1;
        else if (this.y < that.y) ret = -1;
        if (this.y == that.y) {
            if (this.x > that.x) ret = 1;
            else if (this.x < that.x) ret = -1;
            //else if (this.x == that.x) ret = 0;
        }
        return ret;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
        //public static final Comparator<Point> BY_SLOPE = new slopeOrder();
    //public Comparator<Point> slopeOrder = new slopeOrder();
    //public Comparator<Point> slopeOrder() { 
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder(); 
    }
    
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            //from the invoking point (x0, y0), the point (x1, y1) is less than the point (x2, y2) 
            //if and only if the slope (y1 ? y0) / (x1 ? x0) is less than the slope (y2 ? y0) / (x2 ? x0).
        if (p1 == null || p2 == null) throw new NullPointerException("can only compare not null points");
        int ret = 0;
        if (slopeTo(p1) < slopeTo(p2)) ret = -1;
        else if (slopeTo(p1) > slopeTo(p2)) ret = 1;
        return ret;
        }
    }



    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
