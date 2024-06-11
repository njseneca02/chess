package websocket.commands;

import chess.ChessGame;

public class ConnectCommand extends UserGameCommand {

    private ChessGame.TeamColor color;

    public ConnectCommand(String authToken,int gameID, ChessGame.TeamColor color) {
        super(authToken, gameID);
        this.commandType = CommandType.CONNECT;
        this.color = color;
    }

    public ChessGame.TeamColor getColor(){
        return color;
    }
}
