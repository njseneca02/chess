package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public interface GameDAO {
    public int createGame(GameData g) throws DataAccessException;

    public GameData getGame(int id) throws DataAccessException;

    public Collection<GameData> listGames() throws DataAccessException;

    public void clear() throws DataAccessException;

    public void updatePlayer(int id, String username, ChessGame.TeamColor color) throws DataAccessException;

    public HashMap<Integer, GameData> getDatabase();
}
