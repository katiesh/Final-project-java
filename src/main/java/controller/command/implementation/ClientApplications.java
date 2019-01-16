package controller.command.implementation;

import controller.command.Command;
import model.entity.Booking;
import model.entity.Client;
import model.entity.Request;
import service.BookingService;
import service.ClientService;
import service.RequestService;
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
    ClientService clientService;
    /**
     * field service request
     */
    RequestService requestService;
    /**
     * field service bookings
     */
    BookingService bookingService;
    /**
     * constructor without parameters
     */
    public ClientApplications() {
        clientService = new ClientService();
        requestService = new RequestService();
        bookingService = new BookingService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
            Client client = clientService.getClientByEmailOrTel(servletRequest.getParameter("clientData"));
            if(client!=null){
                setCurrentPageRecordsPerPage(servletRequest);
                int currentPage = (Integer) servletRequest.getAttribute("currentPage");
                int recordsPerPage = (Integer) servletRequest.getAttribute("recordsPerPage");
                setNumOfPages(requestService.getNumOfRowsByClientId(client.getId()), servletRequest);
                List<Request> requestForPage = requestService.findRequestsForPageByClientId(currentPage, recordsPerPage,client.getId());
                List<Booking> bookingsForPage = new ArrayList<>();
                for (Iterator<Request> iterator = requestForPage.iterator(); iterator.hasNext();) {
                    Request requestItem = iterator.next();
                    if (requestItem.getStatus().equals("processed")) {
                        bookingsForPage.add(bookingService.findByRequestId(requestItem.getId()));
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
