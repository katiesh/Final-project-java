package controller.command.implementation;

import controller.command.Command;
import model.entity.Booking;
import model.entity.Client;
import model.entity.Request;
import service.ServiceBookings;
import service.ServiceClient;
import service.ServiceRequest;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * implements interface ICommand{@link Command}
 * @author Kateryna Shkulova
 */
public class ClientApplications extends Command {
    /**
     * field service client
     */
    private ServiceClient serviceClient;
    /**
     * field service request
     */
    private ServiceRequest serviceRequest;
    /**
     * field service bookings
     */
    private ServiceBookings serviceBookings;
    /**
     * constructor without parameters
     */
    public ClientApplications() {
        serviceClient = new ServiceClient();
        serviceRequest = new ServiceRequest();
        serviceBookings = new ServiceBookings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
            Client client = serviceClient.getClientByEmailOrTel(servletRequest.getParameter("clientData"));
            if(client!=null){
                setCurrentPageRecordsPerPage(servletRequest);
                int currentPage = (Integer) servletRequest.getAttribute("currentPage");
                int recordsPerPage = (Integer) servletRequest.getAttribute("recordsPerPage");
                setNumOfPages(serviceRequest.getNumOfRowsByClientId(client.getId()), servletRequest);
                List<Request> requestForPage = serviceRequest.findRequestsForPageById(currentPage, recordsPerPage,client.getId());
                List<Booking> bookingsForPage = new ArrayList<>();
                for (Iterator<Request> iterator = requestForPage.iterator(); iterator.hasNext();) {
                    Request requestItem = iterator.next();
                    if (requestItem.getStatus().equals("processed")) {
                        bookingsForPage.add(serviceBookings.findByRequestId(requestItem.getId()));
                        iterator.remove();
                    }
                }
                if(requestForPage.isEmpty() && bookingsForPage.isEmpty()){
                    servletRequest.setAttribute("nothigFound", "true");
                }else{
                    servletRequest.setAttribute("requests", requestForPage);
                    servletRequest.setAttribute("bookings", bookingsForPage);
                }
                servletRequest.getRequestDispatcher(ForwardPagesPaths
                        .CLIENT_APPLICATIONS_AND_BOOKINGS.toString()).forward(servletRequest, servletResponse);
            }else{
                servletRequest.setAttribute("clientNotFound", "true");
                servletRequest.getRequestDispatcher(ForwardPagesPaths
                        .FORM_CLIENT_EMAIL_TEL.toString()).forward(servletRequest, servletResponse);
            }
    }
}
