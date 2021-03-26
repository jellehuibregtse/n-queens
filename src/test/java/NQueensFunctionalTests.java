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
        emptyBoard = emptyBoard.copyWithChangedValueOnCoordinate(1, 2, 1);
        Assertions.assertTrue(emptyBoard.isSafe(0, 0));
    }

    @Test
    public void isNotSafe_QueenOnSameXY() {
        emptyBoard = emptyBoard.copyWithChangedValueOnCoordinate(0, 0, 1);
        Assertions.assertFalse(emptyBoard.isSafe(0, 0));
    }

    @Test
    public void isNotSafe_QueenOnSameXAngle() {
        emptyBoard = emptyBoard.copyWithChangedValueOnCoordinate(0, 0, 1);
        Assertions.assertFalse(emptyBoard.isSafe(3, 0));
    }

    @Test
    public void isNotSafe_QueenOnSameYAngle() {
        emptyBoard = emptyBoard.copyWithChangedValueOnCoordinate(0, 0, 1);
        Assertions.assertFalse(emptyBoard.isSafe(0, 3));
    }

    @Test
    public void isNotSafe_QueenOnSameUpLeftDiagonal() {
        emptyBoard = emptyBoard.copyWithChangedValueOnCoordinate(0, 0, 1);
        Assertions.assertFalse(emptyBoard.isSafe(3, 3));
    }

    @Test
    public void isNotSafe_QueenOnSameDownLeftDiagonal() {
        emptyBoard = emptyBoard.copyWithChangedValueOnCoordinate(0, 0, 1);
        Assertions.assertFalse(emptyBoard.isSafe(3, 0));
    }

    @ParameterizedTest
    @MethodSource("solutionsSet")
    public void amountOfSolutions(int n, int amountOfSolutions) {
        long startTime = System.nanoTime();
        List<NQueensFunctional.Board> solutions = nQueensFunctional.solve(new NQueensFunctional.Board(n), n, 0);
        long stopTime = System.nanoTime();
        Assertions.assertEquals(amountOfSolutions, solutions.size());
        System.out.println("Time in seconds: " + (double)(stopTime - startTime) / 1000000000);
    }

    @ParameterizedTest
    @MethodSource("solutionsSet")
    public void solutionIsAvailable(int n, int amountOfSolutions) {
        long startTime = System.nanoTime();
        NQueensFunctional.Board solution = nQueensFunctional.solveOnce(new NQueensFunctional.Board(n), n, 0);
        long stopTime = System.nanoTime();
        if(amountOfSolutions == 0) {
            Assertions.assertNull(solution);
            System.out.println("Time in seconds: " + (double)(stopTime - startTime) / 1000000000);
        }
        else {
            Assertions.assertNotEquals(solution, null);
            System.out.println("Time in seconds: " + (double)(stopTime - startTime) / 1000000000);
            System.out.println(solution.toString());
        }
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
