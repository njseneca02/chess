package websocket.messages;

import chess.ChessGame;
import model.GameData;

public class LoadGameMessage extends ServerMessage{
    GameData game;
    String message;
    public LoadGameMessage(GameData game, String message){
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
        this.message = message;
    }

    public ChessGame getGame(){
        return game.game();
    }

    public String getMessage(){
        return message;
    }
}
