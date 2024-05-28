package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.NoBodyResult;
import result.RegisterResult;


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
        UserData actual;
        try {

            RegisterRequest request = new RegisterRequest("username", "password", "email");
            userService.register(request);
            actual = userDAO.getUser("username");
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            actual = null;
        }
        Assertions.assertTrue(userDAO.getDatabase().size() == 1);
        Assertions.assertEquals(new UserData("username", "password", "email"), actual);
        Assertions.assertTrue(authDAO.getDatabase().size() == 1);
    }

    @Test
    public void testRegisterFail(){
        try {

            RegisterRequest request = new RegisterRequest("username", "password", null);
            userService.register(request);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(userDAO.getDatabase().isEmpty());
        Assertions.assertTrue(authDAO.getDatabase().isEmpty());
    }

    @Test
    public void testLoginSuccess(){
        LoginResult result;
        try {

            RegisterRequest request = new RegisterRequest("username", "password", "email");
            userService.register(request);
            LoginRequest loginRequest = new LoginRequest("username", "password");
            result = userService.login(loginRequest);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            result = null;
        }
        Assertions.assertEquals(result.username(), "username");
        Assertions.assertTrue(authDAO.getDatabase().size() == 2);

    }

    @Test
    public void testLoginFail(){
        LoginResult result;
        try {

            RegisterRequest request = new RegisterRequest("username", "password", "email");
            userService.register(request);
            LoginRequest loginRequest = new LoginRequest("username", "pass");
            result = userService.login(loginRequest);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            result = null;
        }
        Assertions.assertEquals(result.username(), null);
        Assertions.assertTrue(authDAO.getDatabase().size() == 1);
    }

    @Test
    public void testLogoutSuccess(){
        try {
            RegisterRequest request = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(request);
            String authToken = result.authToken();
            userService.logout(authToken);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(userDAO.getDatabase().size() == 1);
        Assertions.assertTrue(authDAO.getDatabase().isEmpty());
    }

    @Test
    public void testLogoutFail(){
        NoBodyResult result;
        try {
            RegisterRequest request = new RegisterRequest("username", "password", "email");
            userService.register(request);
            result = userService.logout("1234");
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            result = null;
        }
        Assertions.assertTrue(userDAO.getDatabase().size() == 1);
        Assertions.assertTrue(authDAO.getDatabase().size() == 1);
        Assertions.assertTrue(result.message().contains("unauthorized"));
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
