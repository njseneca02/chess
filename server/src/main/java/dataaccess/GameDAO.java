package dataaccess;

import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public interface GameDAO {
    public void createGame(GameData g) throws DataAccessException;

    public GameData getGame(String ID) throws DataAccessException;

    public Collection<GameData> listGames() throws DataAccessException;

    public void updateGame(String ID) throws DataAccessException;
}
