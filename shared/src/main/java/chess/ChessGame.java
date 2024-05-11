package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor teamTurn;
    ChessBoard board;

    public ChessGame() {
        board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = getKingPosition(teamColor);
        PieceMovesCalculator calc;

        calc = new KingMovesCalculator();
        Collection<ChessMove> temp = calc.pieceMoves(board, kingPosition);
        for(ChessMove danger : temp){
            ChessPosition opponentPosition = danger.getEndPosition();
            ChessPiece opponent = board.getPiece(opponentPosition);
            if(opponent != null && opponent.getPieceType() == ChessPiece.PieceType.KING && opponent.getTeamColor() != teamColor){
                return true;
            }
        }

        calc = new PawnMovesCalculator();
        temp = calc.pieceMoves(board, kingPosition);
        for(ChessMove danger : temp){
            ChessPosition opponentPosition = danger.getEndPosition();
            ChessPiece opponent = board.getPiece(opponentPosition);
            if(opponent != null && opponent.getPieceType() == ChessPiece.PieceType.PAWN && opponent.getTeamColor() != teamColor){
                return true;
            }
        }

        calc = new QueenMovesCalculator();
        temp = calc.pieceMoves(board, kingPosition);
        for(ChessMove danger : temp){
            ChessPosition opponentPosition = danger.getEndPosition();
            ChessPiece opponent = board.getPiece(opponentPosition);
            if(opponent != null && opponent.getPieceType() == ChessPiece.PieceType.QUEEN && opponent.getTeamColor() != teamColor){
                return true;
            }
        }

        calc = new RookMovesCalculator();
        temp = calc.pieceMoves(board, kingPosition);
        for(ChessMove danger : temp){
            ChessPosition opponentPosition = danger.getEndPosition();
            ChessPiece opponent = board.getPiece(opponentPosition);
            if(opponent != null && opponent.getPieceType() == ChessPiece.PieceType.ROOK && opponent.getTeamColor() != teamColor){
                return true;
            }
        }

        calc = new BishopMovesCalculator();
        temp = calc.pieceMoves(board, kingPosition);
        for(ChessMove danger : temp){
            ChessPosition opponentPosition = danger.getEndPosition();
            ChessPiece opponent = board.getPiece(opponentPosition);
            if(opponent != null && opponent.getPieceType() == ChessPiece.PieceType.BISHOP && opponent.getTeamColor() != teamColor){
                return true;
            }
        }

        calc = new KnightMovesCalculator();
        temp = calc.pieceMoves(board, kingPosition);
        for(ChessMove danger : temp){
            ChessPosition opponentPosition = danger.getEndPosition();
            ChessPiece opponent = board.getPiece(opponentPosition);
            if(opponent != null && opponent.getPieceType() == ChessPiece.PieceType.KNIGHT && opponent.getTeamColor() != teamColor){
                return true;
            }
        }


        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && isInStalemate(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    public ChessPosition getKingPosition(TeamColor teamColor){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition temp = new ChessPosition(i,j);
                ChessPiece result = board.getPiece(temp);
                if(result != null && result.getPieceType() == ChessPiece.PieceType.KING && result.getTeamColor() == teamColor){
                    return temp;
                }
            }
        }
        return null;
    }
}
