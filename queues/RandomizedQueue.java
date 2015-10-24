import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    //RandQueue will use an array, because random extraction
    //    seems complex if pointers, array counter super convenient.
    // COULD USE DoubleLinkedList, if this starts looking bad.
    

    private int len = 64;
    private int bufferSize = 0;

    private Item[] q;
    private Object obj; 
    
    // to randomize Queue, this runs in linear time:
    // StdRandom.shuffle()
    

    
    private int getRandWholeN (int m) {
        //return a pseudo-random integer between 0 and LenQueue-1
        if (! (m > 0)) m = this.size() - 1;
        return StdRandom.uniform(0, m);
    }
    
    
    public RandomizedQueue()  {
        // construct an empty randomized queue
        
        Item[] q = (Item[]) new Object[len];
    }
    public boolean isEmpty() {
        // is the queue empty?
        if (this.size() < 1) {
            return false;
        } 
        else return true;
    }
    public int size()     {
        // return the number of items on the queue
        int cnt = 0;
        for (int i = 0; i < len; i++) {
            if (q[i].isEmpty()) return cnt;
            else cnt += 1;
        }
    }
    public void enqueue(Item item)       {
        // add the item
        
        //Throw a java.lang.NullPointerException if the client attempts to add a null item
    }
    public Item dequeue()    {
        // remove and return a random item
        
        
        //throw a java.util.NoSuchElementException if the client attempts to sample 
        //   or dequeue an item from an empty randomized queue
        if (isEmpty()) throw new java.util.NoSuchElementException("can not dequeue an empty queue");
    }
    public Item sample()   {
        // return (but do not remove) a random item
        
        if (isEmpty()) throw new java.util.NoSuchElementException("can not sample an empty queue");
    }
    
    public Iterator<Item> iterator()   {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        // return an independent iterator over items in random order
        
        //The order of two or more iterators to the same randomized queue must be mutually independent; 
        //  each iterator must maintain its own random order. 
        private Node current = firstP;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException("remove is not supported");
        }
        public Item next() {
            
            //throw a java.util.NoSuchElementException if the client calls the next() method
            //   in the iterator and there are no more items to return. 
            if (isEmpty()) throw new java.lang.NoSuchElementException("no more iterating -- all empty");
            
            Item item = current.item;
            // avoid hanging references? how force garbage collection?
            // TODO // delete current.next;
            current = current.nxt;
            return item;
        }
    }
    
    public static void main(String[] args) {
        // unit testing
        
        //a = (Item[]) new Object[2];
        //Item[] a = (Item[]) new Object[1];
    }
    


    
   

  /*
       note: a resizing array does not support constant worst-case time operations in a stack
    // hmmm, i can't resize in chunks :-(
    //  private int chunk = 144; // three 48byte items, under 192 max
    
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
    
    */

}