package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SQLGameDAO implements GameDAO{
    public int createGame(GameData g) throws DataAccessException {
        var sql = "INSERT into game (whiteUsername, blackUsername, gameName, chessGame) values (?, ?, ?, ?)";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                Gson gson = new Gson();
                statement.setString(1, g.whiteUsername());
                statement.setString(2, g.blackUsername());
                statement.setString(3, g.gameName());
                statement.setString(4, gson.toJson(g.game()));


                if(statement.executeUpdate() == 1) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        generatedKeys.next();
                        return generatedKeys.getInt(1);

                    }
                }
                return 0;
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public GameData getGame(int id) throws DataAccessException {

        String white;
        String black;
        String gameName;
        String chessJson;

        var sql = "SELECT whiteUsername, blackUsername, gameName, chessGame FROM game WHERE id = ?";
        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                Gson gson = new Gson();
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();
                rs.next();
                white = rs.getString(1);
                black = rs.getString(2);
                gameName = rs.getString(3);
                chessJson = rs.getString(4);
                ChessGame chess = (ChessGame)gson.fromJson(chessJson, ChessGame.class);
                return new GameData(id, white, black, gameName, chess);
            }
        }
        catch (SQLException e) {
            return null;
        }
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
