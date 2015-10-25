import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    
    //private int subsetSize = 1;

    public static void main(String[] args) {
        

        
        // for running time to be linear to the input, must randomize array during
        //   insertion?  and return it in order?
        // OR take a random item while breaking pointers cuz order doesn't matter?
        // OR...  
        // --> first try shuffle them inbound, and Deque.iterator() them out.
        
        //take a command-line integer k
        int subsetSize = Integer.parseInt(args[0]);
        if (subsetSize <= 0) throw new IllegalArgumentException("need a positive integer as first argument for subset size");
        
        //puts them on a lovely new RandomizedQueue
        RandomizedQueue rq = new RandomizedQueue();
        
        //reads in a sequence of N strings from standard input 
        //    using StdIn.readString()
        while (!StdIn.hasNextLine()) {
            String inS = StdIn.readLine();
            rq.enqueue(inS);
            //StdOut.println(line);
        }
        /*
        String ins = new String();
        while (inS = StdIn.readString()) {
            // inS = StdIn.readString();
            rq.enqueue(inS);
        }
        */
        
        //prints out exactly k of them, uniformly at random.
        for (int i = 0; i < subsetSize; i++) {
            System.out.println( rq.dequeue() );
        }     
        
        //Each item from the sequence can be printed out at most once.
        
        //You may assume that 0 ² k ² N, where 
        //  N is the number of string on standard input.
    }


   
/*
% echo A B C D E F G H I | java Subset 3       % echo AA BB BB BB BB BB CC CC | java Subset 8
C                                              BB
G                                              AA
A                                              BB
                                               CC
% echo A B C D E F G H I | java Subset 3       BB
E                                              BB
F                                              CC
G  

% echo AA BB BB BB BB BB CC CC | java Subset 8
(prints all in random order)

*/
    
    }