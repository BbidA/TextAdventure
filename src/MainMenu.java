import io.MessageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/7/21.
 * Description : user choose load/new a game in this menu
 * @author : Liao
 */
public class MainMenu {

    private List<String> menuItems;

    public MainMenu() {
        menuItems = new ArrayList<>();
        menuItems.add("New");
        menuItems.add("Load");
        MessageHelper.printPlainMsg("Welcome to the adventure world");
        MessageHelper.printMenu(menuItems);
    }

    private void interact() {
        String userInput = MessageHelper.getCommand();
        switch (userInput) {
            case "0":
                //create a new game
            case "1":
                //list all the files
            default:
                MessageHelper.printPlainMsg("wrong command");
        }
    }

//    public static void main(String[] args) {
//        MainMenu mainMenu = new MainMenu();
//    }
}
