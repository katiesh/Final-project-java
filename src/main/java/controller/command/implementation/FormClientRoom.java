package controller.command.implementation;

import controller.command.Command;
import service.RequestService;
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
    RequestService requestService;

    /**
     * constructor without parameters
     */
    public FormClientRoom() {
        requestService = new RequestService();
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
            if(requestService.validateRequestForm(classOfRoom, numOfPlaces, dateFrom, dateTo, clientId)) {
                if(requestService.create(classOfRoom, numOfPlaces, dateFrom, dateTo, clientId)) {
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
        }else{
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .DATA_NOT_SENT.toString()).forward(servletRequest, servletResponse);
        }
    }
}
