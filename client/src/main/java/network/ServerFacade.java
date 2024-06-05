package network;

import com.google.gson.Gson;
import network.request.RegisterRequest;

import java.io.IOException;

public class ServerFacade {

    private final String serverUrl;

    private ClientCommunicator communicator = new ClientCommunicator();
    public ServerFacade(String serverUrl){
        this.serverUrl = serverUrl;
    }

    public void register(String username, String password, String email) throws IOException{
        RegisterRequest reqBody = new RegisterRequest(username, password, email);
        Gson gson = new Gson();
        try{
        String json = communicator.register(serverUrl + "/post", gson.toJson(reqBody));

        }
        catch(IOException e){

        }
    }
}
