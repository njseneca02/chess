package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ServiceUnitTests {

    private AuthDAO authDAO = new MemoryAuthDAO();
    private GameDAO gameDAO = new MemoryGameDAO();
    private UserDAO userDAO = new MemoryUserDAO();

    private UtilService utilService = new UtilService(authDAO, gameDAO, userDAO);
    private UserService userService = new UserService(authDAO, userDAO);
    private GameService gameService = new GameService(authDAO, gameDAO);

    @BeforeEach
    public void clear(){
        try{
            utilService.clearDatabase();
        }
        catch(DataAccessException e){
            System.out.println("clear for tests has failed: " + e.getMessage());
        }
    }

    @Test
    public void testClearService(){
        try{
            authDAO.createAuth(new AuthData("hello", "nathan"));
            gameDAO.createGame(new GameData(1, "a", "b", "game", new ChessGame()));
            userDAO.createUser(new UserData("n", "pass", "email"));
            utilService.clearDatabase();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(authDAO.getDatabase().isEmpty());
        Assertions.assertTrue(gameDAO.getDatabase().isEmpty());
        Assertions.assertTrue(userDAO.getDatabase().isEmpty());


    }

    @Test
    public void testRegisterSuccess(){

    }

    @Test
    public void testRegisterFail(){

    }

    @Test
    public void testLoginSuccess(){

    }

    @Test
    public void testLoginFail(){

    }

    @Test
    public void testLogoutSuccess(){

    }

    @Test
    public void testLogoutFail(){

    }

    @Test
    public void testListGamesSuccess(){

    }

    @Test
    public void testListGamesFail(){

    }

    @Test
    public void testCreateGameSuccess(){

    }

    @Test
    public void testCreateGameFail(){

    }

    @Test
    public void testJoinGameSuccess(){

    }

    @Test
    public void testJoinGameFail(){

    }
}
