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
 * @author Jelle Huibregtse
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

    }
}
