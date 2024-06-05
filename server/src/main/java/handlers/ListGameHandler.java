package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import result.ListGameResult;
import service.GameService;

public class ListGameHandler {

    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public ListGameHandler(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public String handleRequest(spark.Request req, spark.Response res){
        var gson = new Gson();
        String authToken = req.headers("authorization");

        GameService service = new GameService(authDAO, gameDAO);

        ListGameResult result;

        try{
            result = service.listGames(authToken);
            if(result.message() != null && result.message().contains("Error: unauthorized")){
                res.status(401);
            }
            else{
                res.status(200);
            }
        }
        catch(DataAccessException e){
            res.status(500);
            result = new ListGameResult("Error: " + e.getMessage(), null);
        }
        res.body(gson.toJson(result));
        return gson.toJson(result);
    }
}
