package ui;

import exception.ResponseException;
import model.GameData;
import network.ServerFacade;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import static ui.EscapeSequences.SET_TEXT_COLOR_GREEN;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;
    private String authToken;
    private HashMap<Integer, GameData> listOfGames = new HashMap<>();

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
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "username: ");
        String username = scanner.nextLine();
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "password: ");
        String password = scanner.nextLine();

        try{
            authToken = server.login(username, password);
        }
        catch(IOException e){
            return e.getMessage();
        }
        state = State.SIGNEDIN;
        visitorName = username;
        return "Logged in as " + username;
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
        assertSignedIn();
        try{
            server.logout(authToken);
        }
        catch (IOException e){
            return e.getMessage();
        }
        state = State.SIGNEDOUT;
        visitorName = null;
        return "Logged out successfully";
    }

    public String createGame() throws ResponseException{
        assertSignedIn();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "enter game name: ");
        String gameName = scanner.nextLine();
        try{
            server.createGame(gameName, authToken);
        }
        catch(IOException e){
            return e.getMessage();
        }
        return "Success! \n" + listGames();
    }

    public String listGames() throws ResponseException{
        assertSignedIn();
        String result = "";
        try {
            Collection<GameData> games = server.listGames(authToken);
            int i = 1;
            for(GameData game: games){
                String data = i + " - " + game.gameName() + " White: " + game.whiteUsername() + " Black: " + game.blackUsername();
                listOfGames.put(i, game);
                i++;
                result += data + "\n";
            }
        }
        catch(IOException e){
            return e.getMessage();
        }
        return result;
    }

    public String joinGame() throws ResponseException{
        assertSignedIn();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "enter game number: ");
        String gameId = scanner.nextLine();
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "enter team color (white or black): ");
        String teamColor = scanner.nextLine().trim().toLowerCase();
        GameData game = listOfGames.get(Integer.parseInt(gameId));
        try {
            server.joinGame(game, authToken, teamColor);
        }
        catch(IOException e){
            return e.getMessage();
        }
        ChessBoard.drawBoards(game.game().getBoard().getChessBoard());
        return "Success!";

    }

    public String observeGame() throws ResponseException{
        assertSignedIn();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "enter game number: ");
        String gameId = scanner.nextLine();
        GameData game = listOfGames.get(Integer.parseInt(gameId));

        ChessBoard.drawBoards(game.game().getBoard().getChessBoard());
        return "Success!";

    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must log in");
        }
    }

    public boolean isLoggedIn(){
        return state != State.SIGNEDOUT;
    }

    public String getUsername(){
        return visitorName;
    }

}
