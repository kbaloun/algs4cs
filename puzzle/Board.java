
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
public final class Board {
    
    private int N = 0; // the dimension
    private int[][] blockarr; // the board array data
    private int ham = 0;
    private int man = 0;

    
    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        blockarr = blocks;
        N = blockarr.length; 
        
        //anything need to be done for the zero block?
        
        // can i check the lengths of the two dimensions, to ensure equal?

    }
    
    
    public int dimension()   {
        // board dimension N
        // foreach or array length?
        return N;
    }
    public int hamming()    {
        // number of blocks out of place
        ham = 0;
        int pos = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // this could be done with mod math, but my brain is tired, pos counting is easy to read
                pos++; //iterate pos first, because block 1 solves to zeroth array position
                if (blockarr[i][j] == 0) continue; // skip zero block
                if (blockarr[i][j] != pos) ham++;
            }
        }
        return ham;
    }
    public int manhattan()   {
        // sum of Manhattan distances between blocks and goal
        man = 0;
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
        return man;

    }
    public boolean isGoal()   {
        // is this board the goal board?
        if (ham == 0) {
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
        if (b2[1][1] != 0) {
            // first point wasn't zero, swap it
            tmp = b2[1][1]; 
            if (b2[1][2] != 0) {
                //first and second points not zero, swap these
                b2[1][1] = b2[1][2];
                b2[1][2] = tmp;
            } else {
                // second point was zero, find next swap spot
                if (N > 2) {
                    // space to swap on row
                    b2[1][1] = b2[1][3];
                    b2[1][3] = tmp;
                } else {
                    // swap into next row
                    b2[1][1] = b2[2][1];
                    b2[2][1] = tmp;
                }
            }       
        } else if (b2[1][2] != 0) {
            // the first position was zero
            tmp = b2[1][2];
            if (N > 2) {
                // space to swap on row
                b2[1][2] = b2[1][3];
                b2[1][3] = tmp;
            } else {
                // swap into next row
                b2[1][2] = b2[2][1];
                b2[2][1] = tmp;
            }
            b2[1][2] = tmp;
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
        // all neighboring boards
        //Add the items you want to a Stack<Board> or Queue<Board> and return that
        MinPQ<Board> bq = new MinPQ();
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
        Board twin = initial.twin();
        StdOut.printf(twin.toString());
        
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