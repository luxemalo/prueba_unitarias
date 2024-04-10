package org.iesvdm.tddjava.connect4;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
//

public class Connect4TDDSpec {

    private Connect4TDD tested;

    private OutputStream output;

    @BeforeEach
    public void beforeEachTest() {
        output = new ByteArrayOutputStream();

        //Se instancia el juego modificado para acceder a la salida de consola
        tested = new Connect4TDD(new PrintStream(output));
    }

    /*
     * The board is composed by 7 horizontal and 6 vertical empty positions
     */

    @Test
    public void whenTheGameStartsTheBoardIsEmpty() {
        assertThat(tested.getNumberOfDiscs()).isEqualTo(0);
    }

    /*
     * Players introduce discs on the top of the columns.
     * Introduced disc drops down the board if the column is empty.
     * Future discs introduced in the same column will stack over previous ones
     */

    @Test
    public void whenDiscOutsideBoardThenRuntimeException() {
        assertThatThrownBy(() -> {tested.putDiscInColumn(8);})
                .isInstanceOf(RuntimeException.class);

        assertThatThrownBy(() -> {tested.putDiscInColumn(-1);})
                .isInstanceOf(RuntimeException.class);

       /* assertThatThrownBy(() -> {
            for (int i = 0; i <= 7; i++) {
                tested.putDiscInColumn(3);
            }
        }).isInstanceOf(RuntimeException.class).hasMessage("No more room");
        */

       assertThrows(RuntimeException.class, () -> {
           for (int i = 0; i <= 7; i++) {
           tested.putDiscInColumn(1);
           }
       });

    }

    @Test
    public void whenFirstDiscInsertedInColumnThenPositionIsZero() {

        for (int i = 0; i < 7; i++) {
            assertThat(tested.putDiscInColumn(i)).isEqualTo(0);
        }
    }

    @Test
    public void whenSecondDiscInsertedInColumnThenPositionIsOne() {
        for (int i = 0; i < 7; i++) {
            tested.putDiscInColumn(i);
            assertThat(tested.putDiscInColumn(i)).isEqualTo(1);
        }
    }

    @Test
    public void whenDiscInsertedThenNumberOfDiscsIncreases() {
        int discosInicio=0;
        for (int i = 0; i < 7; i++) {
            tested.putDiscInColumn(i);
            assertThat(tested.getNumberOfDiscs()).isEqualTo(discosInicio + 1 +i);
        }
    }

    @Test
    public void whenNoMoreRoomInColumnThenRuntimeException() {
        for (int i = 0; i < Connect4.COLUMNS; ++i) {
            tested.putDiscInColumn(1);
        }
        assertThrows(RuntimeException.class, () -> tested.putDiscInColumn(1));


    }

    /*
     * It is a two-person game so there is one colour for each player.
     * One player uses red ('R'), the other one uses green ('G').
     * Players alternate turns, inserting one disc every time
     */

    @Test
    public void whenFirstPlayerPlaysThenDiscColorIsRed() {
        tested.putDiscInColumn(1);
        String board = new String(output.toString()).trim();
        assertThat(board).contains("R");
    }

    @Test
    public void whenSecondPlayerPlaysThenDiscColorIsGreen() {
        tested.putDiscInColumn(1);
        tested.getCurrentPlayer();
        tested.putDiscInColumn(2);
        String board = new String(output.toString()).trim();
        assertThat(board).contains("G");
    }

    /*
     * We want feedback when either, event or error occur within the game.
     * The output shows the status of the board on every move
     */

    @Test
    public void whenAskedForCurrentPlayerTheOutputNotice() {
        tested.putDiscInColumn(1);
        String board = new String(output.toString()).trim();
        assertThat(board).contains("Current turn: GREEN");


    }

    @Test
    public void whenADiscIsIntroducedTheBoardIsPrinted() {
        tested.putDiscInColumn(1);
        String board = new String(output.toString()).trim();
        for (int i = 0; i < Connect4.ROWS; ++i) {
            assertThat(board).contains(String.valueOf(Connect4.Color.EMPTY));
        }
        assertThat(board).contains("1");
    }

    /*
     * When no more discs can be inserted, the game finishes and it is considered a draw
     */

    @Test
    public void whenTheGameStartsItIsNotFinished() {
        assertThat(tested.isFinished()).isFalse();

    }

    @Test
    public void whenNoDiscCanBeIntroducedTheGamesIsFinished() {
        for (int i = 0; i < Connect4.COLUMNS; ++i) {
            for (int j = 0; j < Connect4.ROWS; ++j) {
                tested.putDiscInColumn(i + 1);
            }
        }
        assertThat(tested.isFinished()).isTrue();
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight vertical line then that player wins
     */

    @Test
    public void when4VerticalDiscsAreConnectedThenThatPlayerWins() {
        for (int i = 0; i < Connect4.DISCS_FOR_WIN; ++i) {
            tested.putDiscInColumn(1);
            tested.getCurrentPlayer();
        }
        tested.putDiscInColumn(1);
        String board = new String(output.toString()).trim();
        assertThat(board).contains("RED wins");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight horizontal line then that player wins
     */

    @Test
    public void when4HorizontalDiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(1);
        for (int i = 2; i <= Connect4.COLUMNS; ++i) {
            tested.putDiscInColumn(i);
        }
        String board = output.toString().trim();
        assertThat(board).contains("RED wins");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight diagonal line then that player wins
     */

    @Test
    public void when4Diagonal1DiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.getNumberOfDiscs();
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        String board = output.toString().trim();
        assertThat(board).contains("RED wins");
    }

    @Test
    public void when4Diagonal2DiscsAreConnectedThenThatPlayerWins() {
        for (int i = 0; i < 3; ++i) {
            tested.putDiscInColumn(1);
            tested.getCurrentPlayer();
            tested.putDiscInColumn(Connect4.COLUMNS);
        }
        tested.putDiscInColumn(1);
        tested.getCurrentPlayer();
        tested.putDiscInColumn(Connect4.COLUMNS - 1);
        tested.putDiscInColumn(1);
        String board = output.toString().trim();
        assertThat(board).contains("GREEN wins");
    }
}
