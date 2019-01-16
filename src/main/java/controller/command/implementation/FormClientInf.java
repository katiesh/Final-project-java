package controller.command.implementation;

import controller.command.Command;
import service.ClientService;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * implements interface ICommand{@link Command}
 * @author Kateryna Shkulova
 */
public class FormClientInf extends Command {
    /**
     * field service client
     */
    ClientService clientService;

    /**
     * constructor without parameters
     */
    public FormClientInf() {
        clientService = new ClientService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        if (servletRequest.getMethod().equalsIgnoreCase("post")) {
            String name = servletRequest.getParameter("name");
            String surname = servletRequest.getParameter("surname");
            String telNumber = servletRequest.getParameter("tel");
            String email = servletRequest.getParameter("email");
            if (clientService.validateClient(name, surname, telNumber, email)) {
                if (clientService.create(name, surname, telNumber, email)) {
                    servletRequest.getSession().setAttribute("clientId",
                            clientService.getClientId(email));
                    servletRequest.getRequestDispatcher(ForwardPagesPaths
                            .FORM_CLIENT_ROOM.toString()).forward(servletRequest, servletResponse);
                } else {
                    servletRequest.getRequestDispatcher(ForwardPagesPaths
                            .DATA_NOT_SENT.toString()).forward(servletRequest, servletResponse);
                }
            } else {
                servletRequest.setAttribute("wrongData", "true");
                servletRequest.getRequestDispatcher(ForwardPagesPaths
                        .FORM_CLIENT_INF.toString()).forward(servletRequest, servletResponse);
            }
        } else {
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .DATA_NOT_SENT.toString()).forward(servletRequest, servletResponse);

        }
    }

}
