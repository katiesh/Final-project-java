package controller.command;

import controller.command.implementation.*;
import controller.command.implementation.Error;
import util.constants.Commands;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * class is the factory of commands
 * @author Kateryna Shkulova
 */
public class FactoryCommand {
    /**
     * map of commands class's instances
     */
    private static Map<Commands, Command> commandsMap;
    /**
     * static block which sets map of commands class's instances {@link #commandsMap}
     */
    static {
        commandsMap = new HashMap<>();
        commandsMap.put(Commands.ADD_BOOKING, new AddBooking());
        commandsMap.put(Commands.APPLICATIONS, new AllApplications());
        commandsMap.put(Commands.BOOKINGS, new AllBookings());
        commandsMap.put(Commands.SET_CLIENT, new FormClientEmailOrTel());
        commandsMap.put(Commands.MAIN_PAGE, new MainPage());
        commandsMap.put(Commands.FORM_CLIENT_INF, new FormClientInf());
        commandsMap.put(Commands.FORM_CLIENT_ROOM, new FormClientRoom());
        commandsMap.put(Commands.CLIENT_APPLICATIONS, new ClientApplications());
        commandsMap.put(Commands.REQUEST_STEP1, new RequestStep1());
        commandsMap.put(Commands.ROOMS, new Rooms());
        commandsMap.put(Commands.ERROR, new Error());
    }
    /**
     * gets the command class from commands instances {@link #commandsMap} by its key
     * @param servletRequest is a request from client to server
     * @return ICommand implementation
     */
    public static Command createCommand (HttpServletRequest servletRequest){
        String commandStr = servletRequest.getParameter("command");
        if(commandStr==null){
            return new MainPage();
        }
        //????????? ?????????? ?? ???????
        try{
        Commands command = Commands.valueOf(commandStr.toUpperCase());
            return commandsMap.get(command);
        }catch (IllegalArgumentException e){
            return commandsMap.get(Commands.ERROR);
        }
    }
}
