package controller;

import controller.command.Command;
import controller.command.FactoryCommand;
import org.apache.log4j.Logger;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller class
 * @author Kateryna Shkulova
 */
@WebServlet(name="Controller", urlPatterns = {"/home"})
public class Controller extends HttpServlet {
    /**
     * field logger
     */
    final static Logger logger = Logger.getLogger(Controller.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        performTask(servletRequest,servletResponse);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        performTask(servletRequest,servletResponse);
    }

//    @Override
//    public void destroy(){
//        super.destroy();
//    }

    /**
     * defines command and execute it
     * @param servletRequest request from client to server
     * @param servletResponse response to client from server
     * @throws ServletException if ServletException occurs
     * @throws IOException if IOException occurs
     */
    protected void performTask(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try {
            Command command = FactoryCommand.createCommand(servletRequest);
            command.execute(servletRequest,servletResponse);
        } catch (NullPointerException|ServletException|IOException e) {
            logger.error(e);
            servletRequest.getRequestDispatcher(ForwardPagesPaths.ERROR.toString())
                    .forward(servletRequest,servletResponse);
        }
    }

}
