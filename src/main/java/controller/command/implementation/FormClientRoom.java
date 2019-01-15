package controller.command.implementation;

import controller.command.Command;
import service.ServiceRequest;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * implements interface ICommand{@link Command}
 * @author Kateryna Shkulova
 */
public class FormClientRoom extends Command {
    /**
     * field service request
     */
    private ServiceRequest serviceRequest;

    /**
     * constructor without parameters
     */
    public FormClientRoom() {
        serviceRequest = new ServiceRequest();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        if(servletRequest.getMethod().equalsIgnoreCase("post")){
            String classOfRoom = servletRequest.getParameter("class");
            String numOfPlaces = servletRequest.getParameter("numOfPlaces");
            String dateFrom = servletRequest.getParameter("dateFrom");
            String dateTo = servletRequest.getParameter("dateTo");
            Object clientId = servletRequest.getSession().getAttribute("clientId");
            if(serviceRequest.validateRequestForm(classOfRoom, numOfPlaces, dateFrom, dateTo, clientId)) {
                if(serviceRequest.create(classOfRoom, numOfPlaces, dateFrom, dateTo, clientId)) {
                    servletRequest.getRequestDispatcher(ForwardPagesPaths
                            .THANKS_PAGE.toString()).forward(servletRequest, servletResponse);
                }else{
                    servletRequest.getRequestDispatcher(ForwardPagesPaths
                            .DATA_NOT_SENT.toString()).forward(servletRequest, servletResponse);
                }
            }else{
                servletRequest.setAttribute("wrongData", "true");
                servletRequest.getRequestDispatcher(ForwardPagesPaths
                        .FORM_CLIENT_ROOM.toString()).forward(servletRequest, servletResponse);
            }
        }
    }
}
