package servlets;

import model.entity.Booking;
import model.entity.Request;
import service.ServiceBookings;
import service.ServiceRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="AddBooking", urlPatterns = "/booking")
public class AddBooking extends HttpServlet {
    private ServiceRequest serviceRequest = new ServiceRequest();
    private ServiceBookings serviceBookings = new ServiceBookings();
    @Override
    public void init(){
        ServiceRequest serviceRequest = new ServiceRequest();
        ServiceBookings serviceBookings = new ServiceBookings();
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int requestId = Integer.valueOf(request.getParameter("requestId"));
        int roomId = Integer.valueOf(request.getParameter("roomId"));
        Request requestItem = new Request();
        requestItem = serviceRequest.getRequestById(requestId);
        Booking booking = new Booking();
        booking.setRoomId(roomId);
        booking.setRequestId(requestId);
        booking.setClientId(requestItem.getClientId());
        booking.setDateFrom(requestItem.getDateFrom());
        booking.setDateTo(requestItem.getDateTo());
        if(serviceBookings.create(booking)) {
            serviceRequest.changeStatus(requestItem);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/booking.jsp");
            requestDispatcher.forward(request, response);
        }else{
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/data-not-sent.jsp");
            requestDispatcher.forward(request,response);
        }
    }
}
