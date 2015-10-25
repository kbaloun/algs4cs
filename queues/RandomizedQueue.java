import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    //RandQueue will use an array, because random extraction
    //    seems complex if pointers, array counter super convenient.
    // COULD USE DoubleLinkedList, if this starts looking bad.
    
    // TODO only hard part is shrinking and expanding the array for stay in memory bounds
    // **A randomized queue containing N items must use at most 48N + 192 bytes of memory

    private int len = 0;
    private int space = 0;
    private Item[] queue;
    //private Object obj; 
    
    public RandomizedQueue()  {
        // construct an empty randomized queue
        space = 10;
        queue = (Item[]) new Object[space];
        len = 0;
    }
    public boolean isEmpty() {
        // is the queue empty?
        if (len < 1) return false;
        return true;
    }
    public int size()     {
        // return the number of items on the queue
        return len;
    }
    public void enqueue(Item item)       {
        //if the client attempts to add a null item
        if (item == null) throw new java.lang.NullPointerException("can not add null items");
        
        if (len >= space) {
            space = space*2;
            Item[] newq = (Item[]) new Object[space];
            for (int i = 0; i < len; i++) {
                newq[i] = queue[i];
            }
            queue = newq;
        }
        
        // add the item, to the end (or whereever easiest, doesn't matter where)
        ////System.out.println(len);
        ////System.out.println(space);
        ////System.out.println(queue.toString());
        queue[len] = item;
        len += 1;
            
    }
    public Item dequeue()    {
        
        //throw a java.util.NoSuchElementException if the client attempts to sample 
        //   or dequeue an item from an empty randomized queue
        if (len < 1) { 
            throw new java.util.NoSuchElementException("can not dequeue an empty queue");
        }
        
        // remove and return a random item
        // get a random item, return it, and fill in the hole from the end of the array
        ////System.out.println(len);
        int indx = 0;
        if (len > 1) {
            indx = StdRandom.uniform(0, len-1);
        }
        Item ret = queue[indx];
        queue[indx] = queue[len-1];
        len -= 1;

        if (space > len*2) {
            Item[] newq = (Item[]) new Object[space/2];
            for (int i = 0; i < len; i++) {
                newq[i] = queue[i];
            }
            queue = newq;
        }
        
        return ret;
    }
    public Item sample()   {
        // return (but do not remove) a random item, as above
        int indx = StdRandom.uniform(0, len-1);
        
        if (this.isEmpty()) { 
            throw new java.util.NoSuchElementException("can not sample an empty queue");
        }
        return queue[indx];
    }
    
    public Iterator<Item> iterator()   {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        // return an independent iterator over items in random order
        
        //The order of two or more iterators to the same randomized queue must be mutually independent; 
        //  each iterator must maintain its own random order. 
        //private Node current = null; // TODO
        
        // to randomize Queue, this runs in linear time:
        // StdRandom.shuffle()
        
        // TODO make sure no null items are getting shuffled in
        //Item[] shuffledQueue = new Item[len];
        //StdRandom.shuffle(this.queue);
        
        //for (int i : N) {
         //   shuffledQueue[i] = queue[i];
        //}
        //shuffledQueue.StdRandom.shuffle();
        int cnt = 0; 

        public boolean hasNext() {
            return queue[cnt+1] != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException("remove is not supported");
        }
        public Item next() {
            
            //while(this.hasNext()) {
                cnt += 1;
                return queue[cnt];
            //}
           
            //throw a java.util.NoSuchElementException if the client calls the next() method
            //   in the iterator and there are no more items to return. 
            /// TODO if (isEmpty()) throw new java.lang.NoSuchElementException("no more iterating -- all empty");
            
            // TODO Item item = current.item;
            // avoid hanging references? how force garbage collection?
            // TODO // delete current.next;
            /// TODO current = current.nxt;
        }
    }
    
    public static void main(String[] args) {
        // unit testing
        
        //a = (Item[]) new Object[2];
        //Item[] a = (Item[]) new Object[1];
         // initialize an empty first pointer as null?
        RandomizedQueue rq = new RandomizedQueue();
        rq.enqueue("first");
        rq.enqueue("newer first");
        rq.enqueue("the last");
        rq.enqueue("the real end");
        //StdOut.printf("%d",rq.size());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println("all done");
        //StdOut.printf("%d",rq.size());
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