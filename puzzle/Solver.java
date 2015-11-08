
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
    private MinPQ twinpq;
    private MinPQ spq; //to hold the solution
    
    public Solver(Board initial)  {
        // find a solution to the initial board (using the A* algorithm)
        pq = new MinPQ();
        twinpq = new MinPQ();
        //compute the priority function in Solver by calling hamming() or manhattan() and adding to it the number of moves.
        
        Board priorState = initial;
        Board firstState = initial;
        // use one twin, so see whether this or the twin is solvable
        Board aTwin = initial.twin();
        Board twinPriorState = aTwin;
        
        //these are kind of for debugging, as showing an upper bound to the number of steps
        int steps = 0;
        int twinsteps = 0;
       
        
        //using two synchronized A* searches (e.g., using two priority queues). 
        // get the neighbors, and put them in order on the Queue.
        
        int test = 0;
        while (test < 5 && !initial.isGoal() && !aTwin.isGoal()) {

            test++;
            for (Board board : initial.neighbors()) {
                StdOut.println("the initial board neighbor, dim = " + board);
                int minMan = 100000; //any number higher than an supported board manhattan score
                
                // do not include any board .equal() to the prior position
                if (board.equals(priorState)) continue;
                pq.insert(board);
                
                // proceed to next lowest manhattan board, if tie choose any, so later one overwrites
                priorState = initial;
                if (board.manhattan() <= minMan) {
                    initial = board;
                }
                steps++;
            }
            // Same for twin, synchronized
            for (Board twinboard : aTwin.neighbors()) {
                StdOut.println("the twin board neighbor, dim = " + twinboard);
                int twinMinMan = 100000;
                
                // do not include any board .equal() to the prior position
                if (twinboard.equals(twinPriorState)) continue;
                twinpq.insert(twinboard);
                
                // proceed to next lowest manhattan board, if tie choose any, so later one overwrites
                twinPriorState = aTwin;
                if (twinboard.manhattan() <= twinMinMan) {
                    aTwin = twinboard;
                }
                twinsteps++;
            }
            System.out.println("initial's steps " + steps + "twin's steps " + twinsteps);
        }
        // note, at this point initial and aTwin contain whatever the final solved state is.
        // one of them should have reached the goal.
     
        //if each neighbor has a higher manhattan score, ARE we done?
        //if unsolveable set moves2solve to -1
        if (false && aTwin.isGoal()) {
            moves2solve = -1;
            spq = null; //just to be sure
        } else if (initial.isGoal()) {
            //record the solution sequence

            //Add the items you want to a Stack<Board> or Queue<Board> and return that
            System.out.println("starting to solve");
            spq = new MinPQ(); 
            int solCnt = 0;
            final MinPQ<Board> pqCopy = pq;
            //Board[] solutionPath = new Board[moves2solve];
            while (!pqCopy.isEmpty()) {
                //if (pqCopy.min()
                // TODO something about continue;
                //Board nextStep = new Board(pqCopy.delMin());
                Board bstep = pqCopy.delMin();
                spq.insert(bstep);
                if (bstep.equals(firstState)) break;
            }
            if (solCnt != spq.size()) System.out.println("error -- unexpected MinPQ result.");
            moves2solve = spq.size();
        } else {
            System.out.println("error -- exactly ONE OF twin OR the initial state must be solvable.");    
        }
                // ?
    }
    public boolean isSolvable()  {
        // is the initial board solvable?
        if (moves2solve == -1) return false;
        if (moves2solve > 0) return true;
        //should return true if the initial states is the goal as well TODO
        return false;
    }
    public int moves()   {
        // min number of moves to solve initial board; -1 if unsolvable
        return moves2solve;
    }
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (moves2solve == -1) return null;
        // now i know there exists a solution, so must just return it, no checks needed
        // spq was built in constructor
        return spq;
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
    
            /*
        Hi, you should add SearchNode to the priority queue (not a simple board). To do this you need to create
        nested class in the Solver class (e.g. with fields Board, previous searchnode, priority and moves) 
        that implements comparable interface. Then  you need to implement compareTo() method that is based on priority
        of a searchnode so when you use MinPQ.delmin() you get searchnode with minimum priority.
        */
    private class SearchNode implements Comparable {
        Board searchBoard;
        SearchNode previousNode;
        int priority;
        int moves;
        
        public int compareTo(Object that) {
            Board thatb = (Board) that;
            if (searchBoard.manhattan() < thatb.manhattan()) return -1;
            if (searchBoard.equals(thatb)) return 0;
            if (searchBoard.manhattan() >= thatb.manhattan()) return 1;
            //if (this.man < thatb.man) return -1;
            //if (this.man > thatb.man) return 1;
            return -5; // should never see that!
        }
    }
}