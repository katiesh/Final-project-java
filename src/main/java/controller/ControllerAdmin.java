package controller;

import controller.command.FactoryCommand;
import controller.command.ICommand;
import org.apache.log4j.Logger;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="Admin", urlPatterns = {"/admin"})
public class ControllerAdmin extends HttpServlet {
    final static Logger logger = Logger.getLogger(Controller.class);
    @Override
    public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        performTask(servletRequest,servletResponse);
    }

    @Override
    public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        performTask(servletRequest,servletResponse);
    }

    private void performTask(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try {
            ICommand command = FactoryCommand.createCommand(servletRequest);
            command.execute(servletRequest,servletResponse);
        } catch (NullPointerException|ServletException|IOException e) {
            logger.error(e);
            servletRequest.getRequestDispatcher(ForwardPagesPaths.ERROR.toString())
                    .forward(servletRequest,servletResponse);
        }
    }
}
