import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    
    // Deque will be a linked list, since keeping order seems tightest with pointers
    // based on FIFO queue on p151

    private int len = 0;
    private Node firstP;
    private Node lastP;
    private Node sentinelP;
    private Deque d;
    //private static Item it;
    // nope: non-static class Item cannot be referenced from a static context
    
    // create a single queue element node, the item and a pointer
    private class Node {
        private Item item;
        private Node nxt;
        private Node prv; // dbl linked list fits in 48N, per p201
    }
    
    public Deque() {
        // construct an empty deque
        
        // initialize an empty first pointer as null?
        // TODO? d = new Deque();
        firstP = new Node();
        lastP = new Node();
        sentinelP = new Node();
        
        firstP.nxt = lastP;
        firstP.item = null;
        firstP.prv = sentinelP;
        lastP.nxt = sentinelP;
        lastP.item = null;
        lastP.prv = firstP;
        
        // this keeps a permanently open reference to both end nodes, to avoid null pointers
        sentinelP.nxt = firstP;
        sentinelP.prv = lastP;
        
        assert check();
        
    }
    
       // check internal invariants
    private boolean check() {

        // check a few properties of instance variable 'first'
        if (len < 0) {
            return false;
        }
        if (len == 0) {
            if (firstP != null) return false;
        }
        else if (len == 1) {
            if (firstP == null)      return false;
            if (firstP.nxt != null) return false;
        }
        else {
            if (firstP == null)      return false;
            if (firstP.nxt == null) return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodes = 0;
        for (Node x = firstP; x != null && numberOfNodes <= len; x = x.nxt) {
            numberOfNodes++;
        }
        if (numberOfNodes != len) return false;

        return true;
    }
    
    public boolean isEmpty()  {
        // is the deque empty?
        //if (firstP.item == null || lastP.item == null)
        return len < 1;
        
    }
    public int size()     {
        // return the number of items on the deque
        return len;
    }
    public void addFirst(Item it) {
        //Throw a java.lang.NullPointerException if the client attempts to add a null item
        if (it == null) throw new java.lang.NullPointerException("no adding null items");
        
        //if (len <= 1 && firstP.item == null) 
        if (firstP.item == null) { 
            //System.out.println("in addFirst: firstP item is null");
            firstP.item = it;
        } else {
            if (lastP.item == null) {
                // the first and last nodes exist, the last only is empty.
                lastP.item = it;
            } else {
                Node newSecond = new Node();
                newSecond.prv = firstP;
                newSecond.nxt = firstP.nxt;
                newSecond.item = firstP.item;
                firstP.nxt =  newSecond;
                firstP.item = it;
                firstP.prv = sentinelP;  //just ensuring it is the first node still
            }
        }
        len += 1;
        
        /*  breaks with NullPointerException
        // add the item to the front
        if (firstP.nxt == lastP) { 
            firstP.item = it;
        } else {
            // the 6 lines above
        }
        */
        
    }
    public void addLast(Item it)  {
        //Throw a java.lang.NullPointerException if the client attempts to add a null item
        if (it == null) throw new java.lang.NullPointerException("no adding null items");
        
        //if (len <= 1 && lastP != null && lastP.item == null)  
        if (lastP.item == null)  {
            if (firstP.item == null) {
                firstP.item = it;
            } else {
                lastP.item = it;
            }
        } else {
            Node secondLastP = new Node();
            secondLastP.item = lastP.item;
            secondLastP.prv = lastP.prv;
            secondLastP.nxt = lastP;
            lastP.item = it;
            lastP.prv = secondLastP;
            lastP.nxt = sentinelP; //just ensuring it is the new last node still
        }
        len += 1;        
    }
    
    public Item removeFirst()     {
        
        //throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque
        if (isEmpty()) throw new NoSuchElementException("sorry can't remove from an empty deque");
        
        // remove and return the item from the front
        Item item = firstP.item;
        //if (firstP.nxt == lastP) {
        if (len <=2) {
            //System.out.println("in remove First: firstP.nxt is lastP");
            // if the next node is the last node, must just keep this node alive
            firstP.item = lastP.item;
            lastP.item = null;
            //firstP.nxt = lastP;
            // i think decrementing len effectively nulls it out, and setting it to null makes a nullpointer exception
        } else {
            firstP.prv = null;
            firstP = firstP.nxt;
            firstP.prv = sentinelP;
        }
        len -= 1;
        return item;
    }
    public Item removeLast() {
                
        //throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque
        if (isEmpty()) throw new NoSuchElementException("sorry can't remove from an empty deque");
        
        // remove and return the item from the end
        Item item = null;
        //if (lastP.prv == firstP) { 
        if (len <= 2) {
            //System.out.println("in removeLast: lastP.prv is firstP");
            // if this the last/only node, must return the item from first node.
            if (lastP.item == null) {
                item = firstP.item;
                firstP.item = null;
            } else {
                item = lastP.item;
                lastP.item = null;
            }
        } else {
            // this is the normal case
            item = lastP.item;
            lastP.item = null;
            lastP.nxt = null;
            lastP = lastP.prv;  //how is this lastP.prv null'd out?
            lastP.nxt = sentinelP;
        }
        /*
        if (lastP.prv == firstP) {
            // if the previous node is the first node, must just keep this node alive
            lastP.item = null;
        } else {
            //lastP.prv.nxt = null;  //nullpointerexception
            lastP = lastP.prv;  
            //lastP.nxt = null; //nullpointerexception, is this necessary?
        }
        */
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
            return len > 0;
            //return current.item != null;
        }
        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
        public Item next() {
            
            //throw a java.util.NoSuchElementException if the client calls the next() method
            //   in the iterator and there are no more items to return. 
            //if (isEmpty()) throw new java.util.NoSuchElementException("no more iterating -- all empty");
            if (!hasNext()) throw new NoSuchElementException("no more iterating -- all empty");

            Item item = current.item;
            current = current.nxt;
            return item;

        }
            // avoid hanging references? how force garbage collection?
            // TODO // delete current.next;
        /*
         *     
         * private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
        }
         */
    }
    
    public static void main(String[] args)   {
        // unit testing
        
        Deque deq = new Deque();
        
       
        deq.addFirst("was first");
        deq.addFirst("second");
        deq.addFirst("first first");
        deq.addLast("the last");
        deq.addLast("the real end");       
        StdOut.printf("phase 1 load is %d \n", deq.size());

        // Item it = deq.removeLast();
        // damnit non-static class Item cannot be referenced from a static context
        System.out.println(deq.removeLast());
        System.out.println(deq.removeFirst());
        System.out.println(deq.removeLast());
        System.out.println(deq.removeFirst());
        System.out.println(deq.removeLast());
        System.out.println("done, phase 1");
        StdOut.printf("%d \n", deq.size());
        
         /*
        
        deq.isEmpty();
        deq.isEmpty();
        deq.isEmpty();
        deq.addFirst(3);
        System.out.println(deq.removeLast());
        //deq.removeLast();
        deq.addFirst(5);
        deq.removeLast();
        
        deq.addFirst(0);
        deq.addFirst(1);
         deq.isEmpty();
        deq.removeFirst();
        deq.removeLast();

        System.out.println("done, phase 2\n");
        StdOut.printf("With size %d \n", deq.size());
        */
        
        //System.out.println("First returns zero, should return 6\n");
        deq.addLast(6);
        deq.addFirst(7);
        //StdOut.printf("size is 2? %d \n", deq.size());
        System.out.println(deq.removeFirst()); //     ==> 7
        System.out.println(deq.removeFirst()); //     ==> 0
        System.out.println("done, phase 3\n");
        StdOut.printf("%d \n", deq.size());
        
        for (int i = 1; i < 10; i++) deq.addFirst(i);
        int icnt = 0;
        for (Iterator j = deq.iterator(); j.hasNext(); ) {
            System.out.println(deq.removeFirst());
            icnt++;
            //if (i % 10 == 0) StdOut.printf("queue size is %d \n", rq.size());
        }        
        StdOut.printf("Dequeued %d. Size at end %d \n", icnt, deq.size());
        System.out.println("done, phase 4\n");
        
        //while(!isEmpty()) { return removeFirst(); }

    }

}

