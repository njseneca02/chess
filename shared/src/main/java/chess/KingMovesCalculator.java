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
        if(myPosition.chgPosition(i,k).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(i,k);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        if(myPosition.chgPosition(i,i).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(i,i);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        if(myPosition.chgPosition(j,j).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(j,j);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        if(myPosition.chgPosition(j,k).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(j,k);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        if(myPosition.chgPosition(j,i).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(j,i);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
        if(myPosition.chgPosition(k,j).inBounds()){
            ChessPosition moveTo = myPosition.chgPosition(k,j);
            ChessPiece targetDest = board.getPiece(moveTo);
            if(targetDest == null || targetDest.getTeamColor() != myTeamColor){
                ChessMove add = new ChessMove(myPosition, moveTo, null);
                result.add(add);
            }

        }
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
