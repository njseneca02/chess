package websocket.messages;

public class LoadGameMessage extends ServerMessage{
    public LoadGameMessage(String msg){
        super(ServerMessageType.LOAD_GAME);
    }
}
