import java.util.ArrayList;
import java.util.Arrays;

/**
 * N Queens Problem
 * <p>
 * The n queens puzzle is the problem of placing n non-attacking queens on an n×n chessboard,
 * for which solutions exist for all natural numbers n where n ≠ 2 and n ≠ 3.
 * Thus, a solution requires that no two queens share the same row, column, or diagonal.
 * <p>
 * We use backtracking to solve this problem.
 * 1. We start placing queens one-by-one, starting at the left most file (column) on the board.
 * 2. If we have placed n queens, we are finished.
 * 3. Try each rank (row) on the board for the current file (column).
 * 4. If a queen can be place on rank i, mark [rank, file] as part of the solution.
 * 5. Call the solve function recursively, but moving one file (column) to the right.
 * 6. If placing this queen leads to a solution, return.
 * 7. If not, then backtrack.
 * 6. If all ranks (rows) have been tried, return (backtrack).
 *
 * @author Jelle Huibregtse
 */
public class NQueens {
    private int N = 8;
    private int amountOfSolutions;

    private final ArrayList<int[][]> solutions = new ArrayList<>();
    private int[][] board = generateEmptyBoard();

    // Driver code.
    public static void main(String[] args) {
        NQueens nQueens = new NQueens();
        nQueens.solve();

        System.out.println(nQueens.solutions.size());

        if (nQueens.amountOfSolutions > 0) {
            nQueens.prettyPrintBoard(nQueens.solutions.get(0));
        }
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * A utility function to create an empty board.
     *
     * @return an 2D integer array of the board.
     */
    int[][] generateEmptyBoard() {
        int[][] board = new int[N][N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(board[i], 0);
        }

        return board;
    }

    /**
     * A utility function to print the board in the console.
     *
     * @param board an 2D integer array of the board.
     */
    void printBoard(int[][] board) {
        for (int[] values : board) {
            for (int j = 0; j < N; j++) {
                System.out.print(" " + values[j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * A utility function to print the board in the console. A pretty version.
     *
     * @param board an 2D integer array of the board.
     */
    void prettyPrintBoard(int[][] board) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        System.out.print("  ");
        for (int i = 0; i < N + 1; i++) {
            System.out.print(i == N ? "--\n" : "---");
        }
        for (int i = 0; i < N; i++) {
            System.out.print(N - i + " |");
            for (int j = 0; j < N; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.print("  ");
        for (int i = 0; i < N + 1; i++) {
            System.out.print(i == N ? "--\n" : "---");
        }
        for (int i = 0; i < N; i++) {
            System.out.print(i == 0 ? "\t" + alphabet[0] + " " : " " + alphabet[i] + " ");
        }
    }

    /**
     * Check if a queen can be placed at board[row][column].
     * <p>
     * Note: we only have to check the left side for attacking queens.
     * Since we are placing queens from left to right.
     *
     * @param board  an 2D integer array of the board.
     * @param row    where the queen wants to be placed.
     * @param column where the queen wants to be placed.
     * @return true if it's a valid placement.
     */
    boolean isSafe(int[][] board, int row, int column) {
        // Check row on the left.
        for (int i = 0; i < column; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Check upper left diagonal.
        for (int i = row, j = column; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        // Check lower left diagonal.
        for (int i = row, j = column; i < N && j >= 0; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    void solve() {
        solve(0);
    }

    /**
     * The solve function implementing the algorithm.
     *
     * @param column the current column being addressed.
     * @return true if placing was successful, moving to the parent call or out of the function, when a full solution has been reached.
     */
    boolean solve(int column) {
        // The base case: all queens have been placed.
        if (column >= N) {
            amountOfSolutions++;

            int[][] solution = new int[N][N];

            for (int i = 0; i < N; i++) {
                System.arraycopy(board[i], 0, solution[i], 0, N);
            }

            solutions.add(solution);

            return true;
        }

        boolean result = false;

        // For the current column, consider all rows.
        for (int i = 0; i < N; i++) {
            // Check to see if it is safe to place a queen at row i.
            if (isSafe(board, i, column)) {
                // We know it's safe, we can place a queen.
                board[i][column] = 1;

                // Recursively do this for the rest of the columns, until the base case is reached.
                result = solve(column + 1) || result;

                // If we reach this code, we know that placing a queen here didn't work, so we backtrack.
                board[i][column] = 0;
            }
        }

        return result;
    }
}