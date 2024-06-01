package dataaccess;

import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

public class SQLUserDAO implements UserDAO{

    public void createUser(UserData u) throws DataAccessException {
        var sql = "INSERT into user (username, password, email) values (?, ?, ?)";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, u.username());
                statement.setString(2, u.password());
                statement.setString(3, u.email());

                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public UserData getUser(String username) throws DataAccessException {
        String user;
        String password;
        String email;

        var sql = "SELECT username, password, email FROM user WHERE username = " + username;
        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet rs = statement.executeQuery();
                rs.next();
                user = rs.getString(1);
                password = rs.getString(2);
                email = rs.getString(3);

                return new UserData(user, password, email);
            }
        }
        catch (SQLException e) {
            return null;
            //throw new DataAccessException(e.getMessage());
        }
    }


    public void clear() throws DataAccessException {
        var sql = "DELETE from user";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public Collection<UserData> getDatabase() throws DataAccessException{
        HashSet<UserData> userSet = new HashSet<>();
        var sql = "SELECT * from user";

        try(Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String email = rs.getString("email");

                    UserData user = new UserData(username, password, email);
                    userSet.add(user);

                }
                return userSet;
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
