package client;

import network.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;

import java.io.IOException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterEach
     public void clearDatabases(){
        server.clearDatabases();
    }


    @Test
    public void register() {
        int test = 0;
        try{
            facade.register("username", "password", "email");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(1, test);
    }

    @Test
    public void registerFail() {

        int test = 0;
        try{
            facade.register("username", "password", "email");
            facade.register("username", "password", "email");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(2, test);
    }

    @Test
    public void listGames() {
        int test = 0;
        try{
            String authToken = facade.register("username", "password", "email");
            facade.listGames(authToken);
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(1, test);
    }

    @Test
    public void listGamesFail() {
        int test = 0;
        try{
            facade.register("username", "password", "email");
            facade.listGames("notValid");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(2, test);
    }

    @Test
    public void login() {
        int test = 0;
        try{
            String authToken = facade.register("username", "password", "email");
            facade.logout(authToken);
            facade.login("username", "password");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(1, test);
    }

    @Test
    public void loginFail() {
        int test = 0;
        try{
            String authToken = facade.register("username", "password", "email");
            facade.logout(authToken);
            facade.login("username", "passFail");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(2, test);
    }

    @Test
    public void logout() {
        int test = 0;
        try{
            String authToken = facade.register("username", "password", "email");
            facade.logout(authToken);
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(1, test);
    }

    @Test
    public void logoutFail() {
        int test = 0;
        try{
            facade.register("username", "password", "email");
            facade.logout("inValid");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(2, test);
    }

    @Test
    public void createGame() {
        int test = 0;
        try{
            String authToken = facade.register("username", "password", "email");
            facade.createGame("gameName", authToken);
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(1, test);
    }

    @Test
    public void createGameFail() {
        int test = 0;
        try{
            facade.register("username", "password", "email");
            facade.createGame("gameName", "inValid");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(2, test);
    }

    @Test
    public void joinGame() {
        int test = 0;
        try{
            String authToken = facade.register("username", "password", "email");
            facade.createGame("gameName", authToken);
            facade.joinGame(server.getGame(), authToken, "white");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(1, test);
    }

    @Test
    public void joinGameFail() {
        int test = 0;
        try{
            String authToken = facade.register("username", "password", "email");
            facade.createGame("gameName", authToken);
            facade.joinGame(server.getGame(), "inValid", "white");
            test = 1;
        }
        catch(IOException e){
            test = 2;
        }
        Assertions.assertEquals(2, test);
    }
}
