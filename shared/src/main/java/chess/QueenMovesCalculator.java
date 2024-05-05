package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();
        // get moves to the right.
        int i = 1;
        while(myPosition.chgPosition(0,i).inBounds() && board.getPiece(myPosition.chgPosition(0, i)) == null){
            result.add(new ChessMove(myPosition, myPosition.chgPosition(0, i), null));
            i++;
        }
        if(myPosition.chgPosition(0,i).inBounds() && board.getPiece(myPosition.chgPosition(0, i)).getTeamColor() != myTeamColor){
            result.add(new ChessMove(myPosition, myPosition.chgPosition(0, i), null));
        }
        // get moves upwards
        i = 1;
        while(myPosition.chgPosition(i, 0).inBounds() && board.getPiece(myPosition.chgPosition(i,0)) == null){
            result.add(new ChessMove(myPosition, myPosition.chgPosition(i, 0), null));
            i++;
        }
        if(myPosition.chgPosition(i,0).inBounds() && board.getPiece(myPosition.chgPosition(i, 0)).getTeamColor() != myTeamColor){
            result.add(new ChessMove(myPosition, myPosition.chgPosition(i, 0), null));
        }
        // get moves to the left
        i = -1;
        while(myPosition.chgPosition(0, i).inBounds() && board.getPiece(myPosition.chgPosition(0, i)) == null){
            result.add(new ChessMove(myPosition, myPosition.chgPosition(0, i), null));
            i--;
        }
        if(myPosition.chgPosition(0,i).inBounds() && board.getPiece(myPosition.chgPosition(0, i)).getTeamColor() != myTeamColor){
            result.add(new ChessMove(myPosition, myPosition.chgPosition(0, i), null));
        }
        // get moves downwards
        i = -1;
        while(myPosition.chgPosition(i,0).inBounds() && board.getPiece(myPosition.chgPosition(i,0)) == null){
            result.add(new ChessMove(myPosition, myPosition.chgPosition(i, 0), null));
            i--;
        }
        if(myPosition.chgPosition(i,0).inBounds() && board.getPiece(myPosition.chgPosition(i, 0)).getTeamColor() != myTeamColor){
            result.add(new ChessMove(myPosition, myPosition.chgPosition(i, 0), null));
        }
        //get moves up and to the right
        i = 1;
        while(myPosition.chgPosition(i,i).inBounds() && board.getPiece(myPosition.chgPosition(i,i)) == null){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(i,i), null);
            result.add(add);
            i++;
        }
        if(myPosition.chgPosition(i,i).inBounds() && board.getPiece(myPosition.chgPosition(i,i)).getTeamColor() != myTeamColor){
            ChessMove add = new ChessMove(myPosition, myPosition.chgPosition(i,i), null);
            result.add(add);
        }
        // get moves down left
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
        //get moves up left
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
        // get moves down right
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
