package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class UtilService {

    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    public UtilService(AuthDAO authDAO, GameDAO gameDAO,  UserDAO userDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    public void clearDatabase() throws DataAccessException {
            authDAO.clear();
            gameDAO.clear();
            userDAO.clear();

    }
}
