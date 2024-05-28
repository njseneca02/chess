package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();
        //moves needed for a white pawn piece
        if(myTeamColor == ChessGame.TeamColor.WHITE) {
            if (myPosition.chgPosition(1, 0).inBounds()) {
                ChessPosition moveTo = myPosition.chgPosition(1, 0);
                ChessPiece targetDest = board.getPiece(moveTo);
                //in the case that no piece in the way of the pawn
                if (targetDest == null) {
                    //in the case that the pawn reaches the end of the board and needs promotion
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
                    //in the case that the pawn starts in starting position
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
            //loop allows the pawn to check if there is an enemy piece in the proper place to capture.
            for (int i = -1; i <= 1; i += 2) {
                if (myPosition.chgPosition(1, i).inBounds()) {
                    ChessPosition moveTo = myPosition.chgPosition(1, i);
                    ChessPiece targetDest = board.getPiece(moveTo);
                    //in the case that the pawn is able to capture a piece
                    if (targetDest != null && targetDest.getTeamColor() != myTeamColor) {
                        //in the case that the pawn reaches the end of the board and needs promotion
                        if(moveTo.getRow() == 8){
                            ChessMove addQn = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.QUEEN);
                            ChessMove addBn = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.BISHOP);
                            ChessMove addKn = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.KNIGHT);
                            ChessMove addRn = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.ROOK);
                            result.add(addQn);
                            result.add(addBn);
                            result.add(addKn);
                            result.add(addRn);
                        }
                        else {
                            ChessMove add = new ChessMove(myPosition, moveTo, null);
                            result.add(add);
                        }
                    }
                }
            }
        }
        //gets move for a pawn on the black team
        if(myTeamColor == ChessGame.TeamColor.BLACK) {
            if (myPosition.chgPosition(-1, 0).inBounds()) {
                ChessPosition moveTo = myPosition.chgPosition(-1, 0);
                ChessPiece targetDest = board.getPiece(moveTo);
                //in the case that there is no piece in the way
                if (targetDest == null) {
                    //in the case that the pawn needs a promotion after moving
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
                    //in the case that the pawn is at starting position
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
            //loop used to determine if pawn can move to capture
            for (int i = -1; i <= 1; i += 2) {
                if (myPosition.chgPosition(-1, i).inBounds()) {
                    ChessPosition moveTo = myPosition.chgPosition(-1, i);
                    ChessPiece targetDest = board.getPiece(moveTo);
                    //checks to make sure there is an enemy piece
                    if (targetDest != null && targetDest.getTeamColor() != myTeamColor) {
                        //in the case that the pawn needs promotion after capturing
                        if(moveTo.getRow() == 1){
                            ChessMove addQn = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.QUEEN);
                            ChessMove addBn = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.BISHOP);
                            ChessMove addKn = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.KNIGHT);
                            ChessMove addRn = new ChessMove(myPosition, moveTo, ChessPiece.PieceType.ROOK);
                            result.add(addQn);
                            result.add(addBn);
                            result.add(addKn);
                            result.add(addRn);
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
