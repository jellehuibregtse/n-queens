import java.util.*;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * N Queens Problem – Functional
 * <p>
 * The n queens puzzle is the problem of placing n non-attacking queens on an n×n chessboard,
 * for which solutions exist for all natural numbers n where n ≠ 2 and n ≠ 3.
 * Thus, a solution requires that no two queens share the same row, column, or diagonal.
 * <p>
 * Since, we are doing a functional approach to the N Queens Problem we have to adhere to some functional programming rules:
 * 1. No state
 * 2. No side effects.
 * 3. Immutable variables
 * 4. Recursion > loops
 *
 * @author Jelle Huibregtse, Aron Hemmes
 */
public class NQueensFunctional {

    /**
     * A class to keep track of an (x, y) or position.
     *
     * @author Jelle Huibregtse
     */
    public static class Position {
        public final int x;
        public final int y;

        public Position(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", this.x, this.y);
        }
    }

    // Driver code.
    public static void main(String[] args) {
        NQueensFunctional nQueensFunctional = new NQueensFunctional();
        List<int[][]> solutions = nQueensFunctional.solve(nQueensFunctional.generateEmptyBoard(8), 8, 0);
        System.out.println(solutions.size());
        if(solutions.size() > 0) {
            NQueens nQueens = new NQueens();
            nQueens.prettyPrintBoard(solutions.get(0));
        }
    }

    /**
     * The solve function implementing the algorithm.
     *
     * @param board 2d array of row and column
     * @param n amount of queens
     * @param column iterate between columns
     * @return a list of all the solutions
     */
    List<int[][]> solve(int[][] board, int n, int column) {
        return column >= n?
            // The base case: all queens have been placed.
            new ArrayList<>(Collections.singleton(IntStream.range(0, n).mapToObj(i -> board[i].clone()).toArray(int[][]::new))) :
            // For the current column, consider all rows.
            IntStream.range(0, n).mapToObj(i ->
                // Check to see if it is safe to place a queen at row i.
                isSafe(board, i, column)?
                    // Recursively do this for the rest of the columns, until the base case is reached.
                    solve(
                        // copy board to a new board and add queen on (i, column)
                        IntStream.range(0, n).mapToObj(j -> IntStream.range(0, n).map(k -> j == i && k == column? 1 : board[j][k]).toArray()).toArray(int[][]::new), n, column + 1) :
                    // If we reach this code, we know that placing a queen here didn't work, so we return nothing and backtrack.
                    new ArrayList<int[][]>()
            // collect every board to one list
            ).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * A function to check if a queen can be placed at board[row][column].
     *
     * @param board 2d array of row and column
     * @param x on row where the queen needs to be placed
     * @param y on column where the queen needs to be placed
     * @return true if a queen can be placed on x, y
     */
    boolean isSafe(int[][] board, int x, int y) {
        return IntStream.range(0, board.length).noneMatch(i ->
            IntStream.range(0, board[i].length).anyMatch(j ->
                // check if there's a queen on (i, j), if so check if it's on the same axis or diagonal of (x, y)
                board[i][j] == 1 && (i == x || j == y || Math.abs(j - y) == Math.abs(i - x))
            )
        );
    }

    /**
     * A function to generate an empty board
     *
     * @param n size of the board
     * @return a 2D array (row, column) of the board, starting values are 0
     */
    int[][] generateEmptyBoard(int n) {
        return new int[n][n];
    }
}
