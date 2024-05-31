package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class SQLAuthDAO implements AuthDAO{
    public void createAuth(AuthData u) throws DataAccessException {
        var sql = "INSERT into auth (authToken, username) values (?, ?)";

        try(Connection connection = Utils.createConnection()) {
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
        return null;
    }

    public void deleteAuth(AuthData u) throws DataAccessException {

    }

    public void clear() throws DataAccessException {

    }

    public Collection<AuthData> getDatabase() {
        return List.of();
    }
}
