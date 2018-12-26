package servlets;

import service.ServiceClient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="OrderStatus", urlPatterns = {"/orderstatus"})
public class OrderStatus extends HttpServlet {
    private ServiceClient serviceClient;
    @Override
    public void init(){
        serviceClient = new ServiceClient();
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/client-id.jsp");
        requestDispatcher.forward(req,resp);

    }

}
