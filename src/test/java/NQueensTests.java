import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NQueensTests {

    private NQueens nQueens;
    private int[][] emptyBoard;

    @BeforeEach
    public void setup() {
        nQueens = new NQueens();

        emptyBoard = nQueens.generateEmptyBoard();
    }

    @Test
    public void isSafe_Empty_Board() {
        for (int i = 0; i < emptyBoard.length; i++) {
            for (int j = 0; j < emptyBoard.length; j++) {
                Assertions.assertTrue(nQueens.isSafe(emptyBoard, i, j));
            }
        }
    }
}
