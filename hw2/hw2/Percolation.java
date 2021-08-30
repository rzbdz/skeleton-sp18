package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int ufBottom;
    private int openCount;
    private boolean[][] grids;
    private WeightedQuickUnionUF unionUF;

    private boolean wrongIndex(int r, int c) {
        return (r < 0 || r >= size || c < 0 || c >= size);
    }

    private int trans(int r, int c) {
        return (r + 1) * size + c;
    }

    /* create N-by-N grid, with all sites initially blocked*/
    public Percolation(int N) {
        if (N < 0) {
            throw new IndexOutOfBoundsException("grid size must be positive");
        }
        this.size = N;
        this.ufBottom = (size + 2) * size - 1;
        this.openCount = 0;
        grids = new boolean[size][size];
        unionUF = new WeightedQuickUnionUF(size * (size + 2));
        for (int i = 1; i < size; i++) {
            unionUF.union(0, i);
            unionUF.union(ufBottom, ufBottom - i);
        }
    }

    private static final int[] VONR = {1, 0, -1, 0};
    private static final int[] VONC = {0, 1, 0, -1};

    /* open the site (row, col) if it is not open already*/
    public void open(int row, int col) {
        if (wrongIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (grids[row][col]) {
            return;
        }
        grids[row][col] = true;
        openCount++;
        int tr = trans(row, col);
        for (int i = 0; i < 4; i++) {
            int neir = row + VONR[i];
            int neic = col + VONC[i];
            if (neir < -1 || neir >= size + 1 || neic < 0 || neic >= size) {
                continue;
            }
            if (neir >= 0 && neir < size && !isOpen(neir, neic)) {
                continue;
            }
            unionUF.union(tr, trans(neir, neic));
        }
    }

    /* is the site (row, col) open?*/
    public boolean isOpen(int row, int col) {
        if (wrongIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return this.grids[row][col];
    }

    /* is the site (row, col) full?*/
    public boolean isFull(int row, int col) {
        if (wrongIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return unionUF.connected(0, trans(row, col));
    }

    /* number of open sites*/
    public int numberOfOpenSites() {
        return openCount;
    }

    /* does the system percolate?*/
    public boolean percolates() {
        return unionUF.connected(0, ufBottom);
    }

    /* use for unit testing (not required)*/
    public static void main(String[] args) {
        Percolation p = new Percolation(6);
        for (int i = 0; i < 6; i++) {
            p.open(i, i);
        }
        assert p.isFull(0, 0);
        for (int i = 1; i < 6; i++) {
            assert !p.isFull(i, i);
        }
        p.print();
        assert p.numberOfOpenSites() == 6;
        assert !p.percolates();
    }

    private void print() {
        boolean[][] a = grids;
        int r = a.length;
        int c = a[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(a[i][j] + ",");
            }
            System.out.println();
        }
    }
}
