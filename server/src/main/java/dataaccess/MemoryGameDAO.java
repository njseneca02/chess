package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    private HashMap<Integer, GameData> database;
    private int idCounter = 1;

    public MemoryGameDAO(){
        this.database = new HashMap<Integer, GameData>();
    }

    public void createGame(GameData g) throws DataAccessException{
        database.put(g.gameID(), g);
    }

    public GameData getGame(int ID) throws DataAccessException{
        return database.get(ID);
    }

    public Collection<GameData> listGames() throws DataAccessException{
        return database.values();
    }

    public void updateGame(int id, ChessGame game) throws DataAccessException{
        GameData oldGame = database.get(id);
        GameData newGame = new GameData(id, oldGame.whiteUsername(), oldGame.blackUsername(), oldGame.gameName(), game);
        database.replace(id, oldGame, newGame);
    }

    public void updatePlayer(int id, String username, ChessGame.TeamColor color) throws DataAccessException{
        GameData oldGame = database.get(id);
        GameData newGame;
        if(color == ChessGame.TeamColor.WHITE){
            newGame = new GameData(id, username, oldGame.blackUsername(), oldGame.gameName(), oldGame.game());
        }
        else{
            newGame = new GameData(id, oldGame.whiteUsername(), username, oldGame.gameName(), oldGame.game());
        }
        database.replace(id, oldGame, newGame);

    }

    public int getIDCounter(){
        idCounter++;
        return idCounter - 1;
    }

    public void clear() throws DataAccessException{
        database.clear();
    }

    public HashMap<Integer, GameData> getDatabase(){
        return database;
    }
}
