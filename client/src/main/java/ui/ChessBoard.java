package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import static ui.EscapeSequences.*;

public class ChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String EMPTY = "   ";
    private static final String[] WHITE_HEADERS = { " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h " };
    private static final String[] BLACK_HEADERS = {" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "};
    private static final String[] headers = { "a", "b", "c", "d", "e",  "f", "g", "h" };


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        ChessGame game = new ChessGame();

        drawWhiteBoard(game);
        drawBlackBoard(game);
        drawWhiteBoardHighlight(game, new ChessPosition(2,2));
        drawBlackBoardHighlight(game, new ChessPosition(2,2));
    }

    public static int positionConverterToInt(String pos){
        for(int i = 0; i < headers.length; i++){
            if(headers[i].equals(pos)){
                return i + 1;
            }
        }
        return 0;
    }

    public static void drawWhiteBoardHighlight(ChessGame chessGame, ChessPosition piece){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        drawWhite(out, chessGame, piece);
    }

    public static void drawBlackBoardHighlight(ChessGame chessGame, ChessPosition piece){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        drawBlack(out, chessGame, piece);
    }

    public static void drawWhiteBoard(ChessGame chessGame){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        drawWhite(out, chessGame, null);
    }

    public static void drawBlackBoard(ChessGame chessGame){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        drawBlack(out, chessGame, null);
    }

    private static void drawWhite(PrintStream out, ChessGame chessGame, ChessPosition targetPiece){
        drawHeaders(out, WHITE_HEADERS);

        drawChessBoard(out, "white", chessGame, targetPiece);

        drawHeaders(out, WHITE_HEADERS);

        out.print(RESET_BG_COLOR);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawBlack(PrintStream out, ChessGame chessGame, ChessPosition targetPiece){
        drawHeaders(out, BLACK_HEADERS);

        drawChessBoard(out, "black", chessGame, targetPiece);

        drawHeaders(out, BLACK_HEADERS);

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

    private static void drawChessBoard(PrintStream out, String team, ChessGame chessGame, ChessPosition targetPiece) {
        int rowId;
        ChessPiece[][] chessBoard = chessGame.getBoard().getChessBoard();
        ChessPosition finalPosition;
        if(team.equals("black") && targetPiece != null){
            rowId = 1;
            chessBoard = reverseBoard(chessBoard);
            finalPosition = new ChessPosition(targetPiece.getRow(), targetPiece.getColumn());
        }
        else if(team.equals("white") && targetPiece != null){
            rowId = 8;
            finalPosition = new ChessPosition(9 - targetPiece.getRow(), targetPiece.getColumn());
        }
        else{
            if(team.equals("black")){
                chessBoard = reverseBoard(chessBoard);
                rowId = 1;
            }
            else{
                rowId = 8;
            }
            finalPosition = targetPiece;
        }
        Collection<ChessPosition> positions = new HashSet<>();
        if(targetPiece == null){
            positions = null;
        }
        else{
            Collection<ChessMove> validMoves = chessGame.validMoves(finalPosition);
            for(ChessMove move : validMoves){
                positions.add(move.getEndPosition());
            }
        }



        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            out.print(RESET_BG_COLOR);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(" " + rowId + " ");
            if(boardRow % 2 == 1) {
                drawRowOfSquaresBlue(out, chessBoard[7 - boardRow], boardRow + 1, positions);
            }
            else {
                drawRowOfSquaresWhite(out, chessBoard[7 - boardRow], boardRow + 1, positions);
            }
            out.print(RESET_BG_COLOR);
            out.print(SET_TEXT_COLOR_GREEN);
            out.print(" " + rowId + " ");
            out.println();
            if(team.equals("black")){
                ++rowId;
            }
            else{
                --rowId;
            }

        }
    }

    private static void drawRowOfSquaresBlue(PrintStream out, ChessPiece[] row, int currentRow, Collection<ChessPosition> positions) {
            if(positions != null) {
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    boolean isPositionInCollection = false;

                    for (ChessPosition position : positions) {
                        if (position.getRow() == currentRow && position.getColumn() == boardCol + 1) {
                            isPositionInCollection = true;
                            break;
                        }
                    }

                    if (boardCol % 2 == 1) {
                        setWhite(out);
                        if (isPositionInCollection) {
                            setYellow(out);
                        }
                    } else {
                        setBlue(out);
                        if (isPositionInCollection) {
                            setBrightYellow(out);
                        }
                    }

                    printPieceConverter(out, row[boardCol]);
                }
            }
            else{
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                        if (boardCol % 2 == 1) {
                            setWhite(out);
                        } else {
                            setBlue(out);
                    }
                    printPieceConverter(out, row[boardCol]);
                }
            }
        }

    private static void drawRowOfSquaresWhite(PrintStream out, ChessPiece[] row, int currentRow, Collection<ChessPosition> positions) {
        if(positions != null) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                boolean isPositionInCollection = false;

                for (ChessPosition position : positions) {
                    if (position.getRow() == currentRow && position.getColumn() == boardCol + 1) {
                        isPositionInCollection = true;
                        break;
                    }
                }

                if (boardCol % 2 == 0) {
                    setWhite(out);
                    if (isPositionInCollection) {
                        setYellow(out);
                    }
                } else {
                    setBlue(out);
                    if (isPositionInCollection) {
                        setBrightYellow(out);
                    }
                }

                printPieceConverter(out, row[boardCol]);
            }
        }
        else{
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                if (boardCol % 2 == 0) {
                    setWhite(out);
                } else {
                    setBlue(out);
                }
                printPieceConverter(out, row[boardCol]);
            }
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

    private static void setYellow(PrintStream out){
        out.print(SET_BG_COLOR_YELLOW);
        out.print(SET_TEXT_COLOR_YELLOW);
    }

    private static void setBrightYellow(PrintStream out){
        out.print(SET_BG_COLOR_BRIGHT_YELLOW);
        out.print(SET_TEXT_COLOR_YELLOW);
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
