package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.util.HashMap;

@WebSocket
public class WebSocketHandler {

    private HashMap<String, Session> sessions = new HashMap<>();

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws Exception {
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
            if(command.getCommandType() == UserGameCommand.CommandType.CONNECT){
                command = new Gson().fromJson(msg, ConnectCommand.class);
            }
            else if(command.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE){
                command = new Gson().fromJson(msg, MakeMoveCommand.class);
            }
            else if(command.getCommandType() == UserGameCommand.CommandType.LEAVE){
                command = new Gson().fromJson(msg, LeaveGameCommand.class);
            }
            else if(command.getCommandType() == UserGameCommand.CommandType.RESIGN){
                command = new Gson().fromJson(msg, ResignCommand.class);
            }

            String username = getUsername(command.getAuthString());

            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, (ConnectCommand) command);
                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
                case LEAVE -> leave(session, username, (LeaveGameCommand) command);
                case RESIGN -> resign(session, username, (ResignCommand) command);
            }
        } catch (UnauthorizedException ex) {
            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }
    }


    private void connect(Session session, String username, ConnectCommand command){

    }

    private void makeMove(Session session, String username, MakeMoveCommand command){

    }

    private void resign(Session session, String username, LeaveGameCommand command){

    }

    private void leave(Session session, String username, ResignCommand command){

    }

    private String getUsername(String authToken){
        return null;
    }

    private void saveSession(String id, Session session){
        sessions.put(id, session);
    }
}
