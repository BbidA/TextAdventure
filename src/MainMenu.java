import io.MessageHelper;
import io.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/7/21.
 * Description : user choose load/new a game in this menu
 * @author : Liao
 */
public class MainMenu {

    public MainMenu() {
        List<String> menuItems = new ArrayList<>();
        menuItems.add("New");
        menuItems.add("Load");
        MessageHelper.printPlainMsg("Welcome to the adventure world", MessageType.PLAIN);
        MessageHelper.printMenu(menuItems);
        interact();
    }

    /**
     * Loop until the user input the right command. Choose 1 means create a new game, choose 2 meas load a game from the profiles and
     * if the user input "exit", the while loop will be broke.
     */
    private void interact() {
        String userInput = MessageHelper.getUserInput();
        label:
        while (true) {
            switch (userInput) {
                case "0":
                    Game.createNewGame().startGame();
                    break label;
                case "1":
                    Game.loadGame().startGame();
                    break label;
                case "exit":
                    break label;
                default:
                    MessageHelper.printPlainMsg("Wrong command", MessageType.WARNING);
                    break;
            }
        }
    }

//    public static void main(String[] args) {
//        MainMenu mainMenu = new MainMenu();
//    }
}
