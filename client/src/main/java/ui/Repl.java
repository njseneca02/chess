package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run() {
        System.out.println(SET_TEXT_COLOR_BLUE + "Welcome to playing Chess! Type help to start.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (result == null || !result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        if(client.isLoggedIn()) {
            System.out.print("\n" + SET_TEXT_COLOR_GREEN + client.getUsername() + " >>> ");
        }
        else{
            System.out.print("\n" + SET_TEXT_COLOR_GREEN + ">>> ");
        }
    }

}
