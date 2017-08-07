import command.CommandParser;
import io.MessageHelper;
import io.MessageType;
import item.repository.EquipmentRepository;
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
     * Create a new Game. (The user's name can't be 'exit')
     */
    public static void createNewGame() {

        MessageHelper.printMessage("Please enter your name: ", MessageType.PLAIN);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String playerName;
            while ((playerName = reader.readLine()) != null) {
                //check the name's validation
                playerName = playerName.trim();
                if (playerName.equals("")) {
                    MessageHelper.printMessage("Can't be blank", MessageType.WARNING);
                } else if (nameDuplicate(playerName)) {
                    MessageHelper.printMessage("This name has been used", MessageType.WARNING);
                } else if (playerName.equals("exit")) {
                    MessageHelper.printExitMsg();
                    return;
                } else {
                    break;
                }
            }
            Player player = new Player(playerName);
            //create profiles
            new Game(player).startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean nameDuplicate(String name) {
        String[] files = new File("json/profiles").list();
        for (String file : files) {
            if (file.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * List the files and then load a game according to the user's input.
     */
    public static boolean loadGame() {
        File file = new File("json/profiles");
        String[] files = Optional.ofNullable(file.list()).orElse(new String[0]);
        if (files.length == 0) {
            MessageHelper.printMessage("There's no old profiles can be loaded", MessageType.WARNING);
            return false;
        } else {
            MessageHelper.printMenu(Arrays.asList(files));
            int optNum;
            try {
                String option = MessageHelper.take();
                //When user input "exit"
                if (option.equals("exit")) {
                    MessageHelper.printExitMsg();
                    System.exit(0);
                }

                optNum = Integer.parseInt(option);
                // Check bounds
                if (optNum >= files.length || optNum < 0) {
                    MessageHelper.printMessage("Out of Bounds", MessageType.WARNING);
                    return false;
                }
            } catch (NumberFormatException e) {
                MessageHelper.printMessage("Invalid Input", MessageType.WARNING);
                return false;
            }

            Optional<Player> tmpOptional = Optional.ofNullable(PlayerManager.loadPlayer(files[optNum]));
            tmpOptional.map(Game::new).ifPresent(Game::startGame);
            return tmpOptional.isPresent();
        }
    }

    /**
     * the main loop of the game. If the user input "exit", the game will save the user status and then exit.
     */
    private void startGame() {
        MessageHelper.printMessage("Welcome to the adventure world " + player.getName(), MessageType.PLAIN);
        player.printAttributes();
        String userInput;
        while (true) {
            userInput = MessageHelper.take();
            if (userInput.equals("exit")) {
                MessageHelper.printExitMsg();
                //save user status and exit
                exit();
                break;
            } else if (userInput.equals("storage")) {
                player.storage.queryStorage();
            } else {
                parser.parseCommand(userInput);
            }
        }
    }

    /**
     * Save data and then exit the game.
     */
    private void exit() {
        Optional.of(player).ifPresent(Player::save);
        System.exit(0);
    }
}
