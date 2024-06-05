package network;

import com.google.gson.Gson;
import network.request.RegisterRequest;
import network.result.RegisterResult;

import java.io.IOException;

public class ServerFacade {

    private final String serverUrl;

    private ClientCommunicator communicator = new ClientCommunicator();
    public ServerFacade(String serverUrl){
        this.serverUrl = serverUrl;
    }

    public String register(String username, String password, String email) throws IOException{
        RegisterRequest reqBody = new RegisterRequest(username, password, email);
        Gson gson = new Gson();
        try{
            String json = communicator.register(serverUrl + "/user", gson.toJson(reqBody));
            RegisterResult resBody = gson.fromJson(json, RegisterResult.class);
            if(resBody.message() != null){
                throw new IOException(resBody.message());
            }
            else{
                return resBody.authToken();
            }
            }
        catch(IOException e){
            throw new IOException(e.getMessage());
        }
    }
}
