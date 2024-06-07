package client;

import network.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        ServerFacade facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void register() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void listGames() {
        Assertions.assertTrue(true);
    }

    @Test
    public void listGamesFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void login() {
        Assertions.assertTrue(true);
    }

    @Test
    public void loginFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void logout() {
        Assertions.assertTrue(true);
    }

    @Test
    public void logoutFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void joinGame() {
        Assertions.assertTrue(true);
    }

    @Test
    public void joinGameFail() {
        Assertions.assertTrue(true);
    }
}
