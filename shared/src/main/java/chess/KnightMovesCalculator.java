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
                addMoveHelper(result, i, j, myPosition, board, myTeamColor);
                // gets all the moves to the left and right of the knight
                addMoveHelper(result, j, i, myPosition, board, myTeamColor);

            }
        }
        return result;
    }
    public void addMoveHelper(ArrayList<ChessMove> result, int x, int y, ChessPosition myPosition, ChessBoard board, ChessGame.TeamColor myTeamColor){
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
