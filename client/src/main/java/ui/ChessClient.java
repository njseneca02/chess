package ui;

import exception.ResponseException;
import network.ServerFacade;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.SET_TEXT_COLOR_GREEN;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;
    private String authToken;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";

            return switch (cmd) {
                case "help" -> help();
                case "login" -> login();
                case "register" -> register();
                case "create" -> createGame();
                case "list" -> listGames();
                case "join" -> joinGame();
                case "observe" -> observeGame();
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String help(){
        if(state == State.SIGNEDOUT){
            return """
                   - register (create new user after providing proper credentials)
                   - login (sign in after providing proper credentials)
                   - quit (ends the program)
                   - help (displays extra text for each command)
                   """;
        }
        else{
            return """
                   - create (makes a new game after providing input)
                   - list (lists all games that currently exist)
                   - join (join a game as a player after specifying game id)
                   - observe (join a game as a spectator after specifying game id)
                   - logout (signs out the user)
                   - quit (ends the program)
                   - help (displays extra text for each command)
                   """;
        }
    }

    public String login() throws ResponseException{
        return null;
    }

    public String register() throws ResponseException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "username: ");
        String username = scanner.nextLine();
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "password: ");
        String password = scanner.nextLine();
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "email: ");
        String email = scanner.nextLine();
        try {
            authToken = server.register(username, password, email);
        }
        catch(IOException e){
            return e.getMessage();
        }
        state = State.SIGNEDIN;
        visitorName = username;
        return "Logged in as " + username;
    }

    public String logout() throws ResponseException{
        return null;
    }

    public String createGame() throws ResponseException{
        return null;
    }

    public String listGames() throws ResponseException{
        return null;
    }

    public String joinGame() throws ResponseException{
        return null;
    }

    public String observeGame() throws ResponseException{
        return null;
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }

    public boolean isLoggedIn(){
        if(state == State.SIGNEDOUT){
            return false;
        }
        else{
        return true;
        }
    }

    public String getUsername(){
        return visitorName;
    }

}
