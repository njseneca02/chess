package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SQLGameDAO implements GameDAO{
    public int createGame(GameData g) throws DataAccessException {
        return 0;
    }

    public GameData getGame(int id) throws DataAccessException {
        return null;
    }

    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    public void clear() throws DataAccessException {

    }

    public void updatePlayer(int id, String username, ChessGame.TeamColor color) throws DataAccessException {

    }

    public HashMap<Integer, GameData> getDatabase() {
        return null;
    }
}
