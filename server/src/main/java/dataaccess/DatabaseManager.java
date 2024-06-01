package dataaccess;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) throw new Exception("Unable to load db.properties");
                Properties props = new Properties();
                props.load(propStream);
                DATABASE_NAME = props.getProperty("db.name");
                USER = props.getProperty("db.user");
                PASSWORD = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                CONNECTION_URL = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    static void createTables() throws DataAccessException {
        try{
            var statementGame = """
                    CREATE TABLE IF NOT EXISTS game
                    (
                    id integer not null primary key auto_increment,
                    whiteUsername varchar(255) not null,
                    blackUsername varchar(255) not null,
                    gameName varchar(255) not null,
                    chessGame varchar(4095) not null
                    );
                    """;
            var statementAuth = """
                    CREATE TABLE IF NOT EXISTS auth
                    (
                    authToken varchar(255) not null primary key,
                    username varchar(255) not null
                    );
                    """;
            var statementUser = """
                    CREATE TABLE IF NOT EXISTS user
                    (
                    username varchar(255) not null primary key,
                    password varchar(255) not null,
                    email varchar(255) not null
                    )
                    """;
            try(Connection connection = getConnection()) {
                connection.setAutoCommit(false);
                try (var preparedStatement = connection.createStatement()) {
                    preparedStatement.executeUpdate(statementGame);
                    preparedStatement.executeUpdate(statementUser);
                    preparedStatement.executeUpdate(statementAuth);
                }
            }

        }catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
            createTables();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            conn.setCatalog(DATABASE_NAME);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

}
