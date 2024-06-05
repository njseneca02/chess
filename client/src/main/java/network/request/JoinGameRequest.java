package network.request;

import chess.ChessGame;

public record JoinGameRequest(ChessGame.TeamColor playerColor, String gameID) {
}
