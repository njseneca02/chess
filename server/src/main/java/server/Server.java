package server;

import dataaccess.*;
import handlers.ClearHandler;
import handlers.LoginHandler;
import handlers.LogoutHandler;
import handlers.RegisterHandler;
import spark.*;

public class Server {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    public Server(){
        this.authDAO = new MemoryAuthDAO();
        this.gameDAO = new MemoryGameDAO();
        this.userDAO = new MemoryUserDAO();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
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
    }
}
