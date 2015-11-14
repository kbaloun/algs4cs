
/******************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    java KdTree
 *  Dependencies: Point2D and RectHV from algs4
 *  
 *  A class representing a set of points in the unit square.
 *  Implement (same) insert/search/etc API with a 2d-tree
 *
 ******************************************************************************/

//import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import java.util.Iterator;
//import java.util.TreeSet

    /**
     * Initializes a new KdTree.
     *
     * @param  NONE
     * 
     */
//mutable
public class KdTree {
    
    private SET kd; // the KDtree is still held in a BST.
    private SET inSet; // the results
    
    public         KdTree() {                              
// construct an empty set of points 
        kd = new SET();
    }
    public           boolean isEmpty()   {
        // is the set empty? 
        return kd.isEmpty();
    }
    public               int size()  {
        // number of points in the set 
        return kd.size();
    }
    public              void insert(Point2D p)     {
        if (p == null) throw new NullPointerException("point to insert must not be null");
        // add the point to the set (if it is not already in the set)
        if (!kd.contains(p))  kd.add(p);
        //TODO check about casting to/from generic or Object
        //what is the Comparable element in the Point?  it already Implements Comparable, fortunately.
    }
    public           boolean contains(Point2D p)   {
        if (p == null) throw new NullPointerException("point for contains must not be null");
        // does the set contain point p? 
        return kd.contains(p);
    }
    public              void draw()    {
        // draw all points to standard draw 
    }
    public Iterable<Point2D> range(RectHV rect)  {
        if (rect == null) throw new NullPointerException("rectangle for range must not be null");
        // all points that are inside the rectangle
        inSet = new SET();
        for (Iterator it = kd.iterator(); it.hasNext(); ) {
            Point2D pt = (Point2D) it.next();
            if (rect.contains(pt)) {
                inSet.add(pt);
            }
        }
        return inSet;
    }
    public           Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("point for nearest must not be null");
        // a nearest neighbor in the set to point p; null if the set is empty 
        return null;
    }

    public static void main(String[] args)  {
        // unit testing of the methods (optional) 
    }
}

