package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import result.NoBodyResult;
import service.UserService;

public class LogoutHandler {

    private AuthDAO authDAO;
    private UserDAO userDAO;

    public LogoutHandler(AuthDAO authDAO, UserDAO userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public String handleRequest(spark.Request req, spark.Response res){
        var gson = new Gson();
        String authToken = req.headers("authorization");

        UserService service = new UserService(authDAO, userDAO);

        NoBodyResult result;

        try{
            result = service.logout(authToken);
            if(result.message() != null && result.message().contains("Error: unauthorized")){
                res.status(401);
            }
            else{
                res.status(200);
            }
        }
        catch(DataAccessException e){
            res.status(500);
            result = new NoBodyResult("Error: " + e.getMessage());
        }
        res.body(gson.toJson(result));
        return gson.toJson(result);
    }
}
