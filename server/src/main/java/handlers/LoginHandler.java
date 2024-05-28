package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import request.LoginRequest;
import result.LoginResult;
import service.UserService;

public class LoginHandler {

    private AuthDAO authDAO;
    private UserDAO userDAO;

    public LoginHandler(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public String handleRequest(spark.Request req, spark.Response res){
        var gson = new Gson();

        LoginRequest request = (LoginRequest)gson.fromJson(req.body(), LoginRequest.class);

        UserService service = new UserService(authDAO, userDAO);
        LoginResult result;

        try{
            result = service.login(request);
            if(result.message() != null && result.message().contains("Error: unauthorized")){
                res.status(401);
            }
            else{
                res.status(200);
            }
        }
        catch(DataAccessException e){
            res.status(500);
            result = new LoginResult("Error: " + e.getMessage(), null, null);
        }

        return gson.toJson(result);

    }
}
