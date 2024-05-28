package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> result = new ArrayList<ChessMove>();
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();

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
