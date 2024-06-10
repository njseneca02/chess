package network;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.GameData;
import network.request.*;
import network.result.*;
import ui.ChessClient;

import java.io.IOException;
import java.util.Collection;

public class ServerFacade {

    private final String serverUrl;

    private HttpCommunicator httpCommunicator = new HttpCommunicator();
    private WebsocketCommunicator websocketCommunicator;

    public ServerFacade(ChessClient client, String serverUrl){
        this.serverUrl = serverUrl;
        try{
            websocketCommunicator = new WebsocketCommunicator(client, this.serverUrl);
        }
        catch(ResponseException e){
            System.out.println(e.getMessage());
        }
    }

    public String register(String username, String password, String email) throws IOException{
        RegisterRequest reqBody = new RegisterRequest(username, password, email);
        Gson gson = new Gson();
        try{
            String json = httpCommunicator.register(serverUrl + "/user", gson.toJson(reqBody));
            RegisterResult resBody = gson.fromJson(json, RegisterResult.class);
            if(resBody.message() != null){
                throw new IOException(resBody.message());
            }
            else{
                return resBody.authToken();
            }
            }
        catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

    public Collection<GameData> listGames(String authToken) throws IOException{
        Gson gson = new Gson();
        try{
            String json = httpCommunicator.listGames(serverUrl + "/game", authToken);
            ListGameResult resBody = gson.fromJson(json, ListGameResult.class);
            if(resBody.message() != null){
                throw new IOException(resBody.message());
            }
            else{
                return resBody.games();
            }
        }
        catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

    public void createGame(String gameName, String authToken) throws IOException{
        Gson gson = new Gson();
        CreateGameRequest reqBody = new CreateGameRequest(gameName);
        try{
            String json = httpCommunicator.createGame(serverUrl + "/game", gson.toJson(reqBody), authToken);
            CreateGameResult resBody = gson.fromJson(json, CreateGameResult.class);
            if(resBody.message() != null){
                throw new IOException(resBody.message());
            }
        }
        catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

    public String login(String username, String password) throws IOException{
        LoginRequest reqBody = new LoginRequest(username, password);
        Gson gson = new Gson();
        try{
            String json = httpCommunicator.login(serverUrl + "/session", gson.toJson(reqBody));
            LoginResult resBody = gson.fromJson(json, LoginResult.class);
            if(resBody.message() != null){
                throw new IOException(resBody.message());
            }
            else{
                return resBody.authToken();
            }
        }
        catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

    public void logout(String authToken) throws IOException{
        Gson gson = new Gson();
        try{
            String json = httpCommunicator.logout(serverUrl + "/session", authToken);
            NoBodyResult resBody = gson.fromJson(json, NoBodyResult.class);
            if(resBody.message() != null){
                throw new IOException(resBody.message());
            }
        }
        catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

    public void joinGame(GameData game, String authToken, String teamColor) throws IOException{
        Gson gson = new Gson();
        JoinGameRequest reqBody;
        if(teamColor.equals("white")){
            reqBody = new JoinGameRequest(ChessGame.TeamColor.WHITE, String.valueOf(game.gameID()));
        }
        else{
            reqBody = new JoinGameRequest(ChessGame.TeamColor.BLACK, String.valueOf(game.gameID()));
        }

        try{
            String json = httpCommunicator.joinGame(serverUrl + "/game", gson.toJson(reqBody), authToken);
            NoBodyResult resBody = gson.fromJson(json, NoBodyResult.class);
            if(resBody.message() != null){
                throw new IOException(resBody.message());
            }
        }
        catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }

}
