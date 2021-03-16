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

    static boolean isSafe(int[][] board, int row, int column) {
        //row
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == 1)
                return false;
        }
        //column
        for (int i = 0; i < board.length; i++) {
            if (board[i][column] == 1)
                return false;
        }
        //diagonal


        return true;
    }

    static int[][] generateBoard(int n) {
        int[][] board = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], 0);
        }

        return board;
    }

    public static void main(String[] args) {
        var board = generateBoard(8);

        board[3][5] = 1;

        printBoard(board);
    }
}