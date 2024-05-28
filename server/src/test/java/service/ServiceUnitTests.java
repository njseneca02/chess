package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.LoginRequest;
import request.RegisterRequest;
import result.ListGameResult;
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
        ListGameResult listResult;
        try {
            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);
            String authToken = result.authToken();
            CreateGameRequest request = new CreateGameRequest("gameName");
            gameService.createGame(request, authToken);
            listResult = gameService.listGames(authToken);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            listResult = null;
        }
        Assertions.assertTrue(listResult.games().size() == 1);
        Assertions.assertTrue(listResult != null);
    }


    @Test
    public void testListGamesFail(){
        ListGameResult listResult;
        try {
            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);
            String authToken = result.authToken();
            CreateGameRequest request = new CreateGameRequest("gameName");
            gameService.createGame(request, authToken);
            listResult = gameService.listGames("1234");
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            listResult = null;
        }
        Assertions.assertEquals(null, listResult.games());
        Assertions.assertTrue(listResult.message().contains("unauthorized"));
    }

    @Test
    public void testCreateGameSuccess(){
        GameData game;
        try {
            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);
            String authToken = result.authToken();
            CreateGameRequest request = new CreateGameRequest("gameName");
            gameService.createGame(request, authToken);
            game = gameDAO.getGame(1);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            game = null;
        }
        Assertions.assertTrue(gameDAO.getDatabase().size() == 1);
        Assertions.assertTrue(game != null);
        Assertions.assertEquals("gameName", game.gameName());
    }

    @Test
    public void testCreateGameFail(){
        try {
            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);
            String authToken = result.authToken();
            CreateGameRequest request = new CreateGameRequest(null);
            gameService.createGame(request, authToken);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(gameDAO.getDatabase().isEmpty());
    }

    @Test
    public void testJoinGameSuccess(){
        GameData game;
        try {

            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);

            String authToken = result.authToken();
            CreateGameRequest request = new CreateGameRequest("gameName");
            gameService.createGame(request, authToken);

            JoinGameRequest joinRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, "1");
            gameService.joinGame(joinRequest, authToken);
            game = gameDAO.getGame(1);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            game = null;
        }
        Assertions.assertTrue(gameDAO.getDatabase().size() == 1);
        Assertions.assertTrue(game != null);
        Assertions.assertEquals("username", game.whiteUsername());
    }

    @Test
    public void testJoinGameFail(){
        GameData game;
        try {

            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);
            String authToken = result.authToken();

            String authToken2 = userService.register(new RegisterRequest("imposter", "password", "email")).authToken();

            CreateGameRequest request = new CreateGameRequest("gameName");
            gameService.createGame(request, authToken);

            JoinGameRequest joinRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, "1");
            JoinGameRequest joinRequest2 = new JoinGameRequest(ChessGame.TeamColor.WHITE, "1");
            gameService.joinGame(joinRequest, authToken);
            gameService.joinGame(joinRequest2, authToken2);
            game = gameDAO.getGame(1);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            game = null;
        }
        Assertions.assertTrue(gameDAO.getDatabase().size() == 1);
        Assertions.assertTrue(game != null);
        Assertions.assertEquals("username", game.whiteUsername());
    }
}
