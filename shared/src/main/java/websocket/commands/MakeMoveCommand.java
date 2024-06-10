package websocket.commands;

public class MakeMoveCommand extends UserGameCommand {
    public MakeMoveCommand(String authToken) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
    }
}
