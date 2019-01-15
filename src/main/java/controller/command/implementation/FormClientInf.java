package controller.command.implementation;

import controller.command.Command;
import service.ServiceClient;
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
    private ServiceClient serviceClient;

    /**
     * constructor without parameters
     */
    public FormClientInf() {
        serviceClient = new ServiceClient();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
            if(servletRequest.getMethod().equalsIgnoreCase("post")){
                String name = servletRequest.getParameter("name");
                String surname =  servletRequest.getParameter("surname");
                String telNumber = servletRequest.getParameter("tel");
                String email = servletRequest.getParameter("email");
                if(serviceClient.validateClient(name,surname, telNumber, email)) {
                    if(serviceClient.create(name, surname, telNumber, email)) {
                        servletRequest.getSession().setAttribute("clientId",
                                serviceClient.getClientId(servletRequest.getParameter("email")));
                        servletRequest.getRequestDispatcher(ForwardPagesPaths
                                .FORM_CLIENT_ROOM.toString()).forward(servletRequest, servletResponse);
                    }
                    else{
                        servletRequest.getRequestDispatcher(ForwardPagesPaths
                                .DATA_NOT_SENT.toString()).forward(servletRequest, servletResponse);
                    }
                }
                else{
                    servletRequest.setAttribute("wrongData", "true");
                    servletRequest.getRequestDispatcher(ForwardPagesPaths
                            .FORM_CLIENT_INF.toString()).forward(servletRequest, servletResponse);
                }
            }
    }
}
