package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.*;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@WebSocket
public class WebSocketHandler {

    private HashMap<Integer, HashSet<Session>> sessions = new HashMap<>();
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserDAO userDAO;

    public WebSocketHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

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


    private void connect(Session session, String username, ConnectCommand command) throws Exception{
        if(gameDAO.getGame(command.getGameID()) == null){
            sendMessage(session, new ErrorMessage("Error: no game exists for that id"));
        }
        else if(authDAO.getAuth(command.getAuthString()) == null){
            sendMessage(session, new ErrorMessage("Error: not authorized"));
        }
        else {
            sendMessage(session, new LoadGameMessage(gameDAO.getGame(command.getGameID())));
            for (Session sess : sessions.get(command.getGameID())) {
                if (!sess.equals(session)) {
                    if (command.getColor() == null) {
                        sendMessage(sess, new NotificationMessage(username + " joined as observer"));
                    } else {
                        if (command.getColor() == ChessGame.TeamColor.WHITE) {
                            sendMessage(sess, new NotificationMessage(username + " joined as white player"));
                        } else {
                            sendMessage(sess, new NotificationMessage(username + "joined as black player"));
                        }
                    }
                }
            }
        }

    }

    private void makeMove(Session session, String username, MakeMoveCommand command) throws Exception{
        boolean verifiedMove = false;
        GameData game = gameDAO.getGame(command.getGameID());

        Collection<ChessMove> validMoves = game.game().validMoves(command.getMove().getStartPosition());
        for(ChessMove move : validMoves){
            if (move.equals(command.getMove())) {
                verifiedMove = true;
                break;
            }
        }
        if(!(game.blackUsername().equals(username) || game.whiteUsername().equals(username))){
            sendMessage(session, new ErrorMessage("Error: can't make move as observer"));
        }
        else if(game.whiteUsername().equals(username)){
            if(game.game().getBoard().getPiece(command.getMove().getStartPosition()).getTeamColor() == ChessGame.TeamColor.BLACK){
                sendMessage(session, new ErrorMessage("Error: wrong team"));
            }
            else{
                processValidMove(session, game, command, verifiedMove, username);
            }
        }
        else{
            if(game.game().getBoard().getPiece(command.getMove().getStartPosition()).getTeamColor() == ChessGame.TeamColor.WHITE){
                sendMessage(session, new ErrorMessage("Error: wrong team"));
            }
            else {
                processValidMove(session, game, command, verifiedMove, username);
            }
        }
    }

    private void resign(Session session, String username, ResignCommand command) throws IOException{
        sendMessage(session, new NotificationMessage("msg"));
       // sendMessage(session, new LoadGameMessage("msg"));
    }

    private void leave(Session session, String username, LeaveGameCommand command) throws IOException{
        sendMessage(session, new NotificationMessage("msg"));
      //  sendMessage(session, new LoadGameMessage("msg"));
    }

    private String getUsername(String authToken) throws DataAccessException {
        return authDAO.getAuth(authToken).username();
    }

    public void saveSession(int key, Session session) {
        HashSet<Session> sessionSet = sessions.get(key);
        if (sessionSet == null) {
            sessionSet = new HashSet<>();
            sessions.put(key, sessionSet);
        }
        sessionSet.add(session);
    }

    private void sendMessage(Session session, ServerMessage serverMessage) throws IOException{
        session.getRemote().sendString(new Gson().toJson(serverMessage));
    }

    private void processValidMove(Session session, GameData game, MakeMoveCommand command, boolean verifiedMove, String username) throws Exception{
        if (verifiedMove) {
            game.game().makeMove(command.getMove());
            gameDAO.updateGame(command.getGameID(), game.game());
            for (Session sess : sessions.get(command.getGameID())) {
                sendMessage(sess, new LoadGameMessage(game));
                if (!sess.equals(session)) {
                    sendMessage(sess, new NotificationMessage(username + "made a move"));
                }
            }
        } else {
            sendMessage(session, new ErrorMessage("Error: invalid move"));
        }
    }


}
