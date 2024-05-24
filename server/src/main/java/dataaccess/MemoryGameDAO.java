package dataaccess;

import model.GameData;

import java.util.Collection;

public class MemoryGameDAO implements GameDAO{

    public void createGame(GameData g) throws DataAccessException{

    }

    public GameData getGame(String ID) throws DataAccessException{
        return null;
    }

    public Collection<GameData> listGames() throws DataAccessException{
        return null;
    }

    public void updateGame(String ID) throws DataAccessException{

    }
}
