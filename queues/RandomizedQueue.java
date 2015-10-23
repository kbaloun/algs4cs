import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    //RandQueue will use an array, because random extraction
    //    seems complex if pointers, array counter super convenient.

    private int len = 64;
    private int bufferSize = 0;
    private int chunk = 144; // three 48byte items, under 192 max
    private Item[] q;
    private Object obj; 
    
    private Item[] expandIfNeeded(Item[] q) {
        //check if array is full, and add chunkSize if it is.
        if (q.size() >= q.bufferSize -1) {
            //copy it?  something better?
            int bigger = bufferSize + chunk;
            Item[] newqueue = (Item[]) new Object[bigger];
            for (i = 0; i <= this.size(); i++) newqueue[i] = q[i];
            // is returning the newqueue better than copying it again?
            // if acting on q direcly better than passing it in?
            return newqueue;
        }
    }
    
    private int getRandWholeN (int m) {
        //return a pseudo-random integer between 0 and LenQueue-1
        if (!m > 0) m = this.size() - 1;
        return StdRandom.uniform(0, m);
    }
    public RandomizedQueue()  {
        // construct an empty randomized queue
        
        Item[] q = (Item[]) new Object[len];
    }
    public boolean isEmpty() {
        // is the queue empty?
        (this.size() < 1) ? return false : return true;
    }
    public int size()     {
        // return the number of items on the deque
        int cnt = 0;
        for (i = 0; i < len; i++) {
            if (q[i].isEmpty()) return cnt;
            else cnt += 1;
        }
    public void enqueue(Item item)       {
        // add the item
    }
    public Item dequeue()    {
        // remove and return a random item
    }
    public Item sample()   {
        // return (but do not remove) a random item
    }
    public Iterator<Item> iterator()  {
        // return an independent iterator over items in random order
        
        //The order of two or more iterators to the same randomized queue must be mutually independent; 
        //  each iterator must maintain its own random order. 
    }
    public static void main(String[] args) {
        // unit testing
        
        //a = (Item[]) new Object[2];
        //Item[] a = (Item[]) new Object[1];
    }
    

    //Throw a java.lang.NullPointerException if the client attempts to add a null item
    //throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue
    //throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator
    //throw a java.util.NoSuchElementException if the client calls the next() method in the iterator, if no more items to return. 
}