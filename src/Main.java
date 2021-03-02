import java.util.Arrays;

public class Main {
    static void printBoard(int[][] board) {
        for (int[] values : board) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(" " + values[j] + " ");
            }
            System.out.println();
        }
    }

    static int[][] generateBoard(int n) {
        int[][] board = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], 0);
        }

        return board;
    }

    public static void main(String[] args) {
        printBoard(generateBoard(8));
    }
}
