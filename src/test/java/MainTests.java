import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTests {

    private int[][] fullBoard;
    private int[][] emptyBoard;

    @BeforeEach
    public void setup() {
        fullBoard = Main.generateBoard(8);
        fullBoard[0][5] = 1;
        fullBoard[1][3] = 1;
        fullBoard[2][6] = 1;
        fullBoard[3][0] = 1;
        fullBoard[4][7] = 1;
        fullBoard[5][1] = 1;
        fullBoard[6][4] = 1;
        fullBoard[7][2] = 1;

        emptyBoard = Main.generateBoard(8);
    }

    @Test
    public void isSafe_Full_Board() {
        for (int i = 0; i < fullBoard.length; i++) {
            for (int j = 0; j < fullBoard.length; j++) {
                Assertions.assertFalse(Main.isSafe(fullBoard, i, j));
            }
        }
    }

    @Test
    public void isSafe_Empty_Board() {
        for (int i = 0; i < emptyBoard.length; i++) {
            for (int j = 0; j < emptyBoard.length; j++) {
                Assertions.assertTrue(Main.isSafe(emptyBoard, i, j));
            }
        }
    }
}
