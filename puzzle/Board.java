
/******************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board
 *  Dependencies: MinPQ from algs4
 *  
 *  A new board for 8x8 puzzle game
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

    /**
     * Initializes a new Board.
     *
     * @param  blocks  N-N array of integers, defining the initial board
     * 
     */
public class Board {
//public class Board implements Comparable {
// how the heck do i use minPQ without Comparable :-(
    
    private int N = 0; // the dimension
    private int[][] blockarr; // the board array data
    private int ham = -5;  //anything other than -1, 0 or a positive integer
    private int man = -5;
    private boolean alreadyCalculated = false;

    
    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        blockarr = blocks;
        N = blockarr.length; 
        
        //anything need to be done for the zero block?
        
        // can i check the lengths of the two dimensions, to ensure equal?
        if (blockarr.length != blockarr[0].length) {
            System.out.println("error -- the board array isn't a square");
        }

    }
    
    
    public int dimension()   {
        // board dimension N
        // foreach or array length?
        return N;
    }
    public int hamming()    {
        // number of blocks out of place
        if (alreadyCalculated) return ham;
        int pos = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // this could be done with mod math, but my brain is tired, pos counting is easy to read
                pos++; //iterate pos first, because block 1 solves to zeroth array position
                if (blockarr[i][j] == 0) continue; // skip zero block
                if (blockarr[i][j] != pos) ham++;
            }
        }
        alreadyCalculated = true;
        return ham;
    }
    public int manhattan()   {
        // sum of Manhattan distances between blocks and goal
        if (alreadyCalculated) return man;
        int pos = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                pos++; //iterate pos first, because block 1 solves to zeroth array position
                if (blockarr[i][j] != pos) {
                    if (blockarr[i][j] == 0) continue; // skip zero block
                    // calculate vertical and horizontal distance to final position
                    int correctHpos = blockarr[i][j] % N;
                        int correctVpos = (int) Math.ceil(blockarr[i][j] / N);
                    int vert = Math.abs(i - correctVpos);
                    int hort = Math.abs(j - correctHpos);
                    man = man + vert + hort;
                }
            }
        }
        alreadyCalculated = true;
        return man;

    }
    public boolean isGoal()   {
        // is this board the goal board?
        if (ham == 0 && man == 0) {
            return true;
        }else {
            return false;
        }
    }
    public Board twin()  {
        // a board that is obtained by exchanging any pair of blocks
        // use it to determine whether a puzzle is solvable: exactly one of a board and its twin are solvable
        // ensure that neither swapped block is the zero block, pick the first two non-zero blocks
        int[][] b2 = this.blockarr;
        int tmp;
        if (b2[0][0] != 0) {
            // first point wasn't zero, swap it
            tmp = b2[0][0]; 
            if (b2[0][1] != 0) {
                //first and second points not zero, swap these
                b2[0][0] = b2[0][1];
                b2[0][1] = tmp;
            } else {
                // second point was zero, find next swap spot
                if (N > 2) {
                    // space to swap on row
                    b2[0][0] = b2[0][2];
                    b2[0][2] = tmp;
                } else {
                    // swap into next row
                    b2[0][0] = b2[1][0];
                    b2[1][0] = tmp;
                }
            }       
        } else if (b2[0][1] != 0) {
            // the first position was zero
            tmp = b2[0][1];
            if (N > 2) {
                // space to swap on row
                b2[0][1] = b2[0][2];
                b2[0][2] = tmp;
            } else {
                // swap into next row
                b2[0][1] = b2[1][0];
                b2[1][0] = tmp;
            }
        } else {
            System.out.println("can not have two zero points on a board");
        }
        Board nb = new Board(b2);
        return nb;
    }
    public boolean equals(Object y)  {
        // does this board equal y?
        // TODO if dimensions don't match, return FALSE
        // TODO if type (as Board) don't match, return FALSE
        Board yb = (Board) y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (yb.blockarr[i][j] != this.blockarr[i][j]) return false;
            }
        }
        return true;
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards, which include any block that can slide into the zero position
        // note that order of neighbors prefers those down and to the right
        //    by making them later boards in the loop comparison, so they win ties.
        Board[] newBs = new Board[4];
        int[][] newb = new int[N][N];
        int bcnt = 0;
        boolean returnNow = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!returnNow && this.blockarr[i][j] == 0)  {
                    System.out.println("bcnt = " + bcnt);
                    if (i > 0) { 
                        // swap zero with above item
                        for (int m = 0; m < N; m++) {
                            for (int n = 0; n < N; n++) {
                                newb[m][n] = this.blockarr[m][n];
                            }
                        }
                        int t = newb[i-1][j];
                        newb[i-1][j] = 0;
                        newb[i][j] = t;
                        newBs[bcnt] = new Board(newb);
                        bcnt++;
                    }
                    if (j > 0) {
                        // swap zero with left item
                        for (int m = 0; m < N; m++) {
                            for (int n = 0; n < N; n++) {
                                newb[m][n] = this.blockarr[m][n];
                            }
                        }
                        int t = newb[i][j-1];
                        newb[i][j-1] = 0;
                        newb[i][j] = t;
                        newBs[bcnt] = new Board(newb);
                        bcnt++;
                    }
                    if (j < N - 1) {
                        // swap zero with right item
                        for (int m = 0; m < N; m++) {
                            for (int n = 0; n < N; n++) {
                                newb[m][n] = this.blockarr[m][n];
                            }
                        }
                        int t = newb[i][j+1];
                        newb[i][j+1] = 0;
                        newb[i][j] = t;
                        newBs[bcnt] = new Board(newb);
                        bcnt++;
                    }
                    if (i < N - 1) {
                        // swap zero with below item
                        for (int m = 0; m < N; m++) {
                            for (int n = 0; n < N; n++) {
                                newb[m][n] = this.blockarr[m][n];
                            }
                        }
                        int t = newb[i+1][j];
                        newb[i+1][j] = 0;
                        newb[i][j] = t;
                        newBs[bcnt] = new Board(newb);
                    }
                    // there is only 1 zero among the blocks
                   returnNow = true;
                }
            }
        }
        System.out.println("bcnt = " + bcnt);
        //Add the items you want to a Stack<Board> or Queue<Board> and return that
        MinPQ<Board> bq = new MinPQ();
        // can i just insert them into the queue or do I need to
        // sort the 2-4 boards, and enque them in order, with lowest manhatten score to come off first(?)
        for (int z = 0; z < bcnt; z++) {
            if (newBs[bcnt] != null) bq.insert(newBs[bcnt]);
        }
        return bq;
    }
    
    public String toString() {
        // string representation of this board (in the output format specified below)
        
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blockarr[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    //Comparable interface has method public int compareTo(Object o) which returns a negative integer,
    //    zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
    //Read more: http://javarevisited.blogspot.com/2011/06/comparator-and-comparable-in-java.html#ixzz3qqhmkVxx
    // I can't have the public compareTo within Board, the restricted API. :-(


    /*
    public final Comparator<Board> BY_MANHATTAN = new ByManhattan();
    private static class ByManhattan implements Comparator<Board> {
        public int compare(Board b1, Board b2) {
            return b1.manhattan() <= b2.manhattan();
        }
    }
    */
    
    public static void main(String[] args) {
        // unit tests (not graded)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.printf(initial.toString());
        System.out.println("M " + initial.manhattan());
        System.out.println("H " + initial.hamming());
        System.out.println("Dims " + initial.dimension());
        System.out.println("One twin is:");
        Board twin = initial.twin();
        StdOut.printf(twin.toString());
        /*
        MinPQ<Board> rbq = new MinPQ();
        rbq = initial.neighbors();
        while (!rbq.empty()) {
            Board be = rbq.delmin();
            System.out.println("Neighbor " + be.manhattan());
            StdOut.printf(be.toString());
        }
        //if the priority of the search node dequeued from the priority queue decreases, then you know you did something wrong.
        */
        
    }
        
            
    /*
   this fails ... can't modify the public api, can't make exceptions private, and unknown syntax issues
   public final class Board throws InvalidBoardException {
   public class InvalidBoardException extends Exception { 
        public InvalidBoardException(String message){
            super(message);
        }
    }
    throw new InvalidBoardException("can not have two zero points on a board");
    */
}