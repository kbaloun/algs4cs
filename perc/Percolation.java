import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    
    private int[][] xy;
    private int len;
    private int WFsize;
    private int opensites = 0;
    private int finalNode;
    private static int finalcount;
    private WeightedQuickUnionUF myUF;
    private WeightedQuickUnionUF myUFnoFinal;
    
    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        if (N <= 0) throw new IllegalArgumentException("N must be a positive integer");
        xy = new int[N+1][N+1];
        len = N;
        finalNode = len*len+1;
        WFsize = (N*N)+2;
        for (int i=1; i<=N; i++)
            for (int j=1; j<=N; j++)
                xy[i][j] = 0;
        
        // UF is an a long snake, counting each row l-r going down
        myUF = new WeightedQuickUnionUF(WFsize);
        
        // use a copy, to hack prevent of backbleed thru final node
        myUFnoFinal = new WeightedQuickUnionUF(WFsize);
        
    }
   // open site (row i, column j) if it is not open already
   public void open(int i, int j)  {
       if (i < 1 || i > this.len) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j < 1 || j > this.len) throw new IndexOutOfBoundsException("row index j out of bounds");
       xy[i][j] = 1; 
       opensites += 1;
       
       //connect this site and any of 4 open sites around it, in UF
       int p = (i-1)*(len)+j; // the newly opened point
       //StdOut.printf("running is %d %d %d %d %d\n", finalNode,len, p, i, j);
       
       // connect the zero node at top with any opening on the first row
       //if (i==1) myUF.union(0,p);
       if (i == 1) addUFPoint(0,p,true);
       
       if (i > 1 && isOpen(i-1,j)) addUFPoint(p,p-len,true); //above
       if (i== 2 && isFull(i-1,j)) addUFPoint(0,p-len,true); //if above, also to top
       
       if (j > 1 && isOpen(i,j-1)) addUFPoint(p,p-1,true); //left
       if (j < len && isOpen(i,j+1)) addUFPoint(p,p+1,true); //right
       
       if (i < len && isOpen(i+1,j)) addUFPoint(p,p+len,true); //below
       if (i == len-1 && isFull(i+1,j)) addUFPoint(p+len,finalNode,false);
       
       // connect the final node at the bottom with the entire bottom row
       if (i == len) {
           addUFPoint(p,finalNode,false);
           //StdOut.printf("joining to final node is %d %d %d %d %d\n", finalNode,len, p, i, j);
       }
       
       
       //System.out.println("opened ",i,"and ",j,"\n");
       //StdOut.printf("opened %d %d %d\n", i, j, opensites);
   }
   public boolean isOpen(int i, int j)    {
       // is site (row i, column j) open?
       if (i < 1 || i > this.len) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j < 1 || j > this.len) throw new IndexOutOfBoundsException("row index j out of bounds");
       if (xy[i][j] > 0) return true;
       return false;
   }
   public boolean isFull(int i, int j)  {
       // is site (row i, column j) full?
       if (i < 1 || i > this.len) throw new IndexOutOfBoundsException("row index i out of bounds");
       if (j < 1 || j > this.len) throw new IndexOutOfBoundsException("row index j out of bounds");
       
       // first, if it is empty, it isn't
       //if (this.xy[i][j] == 0) return false;
       
       //count this point i,j within the UF int array
       int p = (i-1)*(len)+j; // the newly opened point
       
       // if this p is connected ONLY THRU the last node, return false
       // to prevent bleedthru.  too bad UF can not temp delete
       //if (myUF.connected(p,finalNode)) {
       //    if (!isFull(i-1,j) && !isFull(i,j+1) && !isFull(i,j-1)) {
       //        return false;
       //    }
       //}
       
       //is it connected to the top root of the tree?
       if (myUF.connected(0,p) && myUFnoFinal.connected(0,p)) { 
           //StdOut.printf("FULL is %d %d %d %d\n", len, p, i, j);
           return true;
       } 
       return false;
   }
   
   public boolean percolates()  {
       // does the system percolate
       //StdOut.printf("Percolates is %d %d\n", finalNode, opensites);
       if (myUF.connected(0,finalNode)) {
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
       //perc.open(1,1);
       //if(perc.isOpen(1,1)) System.out.println("yes, open");
       //if(!perc.isOpen(1,2)) System.out.println("yes, not open");
       
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
       //StdOut.printf("Percolated at %d\n",finalcount);   
   }
   
   private void addUFPoint(int p1, int p2, boolean lastnode) {
       myUF.union(p1,p2);
       if(lastnode) myUFnoFinal.union(p1,p2);
   }
}
