package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import request.CreateGameRequest;
import result.CreateGameResult;
import service.GameService;

public class CreateGameHandler {

    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public CreateGameHandler(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public String handleRequest(spark.Request req, spark.Response res){
        var gson = new Gson();

        String authToken = req.headers("authorization");

        CreateGameRequest request = (CreateGameRequest)gson.fromJson(req.body(), CreateGameRequest.class);

        GameService service = new GameService(authDAO, gameDAO);
        CreateGameResult result;

        try {
            result = service.createGame(request, authToken);
            if(result.message() == null){
                res.status(200);
            }
            else if(result.message().contains("bad request")){
                res.status(400);
            }
            else if(result.message().contains("unauthorized")){
                res.status(401);
            }
        }
        catch(DataAccessException e){
            res.status(500);
            result = new CreateGameResult("Error: " + e.getMessage(), null);
        }
        res.body(gson.toJson(result));
        return gson.toJson(result);
    }
}
