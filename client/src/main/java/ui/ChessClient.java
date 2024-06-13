package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import exception.ResponseException;
import model.GameData;
import network.ServerFacade;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ChessClient implements NotificationHandler{
    private String visitorName = null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;
    private boolean inGame = false;
    private ChessGame.TeamColor myColor = null;
    private String authToken;
    private HashMap<Integer, GameData> listOfGames = new HashMap<>();
    private chess.ChessBoard localBoard;
    private GameData myGame = null;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(this, serverUrl);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";

            //add more cases here for the post game gui
            return switch (cmd) {
                case "help" -> help();
                case "login" -> login();
                case "register" -> register();
                case "create" -> createGame();
                case "list" -> listGames();
                case "join" -> joinGame();
                case "observe" -> observeGame();
                case "logout" -> logout();
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "mm" -> makeMove();
//                case "resign" -> resign();
//                case "highlight" -> highlightMoves();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String leave() throws ResponseException{
        assertSignedIn();
        assertInGame();
        try {
            server.leaveGame(myGame , authToken);
        }
        catch(IOException e){
            return e.getMessage();
        }
        myColor = null;
        inGame = false;
        myGame = null;
        return "Left the game";

    }

    public String redraw() throws ResponseException{
        assertSignedIn();
        assertInGame();
        drawTeamBoard(localBoard.getChessBoard());
        return "";
    }

    public String makeMove() throws ResponseException{
        assertSignedIn();
        assertInGame();

        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "target piece (enter letter, then number");
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "letter: ");
        int colPiece = ChessBoard.positionConverterToInt(scanner.nextLine());
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "number: ");
        int rowPiece = Integer.parseInt(scanner.nextLine());
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "target destination (enter letter, then number");
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "letter: ");
        int colDestination = ChessBoard.positionConverterToInt(scanner.nextLine());
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "number: ");
        int rowDestination = Integer.parseInt(scanner.nextLine());

        ChessMove move = new ChessMove(new ChessPosition(rowPiece, colPiece), new ChessPosition(rowDestination, colDestination), null);

        try {
            server.makeMove(authToken, myGame.gameID(), move);
        }
        catch(IOException e){
            return e.getMessage();
        }
        return "success";
    }

    public String highlight() throws ResponseException{
        assertSignedIn();
        assertInGame();
        //drawTeamBoardHighlight(localBoard.getChessBoard(), ChessPosition position)
        return "";
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
        else if(!inGame){
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
        else{
            return """
                   - redraw (redraws the chessboard)
                   - leave (removes player from game)
                   - mm (make move after specifying piece and destination)
                   - resign (forfeits the game)
                   - highlight (highlights possible moves of specified piece))
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
        if(teamColor.equals("white")){
            myColor = ChessGame.TeamColor.WHITE;
        }
        else{
            myColor = ChessGame.TeamColor.BLACK;
        }
        myGame = game;
        inGame = true;
        return "joined Game";

    }
    //try to figure out why this one doesn't make it to its return statement but the joingame does
    public String observeGame() throws ResponseException{
        assertSignedIn();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "enter game number: ");
        String gameId = scanner.nextLine();
        GameData game = listOfGames.get(Integer.parseInt(gameId));
        try {
            server.joinGame(game, authToken, null);
        }
        catch(IOException e){
            return e.getMessage();
        }
        myGame = game;
        inGame = true;
        return "Observing game";

    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must log in");
        }
    }

    private void assertInGame() throws ResponseException {
        if (!inGame) {
            throw new ResponseException(400, "You must be in a game");
        }
    }

    public boolean isLoggedIn(){
        return state != State.SIGNEDOUT;
    }

    public String getUsername(){
        return visitorName;
    }

    //add code for the post game ui and then correlating calls to serverfacade (join game, make move, highlight moves,

    private void errorNotify(ErrorMessage message){
        System.out.println("\n" + SET_TEXT_COLOR_RED + message.getMessage());
    }

    private void notificationNotify(NotificationMessage message){
        System.out.println("\n" + SET_TEXT_COLOR_GREEN + message.getMessage());
    }

    private void loadGameNotify(LoadGameMessage message){
        System.out.println();
        drawTeamBoard(message.getGame().getBoard().getChessBoard());
        localBoard = message.getGame().getBoard();
    }

    public void notify(ServerMessage serverMessage){
//switch case to handle each server message and take care of code aqccordingly
        switch(serverMessage.getServerMessageType()){
            case ERROR -> errorNotify((ErrorMessage) serverMessage);
            case NOTIFICATION -> notificationNotify((NotificationMessage) serverMessage);
            case LOAD_GAME -> loadGameNotify((LoadGameMessage) serverMessage);
        }
    }

    private void drawTeamBoard(ChessPiece[][] board){
        if(myColor == ChessGame.TeamColor.WHITE || myColor == null){
            ChessBoard.drawWhiteBoard(board);
        }
        else{
            ChessBoard.drawBlackBoard(board);
        }
    }

}
