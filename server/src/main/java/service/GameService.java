package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
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

}
