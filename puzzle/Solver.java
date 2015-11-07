
/******************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver
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
     * Solves a Board, or identifies as unsolvable
     *
     * @param  initial  a board in initial position
     * 
     */
public class Solver {
    private int moves2solve = 0;
    private MinPQ pq;
    
    public Solver(Board initial)  {
        // find a solution to the initial board (using the A* algorithm)
        pq = new MinPQ();
        //compute the priority function in Solver by calling hamming() or manhattan() and adding to it the number of moves.
        
        // use one twin, so see whether this or the twin is solvable
    }
    public boolean isSolvable()  {
        // is the initial board solvable?
        return false;
    }
    public int moves()   {
        // min number of moves to solve initial board; -1 if unsolvable
        return moves2solve;
    }
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        
        //Add the items you want to a Stack<Board> or Queue<Board> and return that
        MinPQ<Board> bq = new MinPQ();
        return bq;
    }
    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
            
        }
    }
}