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

        MessageHelper.printPlainMsg("Please enter your name: ", MessageType.PLAIN);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String playerName;
            while ((playerName = reader.readLine()) != null) {
                //check the name's validation
                playerName = playerName.trim();
                if (playerName.equals("")) {
                    MessageHelper.printPlainMsg("Can't be blank", MessageType.WARNING);
                } else if (nameDuplicate(playerName)) {
                    MessageHelper.printPlainMsg("This name has been used", MessageType.WARNING);
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
            System.out.println(file);
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
        //TODO to be completed
        File file = new File("json/profiles");
        String[] files = Optional.ofNullable(file.list()).orElse(new String[0]);
        if (files.length == 0) {
            MessageHelper.printPlainMsg("There's no old profiles can be loaded", MessageType.WARNING);
            return false;
        } else {
            MessageHelper.printMenu(Arrays.asList(files));
            int optNum = 0;
            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String option = MessageHelper.take();
                optNum = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                return false;
            }

            Optional<Player> tmpOptional = Optional.ofNullable(PlayerManager.loadPlayer(files[optNum]));
            Game game = tmpOptional.map(Game::new).get();
            game.startGame();
            return tmpOptional.isPresent();
        }
    }

    /**
     * the main loop of the game. If the user input "exit", the game will save the user status and then exit.
     */
    private void startGame() {
        MessageHelper.printPlainMsg("Welcome to the adventure world " + player.getName(), MessageType.PLAIN);
        player.printAttributes();
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            String userInput;
//            while ((userInput = reader.readLine()) != null) {
//                if (userInput.equals("exit")) {
//                    MessageHelper.printPlainMsg("Bye ", MessageType.PLAIN);
//                    //save user status and exit
//                    exit();
//                }
//                parser.parseCommand(userInput);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String userInput;
        while (true) {
            userInput = MessageHelper.take();
            if (userInput.equals("exit")) {
                MessageHelper.printPlainMsg("Bye ", MessageType.PLAIN);
                //save user status and exit
                exit();
            } else
                parser.parseCommand(userInput);
        }
    }

    /**
     * Save data and then exit the game.
     */
    private void exit() {
        player.save();
        System.exit(0);
    }
}
