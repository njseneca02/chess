package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UtilService;

public class DataAccessTests {

    private AuthDAO authDAO = new SQLAuthDAO();
    private GameDAO gameDAO = new SQLGameDAO();
    private UserDAO userDAO = new SQLUserDAO();

    private UtilService utilService = new UtilService(authDAO, gameDAO, userDAO);

    @BeforeEach
    public void clear(){
        try{
            utilService.clearDatabase();
        }
        catch(DataAccessException e){
            System.out.println("clear for tests has failed: " + e.getMessage());
        }
    }

    // gameDAO tests

    @Test
    public void testGameCreate(){
        GameData g = new GameData(5, null, null, "gameName", false, new ChessGame());
        int id = 0;
        try {
            id = gameDAO.createGame(g);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(id != 0);
    }

    @Test
    public void testGameCreateFail(){
        GameData g = new GameData(5, null, null, null, false, new ChessGame());
        int id = 0;
        try {
            id = gameDAO.createGame(g);
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertEquals(0, id);
    }

    @Test
    public void testGameGet(){
        GameData g = new GameData(5, null, null, "gameName", false, new ChessGame());
        int id = 0;
        String test = null;
        try {
            id = gameDAO.createGame(g);
            test = gameDAO.getGame(id).gameName();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(test.equals("gameName"));
    }

    @Test
    public void testGameGetFail(){
        GameData g = new GameData(5, null, null, "gameName", false, new ChessGame());
        String test = null;
        try {
            gameDAO.createGame(g);
            if(gameDAO.getGame(0) == null){
                test = null;
            }
            else {
                test = gameDAO.getGame(0).gameName();
            }
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertNull(test);
    }

    @Test
    public void testGameList(){
        GameData g1 = new GameData(5, null, null, "gameName1", false, new ChessGame());
        GameData g2 = new GameData(6, null, null, "gameName2", false, new ChessGame());
        GameData g3 = new GameData(7, null, null, "gameName3", false, new ChessGame());
        int size = 0;
        try {
            gameDAO.createGame(g1);
            gameDAO.createGame(g2);
            gameDAO.createGame(g3);
            size = gameDAO.listGames().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertEquals(size, 3);
    }

    @Test
    public void testGameListFail(){
        GameData g1 = new GameData(5, null, null, "gameName1", false, new ChessGame());
        GameData g2 = new GameData(6, null, null, null, false, new ChessGame());
        int size = 0;
        try {
            gameDAO.createGame(g1);
            size = gameDAO.listGames().size();
            gameDAO.createGame(g2);
            size = gameDAO.listGames().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertEquals(size, 1);
    }

    @Test
    public void testGameUpdatePlayer(){
        GameData g = new GameData(5, null, null, "gameName",false,  new ChessGame());
        String test = null;
        try {
            int id = gameDAO.createGame(g);
            gameDAO.updatePlayer(id, "testName", ChessGame.TeamColor.BLACK);
            test = gameDAO.getGame(id).blackUsername();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(test.equals("testName"));
    }

    @Test
    public void testGameUpdatePlayerFail(){
        GameData g = new GameData(5, null, null, "gameName", false, new ChessGame());
        String test = null;
        try {
            int id = gameDAO.createGame(g);
            gameDAO.updatePlayer(id, "testName", ChessGame.TeamColor.BLACK);
            gameDAO.updatePlayer(0, "replaceName", ChessGame.TeamColor.BLACK);
            test = gameDAO.getGame(id).blackUsername();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertEquals("testName", test);
    }

    @Test
    public void testGameClear(){
        GameData g1 = new GameData(5, null, null, "gameName1", false, new ChessGame());
        GameData g2 = new GameData(6, null, null, "gameName2", false, new ChessGame());
        GameData g3 = new GameData(7, null, null, "gameName3", false, new ChessGame());
        int size = 3;
        try {
            gameDAO.createGame(g1);
            gameDAO.createGame(g2);
            gameDAO.createGame(g3);
            gameDAO.clear();
            size = gameDAO.listGames().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertEquals(size, 0);
    }

    // userDAO tests

    @Test
    public void testUserCreate(){
        UserData u = new UserData("username", "password", "email");
        int pass = 0;
        try {
            userDAO.createUser(u);
        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 0);
    }

    @Test
    public void testUserCreateFail(){
        UserData u = new UserData(null, "password", "email");
        int pass = 0;
        try {
            userDAO.createUser(u);
        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertEquals(1, pass);
    }

    @Test
    public void testUserGet(){
        UserData u = new UserData("username", "password", "email");
        UserData user = null;
        String username = null;
        try {
            userDAO.createUser(u);
            user = userDAO.getUser("username");
            username = user.username();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertNotNull(user);
        Assertions.assertEquals("username", username);
    }

    @Test
    public void testUserGetFail(){
        UserData u = new UserData("username", "password", "email");
        UserData user;
        String username = null;
        try {
            userDAO.createUser(u);
            user = userDAO.getUser(null);
            if(user == null){
                username = null;
            }
            else{
                username = user.username();
            }
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertNull(username);
    }

    @Test
    public void testUserClear(){
        UserData u = new UserData("username", "password", "email");
        int pass = 2;
        try {
            userDAO.createUser(u);
            userDAO.clear();
            pass = userDAO.getDatabase().size();
        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 0);
    }

    @Test
    public void testUserList(){
        UserData u = new UserData("username", "password", "email");
        int pass = 0;
        try {
            userDAO.createUser(u);
            pass = userDAO.getDatabase().size();
        }
        catch(DataAccessException e){
            pass = 2;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 1);
    }

    @Test
    public void testUserListFail(){
        UserData u1 = new UserData("username", "password", "email");
        UserData u2 = new UserData(null, "password", "email");
        int pass = 0;
        try {
            userDAO.createUser(u1);
            pass = userDAO.getDatabase().size();
            userDAO.createUser(u2);
            pass = userDAO.getDatabase().size();
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 1);
    }

    // authDAO tests

    @Test
    public void testAuthCreate(){
        AuthData a = new AuthData("authToken", "username");
        int pass = 0;
        try {
            authDAO.createAuth(a);
        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 0);
    }

    @Test
    public void testAuthCreateFail(){
        AuthData a = new AuthData(null, "username");
        int pass = 0;
        try {
            authDAO.createAuth(a);
        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 1);
    }

    @Test
    public void testAuthGet(){
        AuthData a = new AuthData("authToken", "username");
        String username = null;
        try {
            authDAO.createAuth(a);
            AuthData auth = authDAO.getAuth("authToken");
            username = auth.username();

        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertEquals("username", username);
    }

    @Test
    public void testAuthGetFail(){
        AuthData a = new AuthData("authToken", "username");
        String username = null;
        try {
            authDAO.createAuth(a);
            AuthData auth = authDAO.getAuth("auth");
            if(auth == null){
                username = null;
            }
            else{
                username = auth.username();
            }
        }
        catch(DataAccessException e){
            System.out.println(e.getMessage());
        }
        Assertions.assertNull(username);
    }

    @Test
    public void testAuthDelete(){
        AuthData a = new AuthData("authToken", "username");
        int pass = 0;
        try {
            authDAO.createAuth(a);
            Assertions.assertEquals(authDAO.getDatabase().size(), 1);
            authDAO.deleteAuth(a);
            Assertions.assertEquals(authDAO.getDatabase().size(), 0);
        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 0);
    }

    @Test
    public void testAuthDeleteFail(){
        AuthData a = new AuthData("authToken", "username");
        int pass = 0;
        try {
            authDAO.createAuth(a);
            Assertions.assertEquals(authDAO.getDatabase().size(), 1);
            authDAO.deleteAuth(new AuthData("auth", "username"));
            Assertions.assertEquals(authDAO.getDatabase().size(), 1);
        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 0);
    }

    @Test
    public void testAuthClear(){
        AuthData a1 = new AuthData("authToken1", "username1");
        AuthData a2 = new AuthData("authToken2", "username2");
        AuthData a3 = new AuthData("authToken3", "username3");
        int pass = 2;
        try {
            authDAO.createAuth(a1);
            authDAO.createAuth(a2);
            authDAO.createAuth(a3);
            authDAO.clear();
            pass = authDAO.getDatabase().size();

        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 0);
    }

    @Test
    public void testAuthList(){
        AuthData a1 = new AuthData("authToken1", "username1");
        AuthData a2 = new AuthData("authToken2", "username2");
        AuthData a3 = new AuthData("authToken3", "username3");
        int pass = 0;
        try {
            authDAO.createAuth(a1);
            authDAO.createAuth(a2);
            authDAO.createAuth(a3);
            pass = authDAO.getDatabase().size();

        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 3);
    }

    @Test
    public void testAuthListFail(){
        AuthData a1 = new AuthData("authToken1", "username1");
        AuthData a2 = new AuthData(null, "username2");
        int pass = 0;
        try {
            authDAO.createAuth(a1);
            authDAO.createAuth(a2);
            pass = authDAO.getDatabase().size();

        }
        catch(DataAccessException e){
            pass = 1;
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(pass == 1);
    }
}
