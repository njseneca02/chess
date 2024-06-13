package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import result.CreateGameResult;
import result.ListGameResult;
import result.NoBodyResult;

public class GameService {

    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public ListGameResult listGames(String authToken) throws DataAccessException {
        if(authToken == null){
            return new ListGameResult("Error: unauthorized", null);
        }

        ListGameResult result;

        AuthData auth = authDAO.getAuth(authToken);
        if(auth != null){
            result = new ListGameResult(null, gameDAO.listGames());
        }
        else{
            result = new ListGameResult("Error: unauthorized", null);
        }
        return result;
    }

    public CreateGameResult createGame(CreateGameRequest request, String authToken) throws DataAccessException{
        if(authToken == null){
            return new CreateGameResult("Error: unauthorized", null);
        }

        CreateGameResult result;
        AuthData auth = authDAO.getAuth(authToken);

        if(request.gameName() == null){
            result = new CreateGameResult("Error: bad request", null);
        }

        else if(auth != null){
            int gameId = gameDAO.createGame(new GameData(0, null, null, request.gameName(), false, new ChessGame()));
            result = new CreateGameResult(null, String.valueOf(gameId));
        }

        else{
            result = new CreateGameResult("Error: unauthorized", null);
        }
        return result;
    }

    public NoBodyResult joinGame(JoinGameRequest request, String authToken) throws DataAccessException{
        if(authToken == null){
            return new NoBodyResult("Error: unauthorized");
        }

        NoBodyResult result;
        AuthData auth = authDAO.getAuth(authToken);

        if(request.gameID() == null || (request.playerColor() != null && !(request.playerColor().equalsIgnoreCase("white") || request.playerColor().equalsIgnoreCase("black")))){
            result = new NoBodyResult("Error: bad request");
        }

        else if(auth != null){
            GameData game = gameDAO.getGame(Integer.parseInt(request.gameID()));
            if(game != null){
                if(request.playerColor() == null){
                    result = new NoBodyResult("Error: bad request");
                }
                else if(request.playerColor().equalsIgnoreCase("black") && game.blackUsername() != null) {
                        result = new NoBodyResult("Error: already taken");
                }
                else if(request.playerColor().equalsIgnoreCase("white") && game.whiteUsername() != null){
                        result = new NoBodyResult("Error: already taken");
                }
                else{
                    if(request.playerColor().equalsIgnoreCase("white")){
                        gameDAO.updatePlayer(Integer.parseInt(request.gameID()), authDAO.getAuth(authToken).username(), ChessGame.TeamColor.WHITE);
                    }
                    else if(request.playerColor().equalsIgnoreCase("black")){
                        gameDAO.updatePlayer(Integer.parseInt(request.gameID()), authDAO.getAuth(authToken).username(), ChessGame.TeamColor.BLACK);
                    }
                    result = new NoBodyResult(null);
                }
            }
            else{
                result = new NoBodyResult("Error: bad request");
            }
        }

        else{
            result = new NoBodyResult("Error: unauthorized");
        }
        return result;
    }

}
