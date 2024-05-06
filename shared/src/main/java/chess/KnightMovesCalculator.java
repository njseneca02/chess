package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();

        for(int i = -2; i <= 2; i += 4){
            for(int j = -1; j <= 1; j += 2){
                // gets all the moves below and above the knight
                if(myPosition.chgPosition(i,j).inBounds()){
                    ChessPosition moveTo = myPosition.chgPosition(i,j);
                    ChessPiece targetDest = board.getPiece(moveTo);
                    if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                        ChessMove add = new ChessMove(myPosition, moveTo, null);
                        result.add(add);
                    }
                }
                // gets all the moves to the left and right of the knight
                if(myPosition.chgPosition(j,i).inBounds()){
                    ChessPosition moveTo = myPosition.chgPosition(j,i);
                    ChessPiece targetDest = board.getPiece(moveTo);
                    if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                        ChessMove add = new ChessMove(myPosition, moveTo, null);
                        result.add(add);
                    }
                }

            }
        }
        return result;
    }
}
