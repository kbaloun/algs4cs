import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    
    private int[][] xy;
    private int len;
    private int WFsize;
    private int opensites = 0;
    public static int finalcount;
    private WeightedQuickUnionUF myUF;
    
    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        if (N <= 0) throw new IllegalArgumentException("N must be a positive integer");
        xy = new int[N][N];
        len = N;
        WFsize = (N*N)+2;
        for (int i=0; i<N; i++)
            for (int j=0; j<N; j++)
                xy[i][j] = 0;
        
        // UF is an a long snake, counting each row l-r going down
        myUF = new WeightedQuickUnionUF(WFsize);
        // connect the zero node at top with the entire first row
        for (int i=0; i<N; i++) myUF.union(0,i+1);
        // connect the final node at the bottom with the entire bottom row
        int firstInBottomRow = len*(len-1);
        int finalNode = len*len+1;
        for (int i=0; i<N; i++) myUF.union(firstInBottomRow+i+1,finalNode);
        
    }
   // open site (row i, column j) if it is not open already
   public void open(int i, int j)  {
       if (i < 0 || i > this.len) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j < 0 || j > this.len) throw new IndexOutOfBoundsException("row index j out of bounds");
       xy[i][j] = 1; 
       opensites += 1;
       
       //connect this site and any of 4 open sites around it, in UF
       int p = i*(len-1)+j+1; // the newly opened point
       
       if (i > 0 && isOpen(i-1,j)) myUF.union(p,p-len); //above
       if (j > 0 && isOpen(i,j-1)) myUF.union(p,p-1); //left
       if (j < len-1 && isOpen(i,j+1)) myUF.union(p,p+1); //right
       if (i < len-1 && isOpen(i+1,j)) myUF.union(p,p+len); //below
       
       
       //System.out.println("opened ",i,"and ",j,"\n");
       //StdOut.printf("opened %d %d %d\n", i, j, opensites);
   }
   public boolean isOpen(int i, int j)    {
       // is site (row i, column j) open?
       if (xy[i][j] > 0) return true;
       return false;
   }
   public boolean isFull(int i, int j)  {
       // is site (row i, column j) full?
       // first, if it is empty, it isn't
       if (xy[i][j] == 0) return false;
       
       //count this point i,j within the UF int array
       int p = i*(len-1)+j+1; // the newly opened point
       
       //is it connected to the top root of the tree?
       if (myUF.connected(0,len*len+1)) { 
           return true;
       } 
       return false;
   }
   
   public boolean percolates()  {
       // does the system percolate
       if (myUF.connected(0,len*len+1)) {
           finalcount = opensites; 
           return true;
       } 
       //if (opensites > 1000) return true;
       return false;
   }

   public static void main(String[] args)  { // test client (optional)
       //System.out.println("Now percolate");
       int len = Integer.parseInt(args[0]);
       if (len <= 0) throw new IllegalArgumentException("Percolation takes one positive integer argument.");
       
       Percolation perc = new Percolation(len);
       //perc.open(0,1);
       //if(perc.isOpen(0,1)) System.out.println("yes, open");
       //if(!perc.isOpen(0,2)) System.out.println("yes, not open");
       
       while (!perc.percolates()) {
       
           while(true) {
               // if the .open() fails, we get an infinite loop
               int newx = StdRandom.uniform(len);
               int newy = StdRandom.uniform(len);
               
               if (!perc.isOpen(newx,newy)) {
                   perc.open(newx,newy);
                   break;
               }
           } 

       }
       StdOut.printf("Percolated at %d\n",finalcount);
       
//The constructor should throw a java.lang.IllegalArgumentException if N ² 0. 
       //if any argument to open(), isOpen(), or isFull() is outside its prescribed range
       
       //a constant number of calls to the union-find methods union(), find(), connected(), and count().
       
        //Initialize all sites to be blocked.

//Repeat the following until the system percolates:

    //Choose a site (row i, column j) uniformly at random among all blocked sites.

    //Open the site (row i, column j). 
       
       //use the weighted quick-union algorithm from WeightedQuickUnionUF
       //only call library functions in StdIn, StdOut, StdRandom, StdStats, WeightedQuickUnionUF and java.lang

//The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold. 
   
   }
   
}
