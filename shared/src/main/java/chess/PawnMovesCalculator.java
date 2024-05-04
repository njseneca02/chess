package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();
        if(myTeamColor == ChessGame.TeamColor.WHITE) {
            if (myPosition.chgPosition(1, 0).inBounds()) {
                ChessPosition moveTo = myPosition.chgPosition(1, 0);
                ChessPiece targetDest = board.getPiece(moveTo);
                if (targetDest == null) {
                    if(moveTo.getRow() == 8){
                        ChessMove addQ = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.QUEEN);
                        ChessMove addB = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.BISHOP);
                        ChessMove addK = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.KNIGHT);
                        ChessMove addR = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.ROOK);
                        result.add(addQ);
                        result.add(addB);
                        result.add(addK);
                        result.add(addR);
                    }
                    else {
                        ChessMove add = new ChessMove(myPosition, moveTo, null);
                        result.add(add);
                    }
                    if(myPosition.getRow() == 2){
                        if (myPosition.chgPosition(2, 0).inBounds()) {
                            ChessPosition moveTo2 = myPosition.chgPosition(2, 0);
                            ChessPiece targetDest2 = board.getPiece(moveTo2);
                            if (targetDest2 == null) {
                                ChessMove add2 = new ChessMove(myPosition, moveTo2, null);
                                result.add(add2);
                            }
                        }
                    }
                }
            }

            for (int i = -1; i <= 1; i += 2) {
                if (myPosition.chgPosition(1, i).inBounds()) {
                    ChessPosition moveTo = myPosition.chgPosition(1, i);
                    ChessPiece targetDest = board.getPiece(moveTo);
                    if (targetDest != null && targetDest.getTeamColor() != myTeamColor) {
                        if(moveTo.getRow() == 8){
                            ChessMove addQ = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.QUEEN);
                            ChessMove addB = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.BISHOP);
                            ChessMove addK = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.KNIGHT);
                            ChessMove addR = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.ROOK);
                            result.add(addQ);
                            result.add(addB);
                            result.add(addK);
                            result.add(addR);
                        }
                        else {
                            ChessMove add = new ChessMove(myPosition, moveTo, null);
                            result.add(add);
                        }
                    }
                }
            }
        }
        if(myTeamColor == ChessGame.TeamColor.BLACK) {
            if (myPosition.chgPosition(-1, 0).inBounds()) {
                ChessPosition moveTo = myPosition.chgPosition(-1, 0);
                ChessPiece targetDest = board.getPiece(moveTo);
                if (targetDest == null) {
                    if(moveTo.getRow() == 1){
                        ChessMove addQ = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.QUEEN);
                        ChessMove addB = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.BISHOP);
                        ChessMove addK = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.KNIGHT);
                        ChessMove addR = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.ROOK);
                        result.add(addQ);
                        result.add(addB);
                        result.add(addK);
                        result.add(addR);
                    }
                    else {
                        ChessMove add = new ChessMove(myPosition, moveTo, null);
                        result.add(add);
                    }
                    if(myPosition.getRow() == 7){
                        if (myPosition.chgPosition(-2, 0).inBounds()) {
                            ChessPosition moveTo2 = myPosition.chgPosition(-2, 0);
                            ChessPiece targetDest2 = board.getPiece(moveTo2);
                            if (targetDest2 == null) {
                                ChessMove add2 = new ChessMove(myPosition, moveTo2, null);
                                result.add(add2);
                            }
                        }
                    }
                }
            }

            for (int i = -1; i <= 1; i += 2) {
                if (myPosition.chgPosition(-1, i).inBounds()) {
                    ChessPosition moveTo = myPosition.chgPosition(-1, i);
                    ChessPiece targetDest = board.getPiece(moveTo);
                    if (targetDest != null && targetDest.getTeamColor() != myTeamColor) {
                        if(moveTo.getRow() == 1){
                            ChessMove addQ = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.QUEEN);
                            ChessMove addB = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.BISHOP);
                            ChessMove addK = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.KNIGHT);
                            ChessMove addR = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.ROOK);
                            result.add(addQ);
                            result.add(addB);
                            result.add(addK);
                            result.add(addR);
                        }
                        else {
                            ChessMove add = new ChessMove(myPosition, moveTo, null);
                            result.add(add);
                        }
                    }
                }
            }
        }
        return result;
    }
}
