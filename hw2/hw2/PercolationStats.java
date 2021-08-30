package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    final private double[] thresholds;
    final private PercolationFactory pf;
    final private int size;
    final private int times;
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(3, 4000, new PercolationFactory());
        System.out.println(ps.confidenceHigh());
        System.out.println(ps.confidenceLow());
    }

    /* perform T independent experiments on an N-by-N grid*/
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0 || T < 0) {
            throw new IllegalArgumentException();
        }
        thresholds = new double[T];
        this.pf = pf;
        this.size = N;
        this.times = T;
        for (int i = 0; i < T; i++) {
            thresholds[i] = simulate();
        }
    }

    private double simulate() {
        Percolation p = pf.make(size);
        while (true) {
            int r = StdRandom.uniform(size);
            int c = StdRandom.uniform(size);
            if (p.isOpen(r, c)) {
                continue;
            }  // filter open sites.
            p.open(r, c);
            if (p.percolates()) {
                return (double) p.numberOfOpenSites() / (double) (size * size);
            }
        }
    }

    /* sample mean of percolation threshold*/
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /* sample standard deviation of percolation threshold*/
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /* low endpoint of 95% confidence interval*/
    public double confidenceLow() {
        return mean() - 1.96 * (stddev() / Math.sqrt(times));
    }

    /* high endpoint of 95% confidence interval*/
    public double confidenceHigh() {
        return mean() + 1.96 * (stddev() / Math.sqrt(times));
    }
}
