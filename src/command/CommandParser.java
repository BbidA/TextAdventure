package command;

import io.MessageHelper;
import io.MessageType;
import player.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/7/22.
 * Description: Parse the command user input and then react with this command.
 * @author Liao
 */
public class CommandParser {
    private CommandBag commandBag;
    private Map<String, Method> commandMap;

    public CommandParser(Player player) {
        commandBag = new CommandBag(player);
        // Initialize command map
        commandMap = new HashMap<>();
        Method[] methods = CommandBag.class.getDeclaredMethods();
        for (Method method : methods) {
            // Check annotation
            if (method.isAnnotationPresent(Command.class)) {
                Command annotation = method.getAnnotation(Command.class);
                commandMap.put(annotation.command(), method);
            }
        }
    }

    public void parseCommand(String command) {
        command = command.toLowerCase();
        // Check null
        if (!commandMap.containsKey(command)) {
            MessageHelper.printMessage("Command not found", MessageType.WARNING);
            return;
        }
        // Invoke this method
        Method method = commandMap.get(command);
        try {
            method.invoke(commandBag);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
