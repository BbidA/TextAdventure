import command.CommandParser;
import io.FileHelper;
import io.MessageHelper;
import io.MessageType;
import player.Player;
import player.PlayerManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created on 2017/7/21.
 * @author Liao
 *         Description: user interact with the system in this class
 */
public class Game {
    private CommandParser parser = new CommandParser();
    private Player player;

    public Game(Player player) {
        this.player = player;
    }

    /**
     * Create a new Game.
     * @return A Game object start from beginning.
     */
    public static Game createNewGame() {

        return new Game(PlayerManager.createNewPlayer());
    }

    /**
     * list the files and then load a game according to the user's input.
     * @return A Game object represent the game user chose.
     */
    public static Game loadGame() {

        File file = new File("json/profiles");
        String[] files = Optional.ofNullable(file.list()).orElse(new String[0]);
        if (files.length == 0) {
            //TODO fix the problem when this situation appears
            MessageHelper.printPlainMsg("There's no old profiles can be loaded", MessageType.WARNING);
        } else {
            MessageHelper.printMenu(Arrays.asList(files));
            String option = MessageHelper.getUserInput();
            int optNum;
            try {
                optNum = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                optNum = 0;
            }
            return new Game(PlayerManager.loadPlayer(files[optNum]));
        }
        return new Game(PlayerManager.loadPlayer(""));
    }

    /**
     * the main loop of the game. If the user input "exit", the game will save the user status and then exit.
     */
    public void startGame() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while ((userInput = reader.readLine()) != null) {
                if (userInput.equals("exit")) {
                    //save user status and exit
                    exit();
                }
                parser.parseCommand(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save data and then exit the game.
     */
    private void exit() {

    }
}
