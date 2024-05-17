package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();

//        if(myTeamColor == ChessGame.TeamColor.WHITE){
//            if(!board.getWKmoved() && !board.getWRRmoved()){
//                if(board.getPiece(new ChessPosition(1, 6)) == null && board.getPiece(new ChessPosition(1, 7)) == null){
//                    result.add(new ChessMove(myPosition, new ChessPosition(1,6), null));
//                }
//            }
//            if(!board.getWKmoved() && !board.getWLRmoved()){
//                if(board.getPiece(new ChessPosition(1, 4)) == null && board.getPiece(new ChessPosition(1, 3)) == null && board.getPiece(new ChessPosition(1, 2)) == null){
//                    result.add(new ChessMove(myPosition, new ChessPosition(1,4), null));
//                }
//            }
//        }
//
//        if(myTeamColor == ChessGame.TeamColor.BLACK){
//            if(!board.getBKmoved() && !board.getBRRmoved()){
//                if(board.getPiece(new ChessPosition(8, 6)) == null && board.getPiece(new ChessPosition(8, 7)) == null){
//                    result.add(new ChessMove(myPosition, new ChessPosition(8,6), null));
//                }
//            }
//            if(!board.getBKmoved() && !board.getBLRmoved()){
//                if(board.getPiece(new ChessPosition(8, 4)) == null && board.getPiece(new ChessPosition(8, 3)) == null && board.getPiece(new ChessPosition(8, 2)) == null){
//                    result.add(new ChessMove(myPosition, new ChessPosition(8,4), null));
//                }
//            }
//        }

        // get moves to the right.
        int i = 1;
        while (myPosition.chgPosition(0, i).inBounds() && board.getPiece(myPosition.chgPosition(0, i)) == null) {
            result.add(new ChessMove(myPosition, myPosition.chgPosition(0, i), null));
            i++;
        }
        if (myPosition.chgPosition(0, i).inBounds() && board.getPiece(myPosition.chgPosition(0, i)).getTeamColor() != myTeamColor) {
            result.add(new ChessMove(myPosition, myPosition.chgPosition(0, i), null));
        }
        // get moves upwards
        i = 1;
        while (myPosition.chgPosition(i, 0).inBounds() && board.getPiece(myPosition.chgPosition(i, 0)) == null) {
            result.add(new ChessMove(myPosition, myPosition.chgPosition(i, 0), null));
            i++;
        }
        if (myPosition.chgPosition(i, 0).inBounds() && board.getPiece(myPosition.chgPosition(i, 0)).getTeamColor() != myTeamColor) {
            result.add(new ChessMove(myPosition, myPosition.chgPosition(i, 0), null));
        }
        // get moves to the left
        i = -1;
        while (myPosition.chgPosition(0, i).inBounds() && board.getPiece(myPosition.chgPosition(0, i)) == null) {
            result.add(new ChessMove(myPosition, myPosition.chgPosition(0, i), null));
            i--;
        }
        if (myPosition.chgPosition(0, i).inBounds() && board.getPiece(myPosition.chgPosition(0, i)).getTeamColor() != myTeamColor) {
            result.add(new ChessMove(myPosition, myPosition.chgPosition(0, i), null));
        }
        // get moves downwards
        i = -1;
        while (myPosition.chgPosition(i, 0).inBounds() && board.getPiece(myPosition.chgPosition(i, 0)) == null) {
            result.add(new ChessMove(myPosition, myPosition.chgPosition(i, 0), null));
            i--;
        }
        if (myPosition.chgPosition(i, 0).inBounds() && board.getPiece(myPosition.chgPosition(i, 0)).getTeamColor() != myTeamColor) {
            result.add(new ChessMove(myPosition, myPosition.chgPosition(i, 0), null));
        }

        return result;
    }
}
