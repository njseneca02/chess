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
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
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
        ChessPiece piece = board.getPiece(startPosition);
        if(piece == null){
            return null;
        }
        ArrayList<ChessMove> allMoves = (ArrayList<ChessMove>) piece.pieceMoves(board, startPosition);
        ArrayList<ChessMove> validMoves = new ArrayList<ChessMove>();
        for(ChessMove temp : allMoves){
            ChessBoard copiedBoard = board.clone();
            copiedBoard.makeMove(temp);
            if(!copiedBoard.isInCheck(copiedBoard, piece.getTeamColor())){
                validMoves.add(temp);
            }

        }

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece movingPiece = board.getPiece(move.getStartPosition());
        if(movingPiece == null || !movingPiece.getTeamColor().equals(teamTurn)){
            throw new InvalidMoveException();
        }

        ArrayList<ChessMove> validMoves = (ArrayList<ChessMove>) validMoves(move.getStartPosition());
        boolean moved = false;
        for(ChessMove temp : validMoves){
            if(temp.equals(move)){
                board.makeMove(move);
                if(teamTurn == TeamColor.WHITE){
                    setTeamTurn(TeamColor.BLACK);
                }
                else{
                    setTeamTurn(TeamColor.WHITE);
                }
                moved = true;
                break;
            }
        }

        if(!moved){
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return board.isInCheck(board, teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor)){
            return cycleBoardPositions(teamColor);
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(!isInCheck(teamColor)){
            return cycleBoardPositions(teamColor);
        }
        return false;
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

    public boolean cycleBoardPositions(TeamColor teamColor){
        for(int i = 1; i <= 8; i++){
            for(int k = 1; k <= 8; k++){
                ChessPosition itr = new ChessPosition(i,k);
                ArrayList<ChessMove> validmvs = (ArrayList<ChessMove>) validMoves(itr);
                if (validmvs != null && !validmvs.isEmpty()) {
                    if(board.getPiece(itr).getTeamColor() == teamColor) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
