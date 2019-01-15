package controller.command.implementation;

import controller.command.Command;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * implements interface ICommand{@link Command}
 * @author Kateryna Shkulova
 */
public class Error extends Command {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        servletRequest.getRequestDispatcher(ForwardPagesPaths
                .ERROR.toString()).forward(servletRequest, servletResponse);
    }
}
