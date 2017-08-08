package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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
        if (type == MessageType.INFO) {
            System.out.println(SEP_LINE);
            System.out.println(msg);
            System.out.println(SEP_LINE + System.lineSeparator());
        } else
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
        System.out.println(SEP_LINE);
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.printf("[%d] " + menuItems.get(i).toString() + System.lineSeparator(), i);
        }
        System.out.println(SEP_LINE);
        System.out.println();
    }

    /**
     * Print a menu in a user defined format.
     * @param menuItem the items of the menu
     * @param function way to present items
     * @param <T> type of the item in the list
     */
    public static <T> void printMenu(List<T> menuItem, Function<T, String> function) {
        System.out.println(SEP_LINE);
        for (int i = 0; i < menuItem.size(); i++) {
            System.out.printf("[%d] " + function.apply(menuItem.get(i)) + System.lineSeparator(), i);
        }
        System.out.println(SEP_LINE);
        System.out.println();
    }

    /**
     * List information of the given collection's items in the format you specified.
     * @param collection collection you want to list
     * @param function   translate the item to some kind of presentation with string
     * @param addition   the additive information about the item
     * @param <T>        the type of the item in the collection
     */
    public static <T> void list(Collection<? extends T> collection, Function<T, String> function, Function<T, String> addition) {
        System.out.println(SEP_LINE);
        if (collection.isEmpty())
            System.out.println("Empty");
        else
            collection.forEach(e -> System.out.println(function.apply(e) + addition.apply(e)));
        System.out.println(SEP_LINE);
        System.out.println();
    }

    /**
     * Get user's input from the console.
     * @return user's input
     */
    public static String take() {
        String message = "";
        try {
            System.out.print(">> ");
            message = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * Get a numerical input from the console. If it's not a number, it'll print warning message.
     * @return
     */
    public static int getNumberInput() {
        try {
            System.out.print("Enter a number: ");
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            printMessage("Invalid Input", MessageType.WARNING);
        }
        return -1;
    }
}
