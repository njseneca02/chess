package dataaccess;

import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public interface GameDAO {
    public void createGame(GameData g) throws DataAccessException;

    public GameData getGame(int ID) throws DataAccessException;

    public Collection<GameData> listGames() throws DataAccessException;

    public void updateGame(int ID, ChessGame chessGame) throws DataAccessException;

    public void clear() throws DataAccessException;

    public int getIDCounter();
}
