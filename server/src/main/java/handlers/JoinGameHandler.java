package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import request.JoinGameRequest;
import result.NoBodyResult;
import service.GameService;

public class JoinGameHandler {

    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public JoinGameHandler(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public String handleRequest(spark.Request req, spark.Response res){
        var gson = new Gson();
        String authToken = req.headers("authorization");

        JoinGameRequest request = (JoinGameRequest)gson.fromJson(req.body(), JoinGameRequest.class);

        GameService service = new GameService(authDAO, gameDAO);

        NoBodyResult result;

        try{
            result = service.joinGame(request, authToken);
            if(result.message() != null){
                if(result.message().contains("Error: unauthorized")){
                    res.status(401);
                }
                else if(result.message().contains("bad request")){
                    res.status(400);
                }
                else if(result.message().contains("already taken")){
                    res.status(403);
                }
            }
            else{
                res.status(200);
            }
        }
        catch(DataAccessException e){
            res.status(500);
            result = new NoBodyResult("Error: " + e.getMessage());
        }

        return gson.toJson(result);
    }

}
