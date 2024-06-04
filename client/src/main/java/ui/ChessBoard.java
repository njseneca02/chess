package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";
    private static final String[] whiteHeaders = { " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h " };
    private static final String[] blackHeaders = {" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "};
    private static Random rand = new Random();


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        drawWhiteBoard(out);
        drawBlackBoard(out);
    }

    private static void drawWhiteBoard(PrintStream out){
        drawHeaders(out, whiteHeaders);

        drawTicTacToeBoard(out, "white");

        drawHeaders(out, whiteHeaders);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawBlackBoard(PrintStream out){
        drawHeaders(out, blackHeaders);

        drawTicTacToeBoard(out, "black");

        drawHeaders(out, blackHeaders);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out, String[] headers) {

        setBlack(out);
        out.print(EMPTY);
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        printHeaderText(out, headerText);
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }
// pass in a 2D array here and have it cycle throw each "row"
    private static void drawTicTacToeBoard(PrintStream out, String team) {
        int rowId;
        if(team == "black"){
            rowId = 1;
        }
        else{
            rowId = 8;
        }
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(" " + rowId + " ");
            if(boardRow % 2 == 1) {
                drawRowOfSquaresBlue(out);
            }
            else {
                drawRowOfSquaresWhite(out);
            }
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(" " + rowId + " ");
            out.println();
            if(team == "black"){
                ++rowId;
            }
            else{
                --rowId;
            }

        }
    }
// pass each row into here and it can check to see what piece it should print on the row
    // also need to add code so that before each row there is a number printed as well as after.
    private static void drawRowOfSquaresBlue(PrintStream out) {

            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                if(boardCol % 2 == 1) {
                    setWhite(out);
                }
                else{
                    setBlue(out);
                }
                printPlayer(out, EMPTY);

                setBlack(out);
            }

        }

    private static void drawRowOfSquaresWhite(PrintStream out) {

        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            if(boardCol % 2 == 0) {
                setWhite(out);
            }
            else{
                setBlue(out);
            }
            printPlayer(out, EMPTY);

            setBlack(out);
        }
    }


    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setBlue(PrintStream out){
        out.print(SET_BG_COLOR_BLUE);
        out.print(SET_TEXT_COLOR_BLUE);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }

}
