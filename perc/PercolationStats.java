import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
    
public class PercolationStats {

    private int opencounts = 0;
    private double threshold = 0;
    
    private double[] runs;

    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) throw new IllegalArgumentException("N and T must be positive integers");
        // perform T independent experiments on an N-by-N grid
        runs = new double[T];
        double gridsize = N*N;
        for (int i = 0; i < T; i++) {
            Percolation onerun = new Percolation(N);
            opencounts = 0;
            while (!onerun.percolates()) {
       
                while (true) {
                    // if the .open() fails, we get an infinite loop
                    int newx = StdRandom.uniform(1, N+1);
                    int newy = StdRandom.uniform(1, N+1);
                    
                    //newx= newx+1;
                    //newy= newy+1;
                    
                    if (!onerun.isOpen(newx, newy)) {
                        onerun.open(newx, newy);
                        opencounts += 1;
                        break;
                    }
                } 
                
            }
            threshold = (opencounts/gridsize);
            runs[i] = threshold;
            //StdOut.printf("Percolated at %d %.6f\n",opencounts,threshold);
        }
    }
    
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(runs);
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(runs);
    }
    
    public double confidenceLo()  {
        // low  endpoint of 95% confidence interval
        return StdStats.mean(runs)-(StdStats.stddev(runs)*1.96);
    }
    
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return StdStats.mean(runs)+(StdStats.stddev(runs)*1.96);
    }

    public static void main(String[] args) {
        // test client (described below)
        
        //a main() method that takes two command-line arguments N and T, 
        //performs T independent computational experiments (discussed above) on an N-by-N grid, 
        //and prints the mean, standard deviation, and the 95% confidence interval for the percolation threshold. 
        //Use StdRandom to generate random numbers; use StdStats to compute the sample mean and standard deviation.
       
        int len = Integer.parseInt(args[0]);
        int runs = Integer.parseInt(args[1]);
        if (len <= 0) throw new IllegalArgumentException("PercolationStats requires a positive integer matrix size.");
        if (runs <= 0) throw new IllegalArgumentException("PercolationStats requires a positive integer iterations count.");
       
        PercolationStats sts = new PercolationStats(len, runs);
        //System.out.println("done!");
        StdOut.printf("mean                    = %.2f\n", sts.mean());
        StdOut.printf("stddev                  = %.2f\n", sts.stddev());
        StdOut.printf("95%% confidence interval = %.2f, %.2f\n", sts.confidenceLo(), sts.confidenceHi());
        
        //% java PercolationStats 200 100
        //mean                    = 0.5929934999999997
        //stddev                  = 0.00876990421552567
        //95% confidence interval = 0.5912745987737567, 0.5947124012262428
    }
}
