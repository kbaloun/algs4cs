import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    
    // Deque will be a linked list, since keeping order seems tightest with pointers
    // based on FIFO queue on p151

    private int len = 0;
    private Node firstP;
    private Node lastP;
    private Deque d;
    //private static Item it;
    // nope: non-static class Item cannot be referenced from a static context
    
    // create a single queue element node, the item and a pointer
    private class Node {
        Item item;
        Node nxt;
        Node prv; // dbl linked list fits in 48N, per p201
    }
    
    public Deque() {
        // construct an empty deque
        
        // initialize an empty first pointer as null?
        // TODO? d = new Deque();
        Node firstP = new Node();
        Node lastP = new Node();
        firstP.nxt = lastP;
        firstP.item = null;
        firstP.prv = null;
        lastP.prv = firstP;
        lastP.item = null;
        lastP.nxt = null;
        
        //len is still zero
        
    }
    
    public boolean isEmpty()  {
        // is the deque empty?
        if (firstP.item == null || lastP.item == null)
            return true;
        else return false;
        
    }
    public int size()     {
        // return the number of items on the deque
        return len;
    }
    public void addFirst(Item item) {
        //Throw a java.lang.NullPointerException if the client attempts to add a null item
        if (item == null) throw new java.lang.NullPointerException("no adding null items");
        
        // add the item to the front
        if (firstP == null) { 
            firstP = item;
        } else {
            Node oldFirst = firstP; 
            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.nxt = oldFirst;
            newFirst.prv = null; //just ensuring it is the new first node
            firstP = newFirst;
        }
        len += 1;
    }
    public void addLast(Item item)  {
        //Throw a java.lang.NullPointerException if the client attempts to add a null item
        if (item == null) throw new java.lang.NullPointerException("no adding null items");
        
        // add the item to the end
        if (lastP.item == null) { 
            lastP.item = item;
        } else {
            Node oldLast = lastP; 
            lastP = new Node();
            lastP.item = item;
            lastP.prv = oldLast;
            lastP.nxt = null; //just ensuring it is the new last node
        }
        len += 1;
    }
    public Item removeFirst()     {
        
        //throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque
        if (isEmpty()) throw new java.util.NoSuchElementException("sorry can't remove from an empty deque");
        
        // remove and return the item from the front
        Item item = firstP.item;
        if (firstP.nxt.item == null) {
            // if the next node is the last node, must just keep this node alive
            firstP.item = null;
            firstP.nxt = lastP;
        } else {
            firstP = firstP.nxt;
        }
        len -= 1;
        return item;
    }
    public Item removeLast() {
                
        //throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque
        if (isEmpty()) throw new java.util.NoSuchElementException("sorry can't remove from an empty deque");
        
        // remove and return the item from the end
        Item item = lastP.item;
        if (lastP.prv.item == null) {
            // if the previous node is the first node, must just keep this node alive
            lastP.item = null;
            lastP.prv = firstP;
        } else {
            lastP = lastP.prv;            
        }
        lastP.nxt = null;
        len -= 1;
        return item;
        
        // how find new last item? do i need a double linked list?
        // p146 advocates against DLlist, but with 48N i have space for 2nd int pointer, and 
        // says traverse from front is only other option and takes 
        // "time proportional to N", which is "linear" not constant time :-(
    }
    public Iterator<Item> iterator()   {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        // return an iterator over items in order from front to end
        private Node current = firstP;
        public boolean hasNext() {
            return current.item != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException("remove is not supported");
        }
        public Item next() {
            
            //throw a java.util.NoSuchElementException if the client calls the next() method
            //   in the iterator and there are no more items to return. 
            if (isEmpty()) throw new java.util.NoSuchElementException("no more iterating -- all empty");
            
            Item item = current.item;
            // avoid hanging references? how force garbage collection?
            // TODO // delete current.next;
            current = current.nxt;
            return item;
        }
    }
    
    public static void main(String[] args)   {
        // unit testing
        
        Deque deq = new Deque();
        String it = "first";
        deq.addFirst(it);
        /*
        //deq.addFirst("newer first");
        it = "newer first";
        deq.addFirst(it);
        deq.addLast("the last");
        deq.addLast("the real end");
        StdOut.printf("%d",deq.size());
        deq.removeLast();
        deq.removeFirst();
        deq.removeLast();
        deq.removeFirst();
        */
        System.out.println("all done");
        StdOut.printf("%d",deq.size());
        
        
        //while(!isEmpty()) { return removeFirst(); }

    }

}

