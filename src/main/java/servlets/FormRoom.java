package servlets;

import model.entity.Request;
import service.Service;
import service.ServiceRequest;
import util.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="FormRoom", urlPatterns = "/finish")
public class FormRoom extends HttpServlet {
    private Service service;
    private ServiceRequest serviceRequest;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        service = new Service();
        serviceRequest = new ServiceRequest();
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        performTask(req,resp);
    }

    public void performTask(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
        if(serviceRequest.validateRequestForm(req)) {
            Request request = new Request();
            request.setClassOfRoom(req.getParameter("class"));
            request.setNumOfPlaces(Integer.valueOf(req.getParameter("numOfPlaces")));
            request.setDateFrom(new java.sql.Date(Validator.isCorrectDate(req.getParameter("dateFrom")).getTime()));
            request.setDateTo(new java.sql.Date(Validator.isCorrectDate(req.getParameter("dateTo")).getTime()));
            service.makeOrder(req.getSession().getAttribute("clientId").toString(), request);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/thanks-page.jsp");
            requestDispatcher.forward(req,resp);
            }else {
            if(true){
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/data-not-sent.jsp");
            requestDispatcher.forward(req, resp);
            }else{

            }
        }
    }

}
