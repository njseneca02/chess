package dataaccess;

import model.AuthData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SQLAuthDAO implements AuthDAO{

    static {
        try {
            DatabaseManager.createDatabase();
        }
        catch(DataAccessException e){
            System.out.println("failed to create Database");
        }
    }

    public void createAuth(AuthData u) throws DataAccessException {
        var sql = "INSERT into auth (authToken, username) values (?, ?)";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, u.authToken());
                statement.setString(2, u.username());

                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public AuthData getAuth(String token) throws DataAccessException {
        String authToken;
        String username;

        var sql = "SELECT authToken, username FROM auth WHERE authToken = ?";
        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, token);
                ResultSet rs = statement.executeQuery();
                rs.next();
                authToken = rs.getString(1);
                username = rs.getString(2);

                return new AuthData(authToken, username);
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void deleteAuth(AuthData u) throws DataAccessException {
        var sql = "DELETE from auth WHERE authToken = ?";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, u.authToken());

                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void clear() throws DataAccessException {
        var sql = "DELETE from auth";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public Collection<AuthData> getDatabase() throws DataAccessException{

        ArrayList<AuthData> authList = new ArrayList<>();
        var sql = "SELECT * from auth";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    String authToken = rs.getString("authToken");
                    String username = rs.getString("username");

                    AuthData auth = new AuthData(authToken, username);
                    authList.add(auth);

                }
                return authList;
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
