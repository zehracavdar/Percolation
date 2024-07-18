import java.awt.*;

public class Percolation {

    private boolean[][] grid;
    private int gridSize;
    private int gridSquare;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionFind QUFind;

    private WeightedQuickUnionFind trackline;


    // initializing the code
    public Percolation(int n) {
        this.gridSize = n;
        this.gridSquare = n * n;
        this.grid = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }

        int quickUnionSize = gridSquare + 2;
        this.QUFind = new WeightedQuickUnionFind(quickUnionSize);
        this.trackline = new WeightedQuickUnionFind(quickUnionSize - 1);
        this.virtualTop = 0;
        this.virtualBottom = quickUnionSize - 1;
    }


     //opening a site in the grid and union them in case of necessity

    public void open(int row, int column) {

        if (isOpen(row, column))
            return;

        grid[row][column] = true;

        // if the site is at the top, connects it to the virtual top.
        if (row == 0) {
            QUFind.union(virtualTop, convertToIndex(row, column));

            trackline.union(virtualTop, convertToIndex(row, column));
        }

        // if the site is at the bottom, connects it to the virtual bottom.
        if (row == gridSize - 1) {
            QUFind.union(virtualBottom, convertToIndex(row, column));
        }

        // checking the left site
        if (column - 1 >= 0 && isOpen(row, column - 1)) {
            QUFind.union(convertToIndex(row, column), convertToIndex(row, column - 1));

            trackline.union(convertToIndex(row, column), convertToIndex(row, column - 1));
        }

        // checking the right site
        if (column + 1 < gridSize && isOpen(row, column + 1)) {
            QUFind.union(convertToIndex(row, column), convertToIndex(row, column + 1));

            trackline.union(convertToIndex(row, column), convertToIndex(row, column + 1));
        }

        // checking the site above
        if (row - 1 >= 0 && isOpen(row - 1, column)) {
            QUFind.union(convertToIndex(row, column), convertToIndex(row - 1, column));

            trackline.union(convertToIndex(row, column), convertToIndex(row - 1, column));
        }

        // checking the site below
        if (row + 1 < gridSize && isOpen(row + 1, column)) {
            QUFind.union(convertToIndex(row, column), convertToIndex(row + 1, column));

            trackline.union(convertToIndex(row, column), convertToIndex(row + 1, column));
        }
    }

    public void openAllSites(double p) {

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                @SuppressWarnings("deprecation")
                double randomValue = StdRandom.uniform();
                if (randomValue < p) {
                    open(row, col);
                }
            }
        }

    }


     //checking if the system percolates

    public boolean percolationCheck() {
        return QUFind.find(virtualTop) == QUFind.find(virtualBottom);
    }


     // displaying the grid using StdDraw

    public void displayGrid() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, gridSize);
        StdDraw.setYscale(0, gridSize);
        StdDraw.filledSquare(gridSize / 2.0, gridSize / 2.0, gridSize / 2.0);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    if (isConnectedWithTop(row, col)) {
                        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                    }
                    StdDraw.filledSquare(col + 0.5, gridSize - row - 0.5, 0.45);
                }
            }
        }

        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        StdDraw.textLeft(0.0, 0.5, "Does is percolate?: " + percolationCheck());
    }


     //converting the 2D matrix array to 1D array

    private int convertToIndex(int row, int column) {

        return (column + 1) + gridSize * row;
    }


     //checking if the site is open by checking its boolean value in the grid.

    private boolean isOpen(int row, int column) {
        return grid[row][column];
    }


     // checking if the site is connected with top by checking if they have the same root
     //this method is used to determine which sites are connected with top showing
     //that the liquid would percolate to the connected sites.

    private boolean isConnectedWithTop(int row, int column) {
        return trackline.find(convertToIndex(row, column)) == trackline.find(virtualTop);
    }


    //  MAIN METHOD

    public static void main(String[] args) {

        //in main method, here written 10 cases. we will see if they percolate
        //with given open sites.
        //they will come to screen respectively below

        // TestCase1
        Percolation p1 = new Percolation(10);
        p1.open(0, 0);
        p1.open(1, 0);
        p1.open(2, 1);
        p1.open(3, 0);
        p1.open(4, 1);
        p1.open(5, 0);
        p1.open(6, 0);
        p1.open(8, 0);
        p1.open(9, 0);
        p1.open(6, 3);
        p1.open(7, 0);
        p1.open(8, 3);
        p1.open(9, 3);
        p1.displayGrid();
        StdDraw.pause(3000);

        // TestCase2
        Percolation p2 = new Percolation(5);
        p2.open(0, 0);
        p2.open(1, 1);
        p2.open(2, 2);
        p2.open(3, 3);
        p2.open(4, 4);
        p2.displayGrid();
        StdDraw.pause(3000);

        // TestCase3
        Percolation p3 = new Percolation(8);
        p3.open(0, 0);
        p3.open(0, 7);
        p3.open(7, 0);
        p3.open(7, 7);
        p3.displayGrid();
        StdDraw.pause(3000);

        // TestCase4
        Percolation p4 = new Percolation(12);
        p4.open(1, 0);
        p4.open(1, 1);
        p4.open(1, 2);
        p4.open(1, 3);
        p4.open(1, 4);
        p4.open(1, 5);
        p4.open(1, 6);
        p4.open(1, 7);
        p4.open(1, 8);
        p4.open(1, 9);
        p4.open(1, 10);
        p4.open(1, 11);
        p4.open(5, 0);
        p4.open(5, 1);
        p4.open(5, 2);
        p4.open(5, 3);
        p4.open(5, 4);
        p4.open(5, 5);
        p4.open(5, 6);
        p4.open(5, 7);
        p4.open(5, 8);
        p4.open(5, 9);
        p4.open(5, 10);
        p4.open(5, 11);
        p4.open(2, 5);
        p4.open(3, 5);
        p4.open(4, 5);
        p4.open(6, 5);
        p4.open(7, 5);
        p4.open(8, 5);
        p4.open(9, 5);
        p4.open(10, 5);
        p4.open(11, 5);
        p4.open(0, 5);


        p4.displayGrid();
        StdDraw.pause(3000);

        // TestCase5
        Percolation p5 = new Percolation(6);
        p5.open(0, 0);
        p5.open(0, 1);
        p5.open(0, 2);
        p5.open(3, 3);
        p5.open(4, 3);
        p5.open(5, 3);
        p5.displayGrid();
        StdDraw.pause(3000);

        // TestCase6
        Percolation p6 = new Percolation(7);
        p6.openAllSites(0.7);
        p6.displayGrid();
        StdDraw.pause(3000);

        // TestCase7
        Percolation p7 = new Percolation(15);
        p7.openAllSites(0.5);
        p7.displayGrid();
        StdDraw.pause(3000);

        // TestCase8
        Percolation p8 = new Percolation(20);
        p8.openAllSites(0.4);
        p8.displayGrid();
        StdDraw.pause(3000);

        // TestCase9
        Percolation p9 = new Percolation(20);
        for (int i = 0; i < 20; i++) {
            p9.open(i, 7);
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if ((i + j) % 5 == 0) {
                    p9.open(i, j);
                }
            }
        }
        p9.displayGrid();
        StdDraw.pause(3000);

        // TestCase10
        Percolation p10 = new Percolation(8);
        p10.open(0, 0);
        p10.open(0, 7);
        p10.open(7, 0);
        p10.open(7, 0);
        p10.open(7, 1);
        p10.open(7, 2);
        p10.open(7, 3);
        p10.open(7, 4);
        p10.open(7, 5);
        p10.open(7, 6);
        p10.open(7, 7);
        p10.displayGrid();
        StdDraw.pause(3000);
    }}