package chess;

import java.util.ArrayList;
import java.util.Collection;

public interface PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

    public default void addMoveHelper(ArrayList<ChessMove> result, int x, int y, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor myTeamColor){
        if(myPosition.chgPosition(x,y).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(x,y);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }
        }
    }
}
