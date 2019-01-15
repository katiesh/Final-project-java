package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Controller class
 * @author Kateryna Shkulova
 */
@WebServlet(name="Admin", urlPatterns = {"/admin"})
public class ControllerAdmin extends Controller {
    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        performTask(servletRequest,servletResponse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        performTask(servletRequest,servletResponse);
    }
}
