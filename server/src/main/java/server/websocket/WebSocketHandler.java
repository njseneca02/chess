package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException{
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch(command.getCommandType()){
            case CONNECT -> connect();
            case MAKE_MOVE -> makeMove();
            case RESIGN -> resign();
            case LEAVE -> leave();
        }
    }

    private void connect(){

    }

    private void makeMove(){

    }

    private void resign(){

    }

    private void leave(){

    }
}
