package handlers;

import com.google.gson.Gson;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

public class RegisterHandler extends Handler{

    public String handleRequest(spark.Request req, spark.Response res){

        var gson = new Gson();

        RegisterRequest request = (RegisterRequest)gson.fromJson(req.body(), RegisterRequest.class);

        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);

        return gson.toJson(result);

    }
}
