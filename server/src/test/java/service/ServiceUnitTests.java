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

import java.util.*;


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
        Collection<GameData> game = null;
        Collection<UserData> user = null;
        Collection<AuthData> auth = null;
        try{
            authDAO.createAuth(new AuthData("hello", "nathan"));
            gameDAO.createGame(new GameData(1, "a", "b", "game", false, new ChessGame()));
            userDAO.createUser(new UserData("n", "pass", "email"));
            utilService.clearDatabase();
            game = gameDAO.listGames();
            auth = authDAO.getDatabase();
            user = userDAO.getDatabase();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertNotNull(game);
        Assertions.assertNotNull(auth);
        Assertions.assertNotNull(user);
        Assertions.assertTrue(auth.isEmpty());
        Assertions.assertTrue(game.isEmpty());
        Assertions.assertTrue(user.isEmpty());


    }

    @Test
    public void testRegisterSuccess(){
        int user = 0;
        int auth = 0;
        try {

            RegisterRequest request = new RegisterRequest("username", "password", "email");
            userService.register(request);
            user = userDAO.getDatabase().size();
            auth = authDAO.getDatabase().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(user == 1);
        Assertions.assertTrue( auth == 1);
    }

    @Test
    public void testRegisterFail(){
        boolean user = false;
        boolean auth = false;
        try {

            RegisterRequest request = new RegisterRequest("username", "password", null);
            userService.register(request);
            user = userDAO.getDatabase().isEmpty();
            auth = authDAO.getDatabase().isEmpty();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(user);
        Assertions.assertTrue(auth);
    }

    @Test
    public void testLoginSuccess(){
        LoginResult result;
        int auth = 0;
        try {

            RegisterRequest request = new RegisterRequest("username", "password", "email");
            userService.register(request);
            LoginRequest loginRequest = new LoginRequest("username", "password");
            result = userService.login(loginRequest);
            auth = authDAO.getDatabase().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            result = null;
        }
        Assertions.assertEquals(result.username(), "username");
        Assertions.assertTrue(auth == 2);

    }

    @Test
    public void testLoginFail(){
        LoginResult result;
        int auth = 0;
        try {

            RegisterRequest request = new RegisterRequest("username", "password", "email");
            userService.register(request);
            LoginRequest loginRequest = new LoginRequest("username", "pass");
            result = userService.login(loginRequest);
            auth = authDAO.getDatabase().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            result = null;
        }
        Assertions.assertEquals(result.username(), null);
        Assertions.assertTrue(auth == 1);
    }

    @Test
    public void testLogoutSuccess(){
        int user = 0;
        boolean auth = false;
        try {
            RegisterRequest request = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(request);
            String authToken = result.authToken();
            userService.logout(authToken);
            user = userDAO.getDatabase().size();
            auth = authDAO.getDatabase().isEmpty();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(user == 1);
        Assertions.assertTrue(auth);
    }

    @Test
    public void testLogoutFail(){
        NoBodyResult result;
        int user = 0;
        int auth = 0;
        try {
            RegisterRequest request = new RegisterRequest("username", "password", "email");
            userService.register(request);
            result = userService.logout("1234");
            user = userDAO.getDatabase().size();
            auth = authDAO.getDatabase().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            result = null;
        }
        Assertions.assertTrue(user == 1);
        Assertions.assertTrue(auth == 1);
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
        int gameSetSize = 0;
        try {
            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);
            String authToken = result.authToken();
            CreateGameRequest request = new CreateGameRequest("gameName");
            gameService.createGame(request, authToken);
            game = gameDAO.getGame(1);
            gameSetSize = gameDAO.listGames().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            game = null;
        }
        Assertions.assertTrue( gameSetSize== 1);
        Assertions.assertTrue(game != null);
        Assertions.assertEquals("gameName", game.gameName());
    }

    @Test
    public void testCreateGameFail(){
        int gameSetSize = 1;
        try {
            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);
            String authToken = result.authToken();
            CreateGameRequest request = new CreateGameRequest(null);
            gameService.createGame(request, authToken);
            gameSetSize = gameDAO.listGames().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(gameSetSize == 0);
    }

    @Test
    public void testJoinGameSuccess(){
        GameData game;
        int gameSetSize = 0;
        try {

            RegisterRequest registerRequest = new RegisterRequest("username", "password", "email");
            RegisterResult result = userService.register(registerRequest);

            String authToken = result.authToken();
            CreateGameRequest request = new CreateGameRequest("gameName");
            gameService.createGame(request, authToken);

            JoinGameRequest joinRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, "1");
            gameService.joinGame(joinRequest, authToken);
            game = gameDAO.getGame(1);

            gameSetSize = gameDAO.listGames().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            game = null;
        }
        Assertions.assertTrue(gameSetSize== 1);
        Assertions.assertTrue(game != null);
        Assertions.assertEquals("username", game.whiteUsername());
    }

    @Test
    public void testJoinGameFail(){
        GameData game;
        int gameSetSize = 0;
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

            gameSetSize = gameDAO.listGames().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
            game = null;
        }
        Assertions.assertTrue(gameSetSize == 1);
        Assertions.assertTrue(game != null);
        Assertions.assertEquals("username", game.whiteUsername());
    }
}
