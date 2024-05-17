package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        int i = 1;
        int j = -1;
        int k = 0;
        //code can be condensed here using a loop to get all the moves that go in a line then add the two extras
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();
        //adds up left
//        if(myTeamColor == ChessGame.TeamColor.WHITE){
//            if(!board.getWKmoved() && !board.getWRRmoved()){
//                if(board.getPiece(new ChessPosition(1, 6)) == null && board.getPiece(new ChessPosition(1, 7)) == null){
//                    result.add(new ChessMove(myPosition, new ChessPosition(1,7), null));
//                }
//            }
//            if(!board.getWKmoved() && !board.getWLRmoved()){
//                if(board.getPiece(new ChessPosition(1, 4)) == null && board.getPiece(new ChessPosition(1, 3)) == null && board.getPiece(new ChessPosition(1, 2)) == null){
//                    result.add(new ChessMove(myPosition, new ChessPosition(1,3), null));
//                }
//            }
//        }
//
//        if(myTeamColor == ChessGame.TeamColor.BLACK){
//            if(!board.getBKmoved() && !board.getBRRmoved()){
//                if(board.getPiece(new ChessPosition(8, 6)) == null && board.getPiece(new ChessPosition(8, 7)) == null){
//                    result.add(new ChessMove(myPosition, new ChessPosition(1,7), null));
//                }
//            }
//            if(!board.getBKmoved() && !board.getBLRmoved()){
//                if(board.getPiece(new ChessPosition(8, 4)) == null && board.getPiece(new ChessPosition(8, 3)) == null && board.getPiece(new ChessPosition(8, 2)) == null){
//                    result.add(new ChessMove(myPosition, new ChessPosition(8,3), null));
//                }
//            }
//        }

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
        if(myPosition.chgPosition(i,i).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(i,i);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        //adds down left
        if(myPosition.chgPosition(j,j).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(j,j);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        //adds down
        if(myPosition.chgPosition(j,k).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(j,k);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        //adds down right
        if(myPosition.chgPosition(j,i).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(j,i);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        //adds left
        if(myPosition.chgPosition(k,j).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(k,j);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        //adds right
        if(myPosition.chgPosition(k,i).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(k,i);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        return result;
    }
}
