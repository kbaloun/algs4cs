
/******************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    java PointSET
 *  Dependencies: Point2D and RectHV from algs4
 *  
 *  A class representing a set of points in the unit square.
 *  Implement insert/search/etc API with a red-black BST
 *
 ******************************************************************************/

//import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import java.util.Iterator;
//import java.util.TreeSet

    /**
     * Initializes a new PointSET.
     *
     * @param  NONE
     * 
     */
public class PointSET {

    private SET set;
    private SET inSet; // the results

    public         PointSET()     {
        // construct an empty set of points 
        set = new SET();
    }
    public           boolean isEmpty()   {
        // is the set empty? 
        return set.isEmpty();
    }
    public               int size()  {
        // number of points in the set 
        return set.size();
    }
    public              void insert(Point2D p)  {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException("point to insert must not be null");
        if (!set.contains(p))  set.add(p);
        //TODO check about casting to/from generic or Object
        //what is the Comparable element in the Point?  it already Implements Comparable, fortunately.
    }
    public           boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("point for contains must not be null");
        // does the set contain point p? 
        return set.contains(p);
                //TODO check about casting to/from generic or Object
    }
    public              void draw()    {
        // draw all points to standard draw 
        // test
        // set StdDraw
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.point(0.5, 0.5);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            Point2D pt = (Point2D) it.next();
            pt.draw();
        }

        
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException("rectangle for range must not be null");
        //if (set.isEmpty()) return null;
        inSet = new SET();
        // find the points, put them in the bst to return
        //Interator it = set.iterator();
        for (Iterator it = set.iterator(); it.hasNext();) {
            Point2D pt = (Point2D) it.next();
            if (rect.contains(pt)) {
                inSet.add(pt);
            }
        }
        return inSet;
    }
    public           Point2D nearest(Point2D p)  {
        // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new NullPointerException("point for nearest must not be null");
        if (set.isEmpty()) return null;
        double minDist = 100;
        Point2D closestP = p;
        for (Object op : set){
            Point2D that = (Point2D) op;
            double dist = p.distanceTo(that);
            if (dist < minDist) {
                minDist = dist;
                closestP = that;
            }
        }
        return closestP;
    }

    public static void main(String[] args)   {
        // unit testing of the methods (optional) 
        
        /*
        for (Object op : set){
            Point2D p = (Point2D) op;
            System.out.println(p);
        }
        */
        //draw();

    }
}

