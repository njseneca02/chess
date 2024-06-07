package server;

import dataaccess.*;
import handlers.*;
import model.GameData;
import spark.*;

import javax.xml.crypto.Data;

public class Server {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    public Server(){
        this.authDAO = new SQLAuthDAO();
        this.gameDAO = new SQLGameDAO();
        this.userDAO = new SQLUserDAO();
    }

    public int run(int desiredPort) {

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        registerEndpoints();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public void registerEndpoints(){
        Spark.post("/user", (req, res) ->
                        (new RegisterHandler(authDAO, userDAO)).handleRequest(req,res));

        Spark.delete("/db", (req, res) ->
                (new ClearHandler(authDAO, gameDAO, userDAO).handleRequest(req, res)));

        Spark.post("/session", (req, res) ->
                (new LoginHandler(authDAO, userDAO).handleRequest(req, res)));

        Spark.delete("/session", (req, res) ->
                (new LogoutHandler(authDAO, userDAO).handleRequest(req, res)));

        Spark.get("/game", (req, res) ->
                (new ListGameHandler(authDAO, gameDAO).handleRequest(req, res)));

        Spark.post("/game", (req, res) ->
                (new CreateGameHandler(authDAO, gameDAO).handleRequest(req, res)));

        Spark.put("/game", (req, res) ->
                (new JoinGameHandler(authDAO, gameDAO).handleRequest(req, res)));
    }

    //methods for testing purposes

    public void clearDatabases(){
        try {
            authDAO.clear();
            userDAO.clear();
            gameDAO.clear();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
    }

    public GameData getGame(){
        try {
            for (GameData game : gameDAO.listGames()) {
                return game;
            }
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
