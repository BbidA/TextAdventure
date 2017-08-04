package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created on 2017/7/21.
 * Description: Help convey messages between user and the game system
 * @author Liao
 */
public class MessageHelper {

    public static final String SEP_LINE = "====================";

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * print a plain message to the console(actually this is just a println now, but it may change when I create the GUI in the future)
     * @param msg  the message to print.
     * @param type the type of this message (which may be presented by different color in the future).
     */
    public static void printMessage(String msg, MessageType type) {
        System.out.println(msg);
    }

    /**
     * Print bye when the user typed 'exit'
     */
    public static void printExitMsg() {
        System.out.println("Bye");
    }

    /**
     * print a menu in the console in a format
     * @param menuItems the items of this menu
     */
    public static void printMenu(List menuItems) {
        System.out.println("Please Choose");
        System.out.println(SEP_LINE);
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.printf("[%d] " + menuItems.get(i).toString() + System.lineSeparator(), i);
        }
        System.out.println(SEP_LINE);
    }

    public static String take() {
        String message = "";
        try {
            message = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void main(String[] args) {

    }
}
