import io.MessageHelper;
import io.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/7/21.
 * Description : user choose load/new a game in this menu
 * @author : Liao
 */
public class MainMenu {

    List<String> menuItems;

    public MainMenu() {
        menuItems = new ArrayList<>();
        menuItems.add("New");
        menuItems.add("Load");
        interact();
    }

    /**
     * Loop until the user input the right command. Choose 1 means create a new game, choose 2 meas load a game from the profiles and
     * if the user input "exit", the while loop will be broke.
     */
    private void interact() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            label:
            while (true) {
                MessageHelper.printMessage("Welcome to the adventure world", MessageType.PLAIN);
                MessageHelper.printMenu(menuItems);
                String userInput = reader.readLine();
                switch (userInput) {
                    case "0":
                        Game.createNewGame();
                        break label;
                    case "1":
                        if (Game.loadGame())
                            break label;
                        break;
                    case "exit":
                        MessageHelper.printExitMsg();
                        break label;
                    default:
                        MessageHelper.printMessage("Wrong command", MessageType.WARNING);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//        MainMenu mainMenu = new MainMenu();
//    }
}
