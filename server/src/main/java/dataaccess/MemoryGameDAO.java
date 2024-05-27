package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    private HashMap<Integer, GameData> database;
    private int IDCounter = 1;
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

    public void updateGame(int ID, ChessGame chessGame) throws DataAccessException{
        GameData oldGame = database.get(ID);
        GameData newGame = new GameData(oldGame.gameID(), oldGame.whiteUsername(), oldGame.blackUsername(), oldGame.gameName(), chessGame);
        database.replace(ID, oldGame, newGame);
    }

    public int getIDCounter(){
        IDCounter++;
        return IDCounter - 1;
    }

    public void clear() throws DataAccessException{
        database.clear();
    }
}
