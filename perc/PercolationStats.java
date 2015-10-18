    import edu.princeton.cs.algs4.StdRandom;
    import edu.princeton.cs.algs4.StdStats;
    import edu.princeton.cs.algs4.WeightedQuickUnionUF;
    import edu.princeton.cs.algs4.StdOut;
    
public class PercolationStats {

    private int runcounts = 0;
    private double summeans = 0;
    private double stddevs = 0;
    private double confLo = 0;
    private double confHi = 0;
    private static double X = 0; /// todo: remove X
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0 ) throw new IllegalArgumentException("N and T must be positive integers");
        // perform T independent experiments on an N-by-N grid
        for (int i=0;i<T;i++) {
            Percolation onerun = new Percolation(N);
        }
    }
    
    public double mean() {
        // sample mean of percolation threshold
        return summeans;
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return stddevs;
    }
    
    public double confidenceLo()  {
        // low  endpoint of 95% confidence interval
        return confLo;
    }
    
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return confHi;
    }

    public static void main(String[] args) {
        // test client (described below)
        System.out.println("done!");
        StdOut.printf("mean                    = %.2f\n", X);
        StdOut.printf("stddev                  = %.2f\n", X);
        StdOut.printf("95% confidence interval = %.2f, %.2f\n", X, X);
    }

//The constructor should throw a java.lang.IllegalArgumentException if either N ² 0 or T ² 0.

//Also, include a main() method that takes two command-line arguments N and T, 
        //performs T independent computational experiments (discussed above) on an N-by-N grid, 
       //and prints the mean, standard deviation, and the 95% confidence interval for the percolation threshold. 
       //Use StdRandom to generate random numbers; use StdStats to compute the sample mean and standard deviation.
       
//% java PercolationStats 200 100
//mean                    = 0.5929934999999997
//stddev                  = 0.00876990421552567
//95% confidence interval = 0.5912745987737567, 0.5947124012262428
}