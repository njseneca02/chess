package dataaccess;

import java.sql.Connection;
import java.sql.SQLException;

public class Utils {

    static Connection createConnection() throws SQLException {
        Connection connection = null;
        try(Connection c = DatabaseManager.createConnection()){
            connection = c;
        }
        catch(DataAccessException e){
            throw new SQLException(e.getMessage());
        }
        return connection;
    }
}
