package io;

import java.util.List;

/**
 * Created on 2017/7/21.
 * Description: Help convey messages between user and the game system
 * @author Liao
 */
public class MessageHelper {

    /**
     * print a plain message to the console(actually this is just a println now, but it may change when I create the GUI in the future)
     * @param msg the message to print.
     * @param type the type of this message (which may be presented by different color in the future).
     */
    public static void printPlainMsg(String msg, MessageType type) {
        System.out.println(msg);
    }

    /**
     * print a menu in the console in a format
     * @param menuItems the items of this menu
     */
    public static void printMenu(List<String> menuItems) {
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.printf("[%d] " + menuItems.get(i) + System.lineSeparator(), i);
        }
    }
}
