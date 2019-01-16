package controller.command.implementation;

import controller.command.Command;
import model.entity.Request;
import service.RequestService;
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
    RequestService requestService;

    /**
     * constructor without parameters
     */
    public AllApplications() {
        requestService = new RequestService();
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
                setNumOfPages(requestService.getNumOfRowsForNewRequests(), servletRequest);
                requestsForPage = requestService.findNewRequestsForCurrentPage((Integer) (servletRequest.getAttribute("currentPage"))
                        , (Integer) (servletRequest.getAttribute("recordsPerPage")));
            }else{
                setNumOfPages(requestService.getNumOfRows(), servletRequest);
                requestsForPage = requestService.findAllRequestsForCurrentPage((Integer) (servletRequest.getAttribute("currentPage"))
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
