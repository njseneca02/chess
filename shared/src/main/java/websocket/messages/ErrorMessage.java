package websocket.messages;

public class ErrorMessage extends ServerMessage{
    public ErrorMessage(String msg){
        super(ServerMessageType.ERROR);
    }
}
