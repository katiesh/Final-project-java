package servlets;

import model.entity.Booking;
import model.entity.Client;
import model.entity.Request;
import service.ServiceBookings;
import service.ServiceClient;
import service.ServiceRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="ClientRequest", urlPatterns = "/client-applications")
public class ClientRequsts extends HttpServlet {
    private ServiceBookings serviceBookings;
    private ServiceClient serviceClient;
    private ServiceRequest serviceRequest;
    @Override
    public void init(){
        serviceBookings = new ServiceBookings();
        serviceClient = new ServiceClient();
        serviceRequest = new ServiceRequest();
    }
    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client = serviceClient.getClientByEmailOrTel(request.getParameter("clientData"));
        if(client!=null) {
            int currentPage;
            int recordsPerPage;
            if (request.getParameter("currentPage") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.valueOf(request.getParameter("currentPage"));
            }
            if (request.getParameter("currentPage") == null) {
                recordsPerPage = 3;
            } else {
                recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));
            }
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("recordsPerPage", recordsPerPage);

            List<Request> requestList = serviceRequest.findRequestsFromToById(currentPage,
                    recordsPerPage, client.getId());
            List<Booking> bookings = new ArrayList<>();
            for (Request requestItem : requestList) {
                if (requestItem.getStatus().equals("processed")) {
                    bookings.add(serviceBookings.findByRequestId(requestItem.getId()));
                    requestList.remove(requestItem);
                }
            }
            request.setAttribute("requests", requestList);
            request.setAttribute("bookings", bookings);

            int rows = serviceRequest.getNumOfRowsByClientId(client.getId());

            int nOfPages = rows / recordsPerPage;

            if (rows % recordsPerPage > 0) {
                nOfPages++;
            }

            request.setAttribute("noOfPages", nOfPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("recordsPerPage", recordsPerPage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("client-request.jsp");
            requestDispatcher.forward(request, response);
        }
        else{
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("data-not-sent.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
