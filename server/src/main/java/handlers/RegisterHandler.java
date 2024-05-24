package handlers;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import request.RegisterRequest;
import result.RegisterResult;
import service.UserService;

public class RegisterHandler extends Handler{

    private AuthDAO authDAO;
    private UserDAO userDAO;
    public RegisterHandler(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public String handleRequest(spark.Request req, spark.Response res){

        var gson = new Gson();

        RegisterRequest request = (RegisterRequest)gson.fromJson(req.body(), RegisterRequest.class);

        UserService service = new UserService(authDAO, userDAO);
        RegisterResult result;
        try {
            result = service.register(request);
            if(result.message() == null){
                res.status(200);
            }
            else if(result.message().contains("bad request")){
                res.status(400);
            }
            else if(result.message().contains("already taken")){
                res.status(403);
            }

        }
        catch(DataAccessException e){
            res.status(500);
            result = new RegisterResult("Error: " + e.getMessage(), null, null);
        }
        return gson.toJson(result);

    }
}
