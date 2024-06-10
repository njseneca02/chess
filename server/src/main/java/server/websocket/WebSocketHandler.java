package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;

@WebSocket
public class WebSocketHandler {

    private HashMap<String, Session> sessions = new HashMap<>();

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws Exception {
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);

            String username = getUsername(command.getAuthString());

            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, new Gson().fromJson(msg, ConnectCommand.class));
                case MAKE_MOVE -> makeMove(session, username, new Gson().fromJson(msg, MakeMoveCommand.class));
                case LEAVE -> leave(session, username, new Gson().fromJson(msg, LeaveGameCommand.class));
                case RESIGN -> resign(session, username, new Gson().fromJson(msg, ResignCommand.class));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session, new ErrorMessage("Error: " + ex.getMessage()));
        }
    }


    private void connect(Session session, String username, ConnectCommand command) throws IOException{
        sendMessage(session, new LoadGameMessage((new GameData(3, null, null, "game", new ChessGame())), null));
    }

    private void makeMove(Session session, String username, MakeMoveCommand command) throws IOException{
        //sendMessage(session, new LoadGameMessage("msg"));
        sendMessage(session, new NotificationMessage("msg"));
    }

    private void resign(Session session, String username, ResignCommand command) throws IOException{
        sendMessage(session, new NotificationMessage("msg"));
       // sendMessage(session, new LoadGameMessage("msg"));
    }

    private void leave(Session session, String username, LeaveGameCommand command) throws IOException{
        sendMessage(session, new NotificationMessage("msg"));
      //  sendMessage(session, new LoadGameMessage("msg"));
    }

    private String getUsername(String authToken){
        return null;
    }

    private void saveSession(String id, Session session){
        sessions.put(id, session);
    }

    private void sendMessage(Session session, ServerMessage serverMessage) throws IOException{
        session.getRemote().sendString(new Gson().toJson(serverMessage));
    }
}
