package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static ui.EscapeSequences.*;

public class ChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String EMPTY = "   ";
    private static final String[] whiteHeaders = { " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h " };
    private static final String[] blackHeaders = {" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "};


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        chess.ChessBoard board = new chess.ChessBoard();
        board.resetBoard();
        ChessPiece[][] startingBoard = board.getChessBoard();

        drawWhiteBoard(out, startingBoard);
        drawBlackBoard(out, reverseBoard(startingBoard));
    }

    public static void drawBoards(ChessPiece[][] chessBoard){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        drawWhiteBoard(out, chessBoard);
        drawBlackBoard(out, reverseBoard(chessBoard));
    }

    private static void drawWhiteBoard(PrintStream out, ChessPiece[][] chessBoard){
        drawHeaders(out, whiteHeaders);

        drawChessBoard(out, "white", chessBoard);

        drawHeaders(out, whiteHeaders);

        out.print(RESET_BG_COLOR);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawBlackBoard(PrintStream out, ChessPiece[][] chessBoard){
        drawHeaders(out, blackHeaders);

        drawChessBoard(out, "black", chessBoard);

        drawHeaders(out, blackHeaders);

        out.print(RESET_BG_COLOR);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out, String[] headers) {


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
        out.print(RESET_BG_COLOR);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

    }

    private static void drawChessBoard(PrintStream out, String team, ChessPiece[][] chessBoard) {
        int rowId;
        if(team == "black"){
            rowId = 1;
        }
        else{
            rowId = 8;
        }
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            out.print(RESET_BG_COLOR);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(" " + rowId + " ");
            if(boardRow % 2 == 1) {
                drawRowOfSquaresBlue(out, chessBoard[boardRow]);
            }
            else {
                drawRowOfSquaresWhite(out, chessBoard[boardRow]);
            }
            out.print(RESET_BG_COLOR);
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

    private static void drawRowOfSquaresBlue(PrintStream out, ChessPiece[] row) {

            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                if(boardCol % 2 == 1) {
                    setWhite(out);
                }
                else{
                    setBlue(out);
                }
                printPieceConverter(out, row[boardCol]);

            }

        }

    private static void drawRowOfSquaresWhite(PrintStream out, ChessPiece[] row) {

        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            if(boardCol % 2 == 0) {
                setWhite(out);
            }
            else{
                setBlue(out);
            }
            printPieceConverter(out, row[boardCol]);

        }
    }


    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlue(PrintStream out){
        out.print(SET_BG_COLOR_BLUE);
        out.print(SET_TEXT_COLOR_BLUE);
    }

    private static void printPieceConverter(PrintStream out, ChessPiece piece){
        HashMap<ChessPiece.PieceType, String> converter = new HashMap<>();
        converter.put(ChessPiece.PieceType.ROOK, " R ");
        converter.put(ChessPiece.PieceType.KNIGHT, " N ");
        converter.put(ChessPiece.PieceType.BISHOP, " B ");
        converter.put(ChessPiece.PieceType.QUEEN, " Q ");
        converter.put(ChessPiece.PieceType.KING, " K ");
        converter.put(ChessPiece.PieceType.PAWN, " P ");
        if(piece == null){
            out.print(EMPTY);
            setWhite(out);
        }

        else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(converter.get(piece.getPieceType()));
            setWhite(out);
        }
        else if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            out.print(SET_TEXT_COLOR_RED);
            out.print(converter.get(piece.getPieceType()));
            setWhite(out);
        }
    }

    private static ChessPiece[][] reverseBoard(ChessPiece[][] board){
        ChessPiece[][] result = new ChessPiece[8][8];
        int x = 0;
        int y;
        for(int i = 7; i >= 0; i--){
            y = 0;
            for(int k = 7; k >= 0; k--){
                result[i][k] = board[x][y];
                y++;
            }
            x++;
        }
        return result;
    }

}
