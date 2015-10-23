import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item> {
    
    // Deque will be a linked list, since keeping order seems tightest with pointers

    private int len = 64;
    private int firstP;
    private int lastP;
    private Item q;
    
    // create a single queue element node, the item and a pointer
    private Object element =  
    
    public Deque() {
        // construct an empty deque
        
        // initialize an empty first pointer, to the last pointer?
        
    }
    
    public boolean isEmpty()  {
        // is the deque empty?
        (this.size() < 1) ? return false : return true;
    }
    public int size()     {
        // return the number of items on the deque
    }
    public void addFirst(Item item) {
        // add the item to the front
        
        
    }
    public void addLast(Item item)  {
        // add the item to the end
    }
    public Item removeFirst()     {
        // remove and return the item from the front
    }
    public Item removeLast() {
        // remove and return the item from the end
    }
    public Iterator<Item> iterator()   {
        // return an iterator over items in order from front to end
    }
    
    public static void main(String[] args)   {
        // unit testing

    }
    
    //Throw a java.lang.NullPointerException if the client attempts to add a null item
    //throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque
    //throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator
    //throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return. 
}

