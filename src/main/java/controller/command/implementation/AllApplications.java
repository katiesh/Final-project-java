package controller.command.implementation;

import controller.command.Command;
import model.entity.Request;
import service.ServiceRequest;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * implements interface ICommand{@link Command}
 * @author Kateryna Shkulova
 */
public class AllApplications extends Command {
    /**
     * field service request
     */
    private ServiceRequest serviceRequest;

    /**
     * constructor without parameters
     */
    public AllApplications() {
        serviceRequest = new ServiceRequest();
    }

    /**
     *{@inheritDoc}
     * <p>
     *     shows not processed applications if checkbox is marked
     *     shows all applications if checkbox is not marked
     * </p>
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        if (servletRequest.isUserInRole("admin")){
            List<Request> requestsForPage;
            String checkBoxValue = servletRequest.getParameter("new_appl");
            setCurrentPageRecordsPerPage(servletRequest);
            if (checkBoxValue!=null && checkBoxValue.equals("true")){
                setNumOfPages(serviceRequest.getNumOfRowsForNewRequests(), servletRequest);
                requestsForPage = serviceRequest.findNewRequestsForCurrentPage((Integer) (servletRequest.getAttribute("currentPage"))
                        , (Integer) (servletRequest.getAttribute("recordsPerPage")));
            }else{
                setNumOfPages(serviceRequest.getNumOfRows(), servletRequest);
                requestsForPage = serviceRequest.findAllRequestsForCurrentPage((Integer) (servletRequest.getAttribute("currentPage"))
                        , (Integer) (servletRequest.getAttribute("recordsPerPage")));
            }
            if(requestsForPage!=null) {
                servletRequest.setAttribute("requests", requestsForPage);
            } else{
                servletRequest.setAttribute("nothingFound", "true");
            }
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .ALL_APPLICATIONS.toString()).forward(servletRequest, servletResponse);
        } else{
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .ERROR.toString()).forward(servletRequest, servletResponse);
        }

    }
}
