package servlets;

import model.entity.Client;
import service.ServiceClient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="FormClient", urlPatterns = {"/FormClient"} )
public class FormClient extends HttpServlet {
    private Client client;
    private ServiceClient serviceClient;
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init();
        Client client = new Client();
        serviceClient = new ServiceClient();
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        performTask(req,resp);
    }

    public void performTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = new Client();
        if(serviceClient.vaildateClient(req)) {
            client.setName(req.getParameter("name"));
            client.setSurname(req.getParameter("surname"));
            client.setTelNumber(req.getParameter("tel"));
            client.setEmail(req.getParameter("email"));
            client.setId(serviceClient.getClientId(client));
            req.getSession().setAttribute("clientId", client.getId());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/requestStep2.jsp");
            requestDispatcher.forward(req,resp);
        }
        else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/data-not-sent.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/requestStep1.jsp");
        requestDispatcher.forward(req, resp);
    }
}
