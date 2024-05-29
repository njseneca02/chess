package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        int i = 1;
        int j = -1;
        int k = 0;

        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();

        if(myPosition.chgPosition(i,j).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(i,j);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        //adds up
        if(myPosition.chgPosition(i,k).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(i,k);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        //adds up right
        addMoveHelper(result, i, i, myPosition, board, myTeamColor);
        //adds down left
        addMoveHelper(result, j, j, myPosition, board, myTeamColor);
        //adds down
        addMoveHelper(result, j, k, myPosition, board, myTeamColor);
        //adds down right
        addMoveHelper(result, j, i, myPosition, board, myTeamColor);
        //adds left
        addMoveHelper(result, k, j, myPosition, board, myTeamColor);
        //adds right
        addMoveHelper(result, k, i, myPosition, board, myTeamColor);
        return result;
    }

}
