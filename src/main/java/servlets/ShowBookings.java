package servlets;

import model.entity.Booking;
import service.ServiceBookings;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="ShowBookings", urlPatterns = {"/bookings"})
public class ShowBookings extends HttpServlet {
    private ServiceBookings serviceBookings;
    @Override
    public void init() {serviceBookings = new ServiceBookings();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage;
        int recordsPerPage;
        if(request.getParameter("currentPage")==null){
            currentPage = 1;
        }else{
            currentPage = Integer.valueOf(request.getParameter("currentPage"));
        }
        if(request.getParameter("recordsPerPage")==null){
            recordsPerPage = 3;
        }else{
            recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));
        }
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        List<Booking> bookings = serviceBookings.findBookings(currentPage,
                recordsPerPage);
        request.setAttribute("bookings", bookings);

        int rows = serviceBookings.getNumOfRows();

        int nOfPages = rows / recordsPerPage;

        if (rows % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/bookings.jsp");
        requestDispatcher.forward(request,response);
    }
}
