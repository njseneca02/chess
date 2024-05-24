package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import request.RegisterRequest;
import result.RegisterResult;

import java.util.UUID;

public class RegisterService {

    private AuthDAO authDAO;
    private UserDAO userDAO;
    public RegisterService(AuthDAO authDAO, UserDAO userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }


    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        if(request.username() == null || request.password() == null || request.email() == null){
            return new RegisterResult("Error: bad request", null, null);
        }
        else if(userDAO.getUser(request.username()) != null){
            return new RegisterResult("Error: already taken", null, null);
        }
        UserData newUser = new UserData(request.username(), request.password(), request.email());
        userDAO.createUser(newUser);
        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
        authDAO.createAuth(newAuth);
        return new RegisterResult(null, newAuth.username(), newAuth.authToken());


    }
}
