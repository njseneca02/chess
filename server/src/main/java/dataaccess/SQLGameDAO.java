package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.sql.*;
import java.util.*;

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
        HashSet<GameData> gameList = new HashSet<>();
        var sql = "SELECT * from game";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id");
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    String gameName = rs.getString("gameName");
                    String chessJson = rs.getString("chessGame");
                    Gson gson = new Gson();
                    ChessGame chess = (ChessGame)gson.fromJson(chessJson, ChessGame.class);

                    GameData game = new GameData(id, whiteUsername, blackUsername, gameName, chess);
                    gameList.add(game);

                }
                return gameList;
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void clear() throws DataAccessException {
        var sql = "DELETE from game";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void updatePlayer(int id, String username, ChessGame.TeamColor color) throws DataAccessException {
        if(color == ChessGame.TeamColor.WHITE){
            var sql = "UPDATE game set whiteUsername = ? where id = ?";

            try(Connection connection = DatabaseManager.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setString(1, username);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                }
            }
            catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }
        else if(color == ChessGame.TeamColor.BLACK){
            var sql = "UPDATE game set blackUsername = ? where id = ?";

            try(Connection connection = DatabaseManager.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setString(1, username);
                    statement.setInt(2, id);
                    statement.executeUpdate();
                }
            }
            catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }

    }

    public HashMap<Integer, GameData> getDatabase() {
        return null;
    }
}
