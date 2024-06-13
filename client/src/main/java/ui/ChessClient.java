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
    private ChessGame myGame = null;
    private int myGameID;

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
                case "resign" -> resign();
                case "highlight" -> highlightMoves();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String resign() throws ResponseException{
        assertSignedIn();
        assertInGame();
        try {
            server.resign(authToken, myGameID);
        }
        catch(IOException e){
            return e.getMessage();
        }
        return "";
    }

    public String leave() throws ResponseException{
        assertSignedIn();
        assertInGame();
        try {
            server.leaveGame(myGameID , authToken);
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
        drawTeamBoard(myGame);
        return "";
    }

    public String makeMove() throws ResponseException{
        assertSignedIn();
        assertInGame();

        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "target piece (enter letter, then number)");
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "letter: ");
        int colPiece = ChessBoard.positionConverterToInt(scanner.nextLine());
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "number: ");
        int rowPiece = Integer.parseInt(scanner.nextLine());
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "target destination (enter letter, then number)");
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "letter: ");
        int colDestination = ChessBoard.positionConverterToInt(scanner.nextLine());
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "number: ");
        int rowDestination = Integer.parseInt(scanner.nextLine());

        ChessPosition startPos = new ChessPosition(rowPiece, colPiece);
        ChessMove move = new ChessMove(new ChessPosition(rowPiece, colPiece), new ChessPosition(rowDestination, colDestination), null);
        if(myGame.getBoard().getPiece(startPos).getPieceType() == ChessPiece.PieceType.PAWN) {
            if ((myColor == ChessGame.TeamColor.WHITE && rowDestination == 8) || (myColor == ChessGame.TeamColor.BLACK && rowDestination == 1)) {
                try {
                    move = new ChessMove(new ChessPosition(rowPiece, colPiece), new ChessPosition(rowDestination, colDestination), askForType());
                } catch (IOException e) {
                    return e.getMessage();
                }
            }
        }
        try {
            server.makeMove(authToken, myGameID, move);
        }
        catch(IOException e){
            return e.getMessage();
        }

        return "";
    }


    public String highlightMoves() throws ResponseException{
        assertSignedIn();
        assertInGame();

        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "target piece (enter letter, then number)");
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "letter: ");
        int colPiece = ChessBoard.positionConverterToInt(scanner.nextLine());
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "number: ");
        int rowPiece = Integer.parseInt(scanner.nextLine());

        ChessPosition startPos = new ChessPosition(rowPiece, colPiece);
        if(myGame.getBoard().getPiece(startPos) == null){
            return " no piece there";
        }
        drawTeamBoardHighlight(myGame, startPos);
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
                   - highlight (highlights possible moves of specified piece)
                   - help (displays extra text for each command)
                   """;
        }
    }

    public String login() throws ResponseException{
        assertNotSignedIn();
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
        assertNotSignedIn();
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
        assertNotInGame();
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
        assertNotInGame();
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
        assertNotInGame();
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
        assertNotInGame();
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
        myGameID = game.gameID();
        inGame = true;
        return "joined Game";

    }

    public String observeGame() throws ResponseException{
        assertSignedIn();
        assertNotInGame();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "enter game number: ");
        String gameId = scanner.nextLine();
        GameData game = listOfGames.get(Integer.parseInt(gameId));
        try {
            server.joinGame(game, authToken, null);
        }
        catch(IOException e){
            myGameID = game.gameID();
            inGame = true;
            System.out.println(e.getMessage());
        }
        myGameID = game.gameID();
        inGame = true;
        return "Observing game";

    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must log in");
        }
    }

    private void assertNotSignedIn() throws ResponseException {
        if (state == State.SIGNEDIN) {
            throw new ResponseException(400, "You must log out");
        }
    }

    private void assertInGame() throws ResponseException {
        if (!inGame) {
            throw new ResponseException(400, "You must be in a game");
        }
    }

    private void assertNotInGame() throws ResponseException {
        if (inGame) {
            throw new ResponseException(400, "You must leave your game first");
        }
    }

    public boolean isLoggedIn(){
        return state != State.SIGNEDOUT;
    }

    public String getUsername(){
        return visitorName;
    }

    private void errorNotify(ErrorMessage message){
        System.out.println("\n" + SET_TEXT_COLOR_RED + message.getMessage());
    }

    private void notificationNotify(NotificationMessage message){
        System.out.println("\n" + SET_TEXT_COLOR_GREEN + message.getMessage());
    }

    private void loadGameNotify(LoadGameMessage message){
        System.out.println();
        drawTeamBoard(message.getGame());
        myGame = message.getGame();
    }

    public void notify(ServerMessage serverMessage){
        switch(serverMessage.getServerMessageType()){
            case ERROR -> errorNotify((ErrorMessage) serverMessage);
            case NOTIFICATION -> notificationNotify((NotificationMessage) serverMessage);
            case LOAD_GAME -> loadGameNotify((LoadGameMessage) serverMessage);
        }
    }

    private void drawTeamBoard(ChessGame game){
        if(myColor == ChessGame.TeamColor.WHITE || myColor == null){
            ChessBoard.drawWhiteBoard(game);
        }
        else{
            ChessBoard.drawBlackBoard(game);
        }
    }

    private void drawTeamBoardHighlight(ChessGame game, ChessPosition piece){
        if(myColor == ChessGame.TeamColor.WHITE || myColor == null){
            ChessBoard.drawWhiteBoardHighlight(game, piece);
        }
        else{
            ChessBoard.drawBlackBoardHighlight(game, piece);
        }
    }

    private ChessPiece.PieceType askForType() throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + SET_TEXT_COLOR_GREEN +  "enter promotion piece (q, r, b, n):");
        String piece = scanner.nextLine();
        ChessPiece.PieceType result;
        switch(piece){
            case "q" -> result = ChessPiece.PieceType.QUEEN;
            case "r" -> result = ChessPiece.PieceType.ROOK;
            case "b" -> result = ChessPiece.PieceType.BISHOP;
            case "n" -> result = ChessPiece.PieceType.KNIGHT;
            default -> throw new IOException("not a valid promotion piece");
        }
        return result;
    }

}
