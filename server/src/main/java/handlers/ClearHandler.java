package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import result.NoBodyResult;
import result.RegisterResult;
import service.UtilService;

public class ClearHandler {

    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    public ClearHandler(AuthDAO authDAO, GameDAO gameDAO,  UserDAO userDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    public String handleRequest(spark.Request req, spark.Response res){

        var gson = new Gson();

        UtilService service = new UtilService(authDAO, gameDAO, userDAO);
        NoBodyResult result;

        try{
            service.clearDatabase();
            res.status(200);
            result = new NoBodyResult(null);
        }
        catch(DataAccessException e){
            res.status(500);
            result = new NoBodyResult("Error: " + e.getMessage());
        }
        return gson.toJson(result);
    }
}
