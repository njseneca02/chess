package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.NoBodyResult;
import result.RegisterResult;

import java.util.UUID;

public class UserService {

    private AuthDAO authDAO;
    private UserDAO userDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO){
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

    public LoginResult login(LoginRequest request) throws DataAccessException {

        LoginResult result;

        UserData user = userDAO.getUser(request.username());
        if(user != null && user.password().equals(request.password())){
            AuthData newAuth = new AuthData(UUID.randomUUID().toString(), request.username());
            authDAO.createAuth(newAuth);
            result = new LoginResult(null, request.username(), newAuth.authToken());
        }
        else{
            result = new LoginResult("Error: unauthorized", null, null);
        }
        return result;
    }

    public NoBodyResult logout(String authToken) throws DataAccessException{

        if(authToken == null){
            return new NoBodyResult("Error: unauthorized");
        }

        NoBodyResult result;

        AuthData auth = authDAO.getAuth(authToken);
        if(auth != null){
            authDAO.deleteAuth(auth);
            result = new NoBodyResult(null);
        }
        else{
            result = new NoBodyResult("Error: unauthorized");
        }
        return result;
    }
}
