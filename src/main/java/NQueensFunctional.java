import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * A class to keep track of an (x, y) position.
     *
     * @author Jelle Huibregtse
     */
    public static record Position(int x, int y) {
        @Override
        public String toString() {
            return String.format("(%d, %d)", this.x, this.y);
        }
    }

    /**
     * A class take keeps track of position and state of a tile.
     *
     * @author Aron Hemmes, Jelle Huibregtse
     */
    public static record Tile(Position position, int state) {
        @Override
        public String toString() {
            return String.valueOf(state);
        }
    }

    /**
     * A class that stores tiles and has all functionality of the board.
     *
     * @author Aron Hemmes
     */
    public static class Board {
        private final Tile[] tiles;

        public Board(Tile[] tiles) {
            this.tiles = tiles;
        }

        /**
         * A constructor that generates an empty board.
         *
         * @param size of board
         */
        public Board(final int size) {
            this.tiles =
                    IntStream.range(0, size * size)
                            .mapToObj(pos -> new Tile(new Position(pos % size, (int) Math.floor((double) pos / size)), 0))
                            .toArray(Tile[]::new);
        }

        public int length() {
            return (int) Math.sqrt(tiles.length);
        }

        public Tile[] getTiles() {
            return tiles;
        }

        /**
         * A function to get a tile by position.
         *
         * @param x coordinate.
         * @param y coordinate.
         * @return tile with position and state.
         */
        public Tile getTileByPos(final int x, final int y) {
            return Arrays.stream(tiles)
                    .filter(i -> i.position().x() == x && i.position().y() == y)
                    .findFirst()
                    .orElseThrow(NullPointerException::new);
        }

        /**
         * A function to check if a queen can be placed at board[row][column].
         *
         * @param x on row where the queen needs to be placed.
         * @param y on column where the queen needs to be placed.
         * @return true if a queen can be placed on x, y.
         */
        public boolean isSafe(final int x, final int y) {
            return IntStream.range(0, length()).noneMatch(i ->
                    IntStream.range(0, length()).anyMatch(j ->
                            // Check if there's a queen on (i, j), if so check if it's on the same axis or diagonal of (x, y).
                            getTileByPos(i, j).state() == 1 && (i == x || j == y || Math.abs(j - y) == Math.abs(i - x))
                    )
            );
        }

        /**
         * A function to return a copy of this board with a changed value on coordinate (x, y)
         *
         * @param x     coordinate
         * @param y     coordinate
         * @param state of tile
         * @return new board with changed value
         */
        public Board copyWithChangedValueOnCoordinate(int x, int y, int state) {
            return new Board(Arrays.stream(getTiles()).map(t -> t.position().x() == x && t.position().y() == y ? new Tile(new Position(x, y), state) : t).toArray(Tile[]::new));
        }

        @Override
        public String toString() {
            return "  -" + IntStream.range(0, length()).mapToObj(i -> "---").collect(Collectors.joining("")) + "-\n" +
                   IntStream.range(0, length()).mapToObj(i -> Math.abs(i - length()) + " | " + IntStream.range(0, length()).mapToObj(j -> tiles[j + i * length()].toString()).collect(Collectors.joining("  ")) + " |\n").collect(Collectors.joining("")) +
                   "  -" + IntStream.range(0, length()).mapToObj(i -> "---").collect(Collectors.joining("")) + "-\n" +
                   "    " + IntStream.range(0, length()).mapToObj(i -> String.valueOf("abcdefghijklmnopqrstuvwxyz".toCharArray()[i])).collect(Collectors.joining("  "));
        }
    }

    // Driver code.
    public static void main(String[] args) {
        NQueensFunctional nQueensFunctional = new NQueensFunctional();
        List<Board> solutions = nQueensFunctional.solve(new Board(8), 8, 0);
        System.out.println("There are " + solutions.size() + " solutions.");
        System.out.println(solutions.get(0).toString());
    }


    Board solveOnce(Board board, int n, int column) {
        return column >= n ?
                // The base case: all queens have been placed.
                board :
                // For the current column, consider all rows.
                IntStream.range(0, n).filter(i -> board.isSafe(i, column) && solveOnce(board.copyWithChangedValueOnCoordinate(i, column, 1), n, column + 1) != null).findFirst().stream().mapToObj(i -> solveOnce(board.copyWithChangedValueOnCoordinate(i, column, 1), n, column + 1)).findFirst().orElse(null);
    }

    /**
     * The solve function implementing the algorithm.
     *
     * @param board  2d array of row and column.
     * @param n      amount of queens.
     * @param column iterate between columns.
     * @return a list of all the solutions.
     */
    List<Board> solve(Board board, int n, int column) {
        return column >= n ?
                // The base case: all queens have been placed.
                new ArrayList<>(Collections.singleton(board)) :
                // For the current column, consider all rows.
                IntStream.range(0, n).mapToObj(i ->
                                // Check to see if it is safe to place a queen at row i.
                                board.isSafe(i, column) ?
                                        // Recursively do this for the rest of the columns, until the base case is reached.
                                        solve(
                                                // Copy board to a new board and add queen on (i, column).
                                                board.copyWithChangedValueOnCoordinate(i, column, 1), n, column + 1) :
                                        // If we reach this code, we know that placing a queen here didn't work, so we return nothing and backtrack.
                                        new ArrayList<Board>()
                        // Collect every board to one list.
                ).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
    }
}