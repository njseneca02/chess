package server;

import handlers.RegisterHandler;
import spark.*;

public class Server {

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
                        (new RegisterHandler()).handleRequest(req,res));

        //Spark.get()
    }
}
