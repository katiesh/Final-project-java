package controller.command.implementation;

import model.entity.Booking;
import model.entity.Client;
import model.entity.Request;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import service.BookingService;
import service.ClientService;
import service.RequestService;
import util.constants.ForwardPagesPaths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

public class TestClintApplications {

    private static final String clientData = "email";

    private static ClientApplications clientApplications;
    private static HttpServletRequest servletRequest;
    private static HttpServletResponse servletResponse;
    private static RequestDispatcher requestDispatcher;
    private static BookingService bookingService;
    private static RequestService requestService;
    private static ClientService clientService;

    @BeforeClass
    public static void init(){
        clientApplications = new ClientApplications();
        servletRequest = Mockito.mock(HttpServletRequest.class);
        servletResponse = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
        bookingService = Mockito.mock(BookingService.class);
        clientService = Mockito.mock(ClientService.class);
        requestService = Mockito.mock(RequestService.class);
        clientApplications.bookingService = bookingService;
        clientApplications.clientService = clientService;
        clientApplications.requestService = requestService;
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Client client = new Client();
        int clientId = 1;
        client.setId(1);
        Request request1 = new Request();
        Request request2 = new Request();
        Request request3 = new Request();
        request1.setId(1);
        request2.setId(2);
        request3.setId(3);
        request1.setStatus("status");
        request2.setStatus("processed");
        request3.setStatus("status");
        Booking booking = new Booking();
        booking.setId(1);
        List<Request> requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        Mockito.when(servletRequest.getParameter("clientData")).thenReturn(clientData);
        Mockito.when(clientService.getClientByEmailOrTel(clientData)).thenReturn(client);
        Mockito.when(servletRequest.getAttribute("currentPage")).thenReturn(1);
        Mockito.when(servletRequest.getAttribute("recordsPerPage")).thenReturn(3);
        Mockito.when(requestService.getNumOfRowsByClientId(clientId)).thenReturn(7);
        Mockito.when(requestService.findRequestsForPageByClientId(1,3,clientId)).thenReturn(requests);
        Mockito.when(bookingService.findByRequestId(2)).thenReturn(booking);
        Mockito.when(servletRequest.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        clientApplications.execute(servletRequest,servletResponse);
        Mockito.verify(servletRequest).getParameter("clientData");
        Mockito.verify(requestService).getNumOfRowsByClientId(clientId);
        Mockito.verify(requestService).findRequestsForPageByClientId(1,3,clientId);
        Mockito.verify(bookingService).findByRequestId(2);
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .CLIENT_APPLICATIONS_AND_BOOKINGS.toString());
        Mockito.verify(requestDispatcher).forward(servletRequest,servletResponse);

    }
}
