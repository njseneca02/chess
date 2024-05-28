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

    public void updateGame(int ID, ChessGame game) throws DataAccessException{
        GameData oldGame = database.get(ID);
        GameData newGame = new GameData(ID, oldGame.whiteUsername(), oldGame.blackUsername(), oldGame.gameName(), game);
        database.replace(ID, oldGame, newGame);
    }

    public void updatePlayer(int ID, String username, ChessGame.TeamColor color) throws DataAccessException{
        GameData oldGame = database.get(ID);
        GameData newGame;
        if(color == ChessGame.TeamColor.WHITE){
            newGame = new GameData(ID, username, oldGame.blackUsername(), oldGame.gameName(), oldGame.game());
        }
        else{
            newGame = new GameData(ID, oldGame.whiteUsername(), username, oldGame.gameName(), oldGame.game());
        }
        database.replace(ID, oldGame, newGame);

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
