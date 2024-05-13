package chess;


import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable{
    private ChessPiece[][] chessBoard;
    public ChessBoard() {
         chessBoard = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoard[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // while loop to add the pawns to both sides of the code
        int i = 1;
        while(i <= 8){
            addPiece(new ChessPosition(2, i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(7, i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
            i++;
        }
        //add rest of the white pieces
        addPiece(new ChessPosition(1,1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(1,8), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(1,2), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1,7), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1,3), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1,6), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1,4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(1,5), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));

        //add rest of the black pieces;
        addPiece(new ChessPosition(8,1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8,8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8,2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8,7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8,3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8,6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8,4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8,5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
    }

    public void makeMove(ChessMove move){
        ChessPiece movingPiece = getPiece(move.getStartPosition());
        addPiece(move.getEndPosition(), movingPiece);
        addPiece(move.getStartPosition(), null);
    }

    public boolean isInCheck(ChessBoard board, ChessGame.TeamColor teamColor) {
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

    public ChessPosition getKingPosition(ChessGame.TeamColor teamColor){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition temp = new ChessPosition(i,j);
                ChessPiece result = getPiece(temp);
                if(result != null && result.getPieceType() == ChessPiece.PieceType.KING && result.getTeamColor() == teamColor){
                    return temp;
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(chessBoard, that.chessBoard);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(chessBoard);
    }

    @Override
    public ChessBoard clone(){
        ChessBoard clone;
        try{
            clone = (ChessBoard) super.clone();
            clone.chessBoard = new ChessPiece[8][8];
            for(int i = 1; i <= 8; i++){
                for(int k = 1; k <= 8; k++){
                    ChessPosition temp = new ChessPosition(i,k);
                    clone.addPiece(temp, getPiece(temp));
                }
            }
        }
        catch(CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
        return clone;
    }
}
