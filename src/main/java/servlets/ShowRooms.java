package servlets;

import model.entity.Request;
import model.entity.Room;
import service.ServiceRequest;
import service.ServiceRoom;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="ShowRooms", urlPatterns = {"/rooms"})
public class ShowRooms extends HttpServlet {
    private ServiceRequest serviceRequest = new ServiceRequest();
    private ServiceRoom serviceRoom = new ServiceRoom();
    @Override
    public void init(){
        ServiceRequest serviceRequest = new ServiceRequest();
        ServiceRoom serviceRoom = new ServiceRoom();
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ServiceBookings serviceBookings = new ServiceBookings();
        int id = Integer.valueOf(request.getParameter("requestId"));
        request.setAttribute("requestId", id);
        Request requestItem = serviceRequest.getRequestById(id);
        request.setAttribute("requestItem", requestItem);
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

        List<Room> rooms = serviceRoom.findRooms(currentPage,
                recordsPerPage);
        request.setAttribute("rooms", rooms);

        int rows = serviceRoom.getNumOfRows();

        int nOfPages = rows / recordsPerPage;

        if (rows % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

//        request.setAttribute("rooms", serviceRoom.getSuitableRooms(requestItem.getClassOfRoom(),
//                serviceBookings.getByDate(requestItem.getDateFrom(),requestItem.getDateTo()),requestItem.getNumOfPlaces()));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin-cards.jsp");
        requestDispatcher.forward(request,response);
    }
}
