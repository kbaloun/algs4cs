import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    
    // Deque will be a linked list, since keeping order seems tightest with pointers
    // based on FIFO queue on p151

    private int len;
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
        
        len = 0;
        firstP = new Node();
        lastP = new Node();
        sentinelP = new Node();
        firstP.nxt = lastP;
        firstP.item = null;
        firstP.prv = sentinelP;
        lastP.prv = firstP;
        lastP.item = null;
        lastP.nxt = sentinelP;
        //len is still zero
       
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
        
        if (firstP.item == null || lastP.item == null) {
            if (firstP.item == null) {
                // first spot is empty, it gets it.
                firstP.item = it;
            } else {
                // first spot full, so 2nd spot is empty.  slide it over, then fill.
                lastP.item = firstP.item;
                firstP.item = it;
            }
        } else {
            Node second = new Node();
            second.item = firstP.item;
            second.nxt = firstP.nxt;
            second.prv = firstP;
            (firstP.nxt).prv = second;
            firstP.item = it;
            firstP.nxt = second;
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
        
        if (firstP.item == null || lastP.item == null) {
            if (lastP.item == null) {
                if (firstP.item == null) {
                    // both first and last empty, so only fill first
                    firstP.item = it;
                } else {
                    // only last is empty, so fill it.
                    lastP.item = it;
                }
            } else {
                // last item is full
                if (firstP.item == null) {
                    // only first was empty, so shift to it and fill both
                    firstP.item = lastP.item;
                    lastP.item = it;
                } else {
                    //both first and last are full so should never see this
                    System.out.println("bug in addLast(). go look.");
                }
            }
        } else {
            Node secondL = new Node();
            secondL.item = lastP.item;
            secondL.prv = lastP.prv;
            secondL.nxt = lastP;
            (lastP.prv).nxt = secondL;
            lastP.item = it;
            lastP.prv = secondL;
        }
        len += 1;        
    }
    
    public Item removeFirst()     {
        
        //throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque
        if (isEmpty()) throw new NoSuchElementException("sorry can't remove from an empty deque");
        
        // remove and return the item from the front
        Item item = null;
        if (firstP.nxt == lastP) {
            if (lastP.item == null) {
                item = firstP.item;
                firstP.item = null;
            } else {
                // if the last slot isn't empty, slide it to the first position
                item = firstP.item;
                firstP.item = lastP.item;
                lastP.item = null;
            }
        } else {
            item = firstP.item;
            firstP.item = null;
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
        if (lastP.prv == firstP) { 
            // if this the last/only node, must return the item from first node.
            if (lastP.item == null) {
                if (firstP.item == null) {
                    // we must never be here, the array is empty!
                    System.out.println("bug in removeLast(). go look.");
                } else {
                    item = firstP.item;
                    firstP.item = null;
                }
            } else {
                item = lastP.item;
                lastP.item = null;
            }
        } else {
            // the normal longer case
            item = lastP.item;
            lastP.item = null;
            lastP = lastP.prv;
            lastP.nxt = sentinelP;
        }
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
        private Item an = null;
        private Node current = firstP;
       
        public boolean hasNext() {
            //return current != null;
            return (current.nxt != null && current != lastP.nxt);
            //return ((current.nxt).item != null);
            //return current.item != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException("remove is not supported");
        }
        public Item next() {
            
            //throw a java.util.NoSuchElementException if the client calls the next() method
            //   in the iterator and there are no more items to return. 
            //if (isEmpty()) throw new java.util.NoSuchElementException("no more iterating -- all empty");
            if (!hasNext()) throw new NoSuchElementException("no more iterating -- all empty");
            an = current.item;
            current = current.nxt;
            return an;

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

        System.out.println("start, phase 0");
        //deq.addFirst(0);
        //deq.addFirst(1);
        deq.addFirst(2);
        deq.addFirst(1);
        System.out.println(deq.removeFirst()); // not    ==> 0
        System.out.println(deq.removeFirst());
        System.out.println("done, phase 0\n");



        deq.addFirst("was first");
        deq.addFirst("second");
        deq.addFirst("first first");
        deq.addLast("the last");
        deq.addLast("the real end");
        StdOut.printf("phase 1 load is %d \n", deq.size());

        System.out.println(deq.removeLast());
        System.out.println(deq.removeFirst());
        System.out.println(deq.removeLast());
        System.out.println(deq.removeFirst());
        System.out.println(deq.removeLast());
        System.out.println("done, phase 1\n");
        StdOut.printf("%d \n", deq.size());
        
        System.out.println("start, phase 2");
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

        System.out.println("done, phase 2");
        StdOut.printf("%d \n", deq.size());

        //while(!isEmpty()) { return removeFirst(); }
        for (int i = 1; i <= 1000; i++) deq.addFirst(i);
        StdOut.printf("%d \n", deq.size());
        int icnt = 0;
        for (Iterator j = deq.iterator(); j.hasNext(); ) {
            //System.out.println(deq.removeFirst());
            //deq.removeFirst();
            icnt++;
            j.next();

            if (icnt % 100 == 0) StdOut.printf("queue size is %d \n", deq.size());
        }
        StdOut.printf("Dequeued %d. Size at end %d \n", icnt, deq.size());
        for (int i = 1; i <= 1000; i++) deq.removeFirst();
        System.out.println("done, phase 4\n");
        
        StdOut.printf("phase 5, size at start: %d \n", deq.size());
         deq.addLast(0);
         deq.isEmpty();
         deq.addLast(2);
         System.out.println(deq.removeFirst());  //  ==> 0
         deq.addFirst(4);
         deq.isEmpty();
         deq.size();
         deq.addFirst(7);
         System.out.println(deq.removeLast());  //    ==> 2
         deq.size();
         deq.size();
         System.out.println(deq.removeLast());  //   ==> 7
         System.out.println(deq.removeLast());  
         System.out.println("phase 5 done. should see: 0 2 4 , not 0 2 7 \n");
    }

}

