
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
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

    /**
     * Solves a Board, or identifies as unsolvable
     *
     * @param  initial  a board in initial position
     * 
     */
public class Solver {
    private final int moves2solve;  
    private final Board initialboard;
    private final MinPQ pq;
    private final MinPQ twinpq;
    private final Stack solq = new Stack();
    private final SearchNode solutionNode; //to hold the solution
    private final SearchNode snInitial; 
    
    public Solver(Board initial)  {
        // find a solution to the initial board (using the A* algorithm)
        
        if (initial == null) throw new java.lang.NullPointerException("solver requires a not-null initial board");
        pq = new MinPQ();
        twinpq = new MinPQ();
        initialboard = initial;
        //compute the priority function in Solver by calling hamming() or manhattan() and adding to it the number of moves.
        
        // create a SearchNode for the initial board, since it is the only one to not come off of the MinPQ
        snInitial = new SearchNode();
        snInitial.searchBoard = initial;
        snInitial.previousNode = null;
        snInitial.priority = initial.manhattan();
        snInitial.moves = 0;
        SearchNode priorNode = snInitial;
        Board current = initial;
        SearchNode finalNode = null;
        int finalmoves = 0;
        
        boolean foundSolution = false;
        boolean foundTwinSolution = false;

        // use one twin, so see whether this or the twin is solvable
        Board aTwin = initial.twin();
        SearchNode snTwin = new SearchNode();
        snTwin.searchBoard = aTwin;
        snTwin.previousNode = null;
        snTwin.priority = aTwin.manhattan();
        snTwin.moves = 0;
        SearchNode twinPriorNode = snTwin;
        Board currenttwin = aTwin;
        
        // return a solved input board
        if (initial.isGoal()) {
            //if (board.isGoal()) {
            pq.insert(snInitial);
            solq.push(initialboard);
            finalmoves = 0;
            foundSolution = true;
            finalNode = snInitial;
        }
        
        //StdOut.println("starting with initial board, dim = " + initial);
        //StdOut.println("starting with twin board, dim = " + aTwin);
        //these are kind of for debugging, as showing an upper bound to the number of steps
        int steps = 0;  // include the initial board in the count
        int twinsteps = 0;
       
        
        //using two synchronized A* searches (e.g., using two priority queues). 
        // get the neighbors, and put them in order on the Queue.
        
        int maxruns = 1000;
        int runs = 0;
        int minpriority = snInitial.priority;
        int minpriorityCap = minpriority + 20;
        
        while (runs < maxruns && !foundSolution && !foundTwinSolution && minpriority < minpriorityCap) {
        //while (!initial.isGoal() && !aTwin.isGoal()) {

            runs++;
            steps++;
            twinsteps++;
            for (Board board : current.neighbors()) {

                SearchNode sn = new SearchNode();
                sn.searchBoard = board;
                sn.previousNode = priorNode;
                sn.priority = board.manhattan();
                sn.moves = steps;
                if (sn.priority < minpriority) minpriority = sn.priority;
                //Board board = sn.searchBoard;
                //StdOut.println("on current board neighbor, of priority "+ sn.priority + " and dim = " + board);
                
                // do not include any board .equal() to the prior position
                if (board.equals(priorNode.searchBoard)) {
                    //TODO this optimization doesn't seem to happen, this line never printed.
                    StdOut.println("skipping insertion of equivalent board , dim = " + board);
                    continue;
                }
                pq.insert(sn);
                
                if (sn.priority == 0) {
                //if (board.isGoal()) {
                    finalmoves = steps;
                    foundSolution = true;
                    finalNode = sn;
                } 
                if (minpriority > minpriorityCap) {
                    System.out.println("stopping due to minpriorityCap at " + minpriority);
                }
            }
            // Same for twin, synchronized
            for (Board twinboard : currenttwin.neighbors()) {
                snTwin = new SearchNode();
                snTwin.searchBoard = twinboard;
                snTwin.previousNode = twinPriorNode;
                snTwin.priority = twinboard.manhattan();
                snTwin.moves = twinsteps;
                //StdOut.println("on twin board neighbor, dim = " + twinboard);
                
                // do not include any board .equal() to the prior position
                if (twinboard.equals(twinPriorNode.searchBoard)) continue;
                twinpq.insert(snTwin);
                
                if (snTwin.priority == 0) {
                //if (board.isGoal()) {
                    //if unsolveable set moves2solve to something other than the initialized value is more clear,
                    // but setting all to zero passes more test cases, since some run out of neighbors, before
                    // the twins run out of neighbors
                    finalmoves = -1;
                    foundTwinSolution = true;
                } 
            }
            //System.out.println("initial's steps " + steps + "twin's steps " + twinsteps);


            // proceed to next lowest manhattan board, if tie choose any, so later one overwrites
            SearchNode sn = (SearchNode) pq.delMin();
            SearchNode sntwin = (SearchNode) twinpq.delMin();
            priorNode = sn;
            twinPriorNode = sntwin;
            current = sn.searchBoard;
            currenttwin = sntwin.searchBoard;

        }
        solutionNode = finalNode;
        moves2solve = finalmoves;
        //System.out.println("solver constructor finished at" + steps + " twin's steps " + twinsteps);
        // note, at this point initial and aTwin contain whatever the final solved state is.
        // one of them should have reached the goal.
     
        //if each neighbor has a higher manhattan score, ARE we done?

    }
    public boolean isSolvable()  {
        // is the initial board solvable?
        if (solutionNode != null) return true;
        //if (solutionNode == null && moves2solve == 0) return false;
        //if (moves2solve > 0) return true;
        //should return true if the initial states is the goal as well TODO
        return false;
    }
    public int moves()   {
        // min number of moves to solve initial board; -1 if unsolvable (-1 per API)
        return moves2solve;
    }
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (this.isSolvable() == false) return null;
        // now i know there exists a solution, so must just return it, no checks needed
        // solq was built in constructor
        SearchNode snode = solutionNode;
        while (snode.previousNode != null) {
                solq.push(snode.searchBoard);
                snode = snode.previousNode;
        }
        if (moves2solve > 0) solq.push(initialboard);
        //solq.push(initialboard);
        return solq;
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
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
            
        }
    }
    
            /*
        Hi, you should add SearchNode to the priority queue (not a simple board). To do this you need to create
        nested class in the Solver class (e.g. with fields Board, previous searchnode, priority and moves) 
        that implements comparable interface. Then  you need to implement compareTo() method that is based on priority
        of a searchnode so when you use MinPQ.delmin() you get searchnode with minimum priority.
        */
    private final class SearchNode implements Comparable {
        private Board searchBoard;
        private SearchNode previousNode;
        private int priority;
        private int moves;
        
        public int compareTo(Object that) {
            SearchNode thatsn = (SearchNode) that;
            return (this.priority - thatsn.priority);
            /*
            Board thatb = thatsn.searchBoard;
            if (thatb.manhattan() < thatb.manhattan()) return -1;
            if (thatb.equals(thatb)) return 0;
            if (thatb.manhattan() >= thatb.manhattan()) return 1;
            //if (this.man < thatb.man) return -1;
            //if (this.man > thatb.man) return 1;
            return -5; // should never see that!
            */
        }
    }
}