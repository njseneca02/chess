package ui;

import network.ServerFacade;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;

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

    public String login(){

    }

    public String register(){

    }

    public String logout(){

    }

    public String createGame(){

    }

    public String listGames(){

    }

    public String joinGame(){

    }

    public String observeGame(){

    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }

}
