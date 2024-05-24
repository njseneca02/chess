package handlers;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;

public class Handler {
    private AuthDAO authDAO;
    private UserDAO userDAO;

    public Handler(AuthDAO authDAO, UserDAO userDAO){
        this.authDAO = authDAO;
        this. userDAO = userDAO;
    }
}
