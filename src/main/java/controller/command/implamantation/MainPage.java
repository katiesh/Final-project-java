package controller.command.implamantation;

import controller.command.ICommand;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainPage implements ICommand {

    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        servletRequest.getRequestDispatcher(ForwardPagesPaths
                .MAIN_PAGE.toString()).forward(servletRequest, servletResponse);
    }
}
