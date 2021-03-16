import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class NQueensTests {

    private NQueens nQueens;
    private int[][] emptyBoard;

    @BeforeEach
    public void setup() {
        nQueens = new NQueens();

        emptyBoard = nQueens.generateEmptyBoard(nQueens.getN());
    }

    @Test
    public void isSafe_Empty_Board() {
        for (int i = 0; i < emptyBoard.length; i++) {
            for (int j = 0; j < emptyBoard.length; j++) {
                Assertions.assertTrue(nQueens.isSafe(emptyBoard, i, j));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("solutionsSet")
    public void amountOfSolutions(int n, int amountOfSolutions) {
        nQueens.setN(n);
        nQueens.solve();
        Assertions.assertEquals(amountOfSolutions, nQueens.getAmountOfSolutions());
    }

    private static Stream<Arguments> solutionsSet() {
        return Stream.of(
                Arguments.of(1, 1),
                Arguments.of(2, 0),
                Arguments.of(3, 0),
                Arguments.of(4, 2),
                Arguments.of(5, 10),
                Arguments.of(6, 4),
                Arguments.of(7, 40),
                Arguments.of(8, 92),
                Arguments.of(9, 352),
                Arguments.of(10, 724));
    }
}
