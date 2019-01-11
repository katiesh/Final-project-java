package controller.strategy;

import controller.strategy.implamantation.*;

import javax.servlet.http.HttpServletRequest;

public class FactoryCommand {

    public static ICommand createCommand (HttpServletRequest servletRequest){
        String commandStr = servletRequest.getParameter("command");
        if(commandStr==null){
            return new MainPage();
        }
        Commands command = Commands.valueOf(commandStr.toUpperCase());
//        return command.getCommand();
        switch (command){
            case ADD_BOOKING:{
                return new AddBooking();
            }
            case APPLICATIONS:{
                return new AllApplications();
            }
            case BOOKINGS:{
                return new AllBookings();
            }
            case CLIENT_APPLICATIONS:{
                return new ClientApplications();
            }
            case SET_CLIENT:{
                return new FormClientEmailOrTel();
            }
            case MAIN_PAGE:{
                return new MainPage();
            }
            case FORM_CLIENT_INF:{
                return new FormClientInf();
            }
            case FORM_CLIENT_ROOM:{
                return new FormClientRoom();
            }
            case ROOMS:{
                return new Rooms();
            }
            default:{
                return new MainPage();
            }
        }
    }
}
