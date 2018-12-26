package servlets;

import model.entity.Request;
import service.ServiceRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="ShowApplications", urlPatterns = {"/application"})
public class ShowApplications extends HttpServlet {
    private ServiceRequest serviceRequest = new ServiceRequest();
    @Override
    public void init(){
        ServiceRequest serviceRequest = new ServiceRequest();
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
        if(request.getParameter("currentPage")==null){
            recordsPerPage = 3;
        }else{
            recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));
        }
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        List<Request> requestList = serviceRequest.findRequests(currentPage,
                recordsPerPage);

        request.setAttribute("requests", requestList);

        int rows = serviceRequest.getNumOfRows();

        int nOfPages = rows / recordsPerPage;

        if (rows % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }
}
