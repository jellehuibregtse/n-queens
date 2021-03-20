import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class NQueensFunctionalTests {

    private NQueensFunctional nQueensFunctional;
    private NQueensFunctional.Board emptyBoard;

    @BeforeEach
    public void setup() {
        nQueensFunctional = new NQueensFunctional();

        emptyBoard = new NQueensFunctional.Board(8);
    }

    @Test
    public void isSafe_Empty_Board() {
        for (int i = 0; i < emptyBoard.length(); i++) {
            for (int j = 0; j < emptyBoard.length(); j++) {
                Assertions.assertTrue(emptyBoard.isSafe(i, j));
            }
        }
    }

    @Test
    public void isSafe_QueenOnOffAngle() {
        emptyBoard.getTileByPos(1, 2).setState(1);
        Assertions.assertTrue(emptyBoard.isSafe(0, 0));
    }

    @Test
    public void isNotSafe_QueenOnSameXY() {
        emptyBoard.getTileByPos(0, 0).setState(1);
        Assertions.assertFalse(emptyBoard.isSafe(0, 0));
    }

    @Test
    public void isNotSafe_QueenOnSameXAngle() {
        emptyBoard.getTileByPos(0, 0).setState(1);
        Assertions.assertFalse(emptyBoard.isSafe(3, 0));
    }

    @Test
    public void isNotSafe_QueenOnSameYAngle() {
        emptyBoard.getTileByPos(0, 0).setState(1);
        Assertions.assertFalse(emptyBoard.isSafe(0, 3));
    }

    @Test
    public void isNotSafe_QueenOnSameUpLeftDiagonal() {
        emptyBoard.getTileByPos(0, 0).setState(1);
        Assertions.assertFalse(emptyBoard.isSafe(3, 3));
    }

    @Test
    public void isNotSafe_QueenOnSameDownLeftDiagonal() {
        emptyBoard.getTileByPos(0, 0).setState(1);
        Assertions.assertFalse(emptyBoard.isSafe(3, 0));
    }

    @ParameterizedTest
    @MethodSource("solutionsSet")
    public void amountOfSolutions(int n, int amountOfSolutions) {
        List<NQueensFunctional.Board> solutions = nQueensFunctional.solve(new NQueensFunctional.Board(n), n, 0);
        Assertions.assertEquals(amountOfSolutions, solutions.size());
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
