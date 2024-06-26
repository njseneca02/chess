package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();
        //adds all the paths that are up right.
        int i = 1;
        while(myPosition.chgPosition(i,i).inBounds() && board.getPiece(myPosition.chgPosition(i,i)) == null){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(i,i), null);
            result.add(add);
            i++;
        }
        //needed in the case that a capture is possible
        if(myPosition.chgPosition(i,i).inBounds() && board.getPiece(myPosition.chgPosition(i,i)).getTeamColor() != myTeamColor){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(i,i), null);
            result.add(add);
        }
        //adds all the paths that are down left
        int j = -1;
        while(myPosition.chgPosition(j,j).inBounds() && board.getPiece(myPosition.chgPosition(j,j)) == null){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(j,j), null);
            result.add(add);
            j--;
        }
        if(myPosition.chgPosition(j,j).inBounds() && board.getPiece(myPosition.chgPosition(j,j)).getTeamColor() != myTeamColor){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(j,j), null);
            result.add(add);
        }
        //adds all the paths that are up left
        i = 1;
        j = -1;
        while(myPosition.chgPosition(i,j).inBounds() && board.getPiece(myPosition.chgPosition(i,j)) == null){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(i,j), null);
            result.add(add);
            i++;
            j--;
        }
        if(myPosition.chgPosition(i,j).inBounds() && board.getPiece(myPosition.chgPosition(i,j)).getTeamColor() != myTeamColor){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(i,j), null);
            result.add(add);
        }
        //adds all the paths that are down right
        i = 1;
        j = -1;
        while(myPosition.chgPosition(j,i).inBounds() && board.getPiece(myPosition.chgPosition(j,i)) == null){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(j,i), null);
            result.add(add);
            i++;
            j--;
        }
        if(myPosition.chgPosition(j,i).inBounds() && board.getPiece(myPosition.chgPosition(j,i)).getTeamColor() != myTeamColor){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(j,i), null);
            result.add(add);
        }

        return result;
    }
}
