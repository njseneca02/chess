package dataaccess;

import chess.ChessGame;
import model.GameData;
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

    @Test
    public void testGameCreate(){
        GameData g = new GameData(5, null, null, "gameName", new ChessGame());
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
        GameData g = new GameData(5, null, null, null, new ChessGame());
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
        GameData g = new GameData(5, null, null, "gameName", new ChessGame());
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
        GameData g = new GameData(5, null, null, "gameName", new ChessGame());
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
        GameData g1 = new GameData(5, null, null, "gameName1", new ChessGame());
        GameData g2 = new GameData(6, null, null, "gameName2", new ChessGame());
        GameData g3 = new GameData(7, null, null, "gameName3", new ChessGame());
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
        GameData g1 = new GameData(5, null, null, "gameName1", new ChessGame());
        GameData g2 = new GameData(6, null, null, null, new ChessGame());
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
        GameData g = new GameData(5, null, null, "gameName", new ChessGame());
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
        GameData g = new GameData(5, null, null, "gameName", new ChessGame());
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
        GameData g1 = new GameData(5, null, null, "gameName1", new ChessGame());
        GameData g2 = new GameData(6, null, null, "gameName2", new ChessGame());
        GameData g3 = new GameData(7, null, null, "gameName3", new ChessGame());
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
}
