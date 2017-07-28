import command.CommandParser;
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
     */
    public static void createNewGame() {

        new Game(PlayerManager.createNewPlayer()).startGame();
    }

    /**
     * List the files and then load a game according to the user's input.
     */
    public static boolean loadGame() {

        File file = new File("json/profiles");
        String[] files = Optional.ofNullable(file.list()).orElse(new String[0]);
        if (files.length == 0) {
            //TODO fix the problem when this situation appears
            MessageHelper.printPlainMsg("There's no old profiles can be loaded", MessageType.WARNING);
            return false;
        } else {
            MessageHelper.printMenu(Arrays.asList(files));
            int optNum = 0;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String option = reader.readLine();
                optNum = Integer.parseInt(option);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Game(PlayerManager.loadPlayer(files[optNum])).startGame();
            return true;
        }
    }

    /**
     * the main loop of the game. If the user input "exit", the game will save the user status and then exit.
     */
    private void startGame() {
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
