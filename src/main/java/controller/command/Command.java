package controller.command;

import util.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * interface provides execute method
 * @author Kateryna Shkulova
 */
public abstract class Command {
    /**
     * process the client request and response
     * @param servletRequest is the request from client to server
     * @param servletResponse is the server's response to client
     * @throws ServletException if ServletException occurs
     * @throws IOException if IOException occurs
     */
    public abstract void execute( HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException;

    /**
     * sets current page and records per page
     * <p>
     *     if request doesn't contain parameter currentPage/recordsPerPage
     *     then currentPage/recordsPerPage will be set as 1/3
     *     if request contains parameter currentPage/recordsPerPage
     *     and it is correct{@link Validator#isCorrectInt(String)}
     *     then currentPage/recordsPerPage will be set as the parameter from request
     *     At the end set the attributes currentPage and recordsPerPage
     * </p>
     * @param servletRequest is the sent request to the servlet
     */
    public void setCurrentPageRecordsPerPage(HttpServletRequest servletRequest){
        int currentPage;
        int recordsPerPage;
        String parameter;
        if (servletRequest.getParameter("currentPage") == null) {
            currentPage = 1;
        } else {
            parameter = servletRequest.getParameter("currentPage");
            if (Validator.isCorrectInt(parameter)) {
                currentPage = Integer.valueOf(servletRequest.getParameter("currentPage"));
            } else {
                currentPage = 1;
            }
        }
        if (servletRequest.getParameter("recordsPage") == null) {
            recordsPerPage = 3;
        } else {
            parameter = servletRequest.getParameter("recordsPerPage");
            if (Validator.isCorrectInt(parameter)) {
                recordsPerPage = Integer.valueOf(servletRequest.getParameter("recordsPerPage"));
            } else {
                recordsPerPage = 3;
            }
        }

        servletRequest.setAttribute("currentPage", currentPage);
        servletRequest.setAttribute("recordsPerPage", recordsPerPage);
    }

    /**
     * sets number of pages for pagination
     * @param rows is the number of all rows
     * @param servletRequest is the sent request to the servlet
     */
    public void setNumOfPages(int rows, HttpServletRequest servletRequest) {
        int recordsPerPage;
        int numOfPages;
        recordsPerPage = (Integer) (servletRequest.getAttribute("recordsPerPage"));
        numOfPages = rows / recordsPerPage;
        if (rows % recordsPerPage > 0) {
            numOfPages++;
        }
        servletRequest.setAttribute("noOfPages", numOfPages);
    }
}
