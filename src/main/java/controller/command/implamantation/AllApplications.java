package controller.command.implamantation;

import controller.command.ICommand;
import service.ServiceRequest;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllApplications implements ICommand {

    private ServiceRequest serviceRequest;

    public AllApplications() {
        serviceRequest = new ServiceRequest();
    }

    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try{
            String checkBoxValue = servletRequest.getParameter("new_appl");
            if (checkBoxValue!=null && checkBoxValue.equals("true")){
                serviceRequest.newRequestsPagination(servletRequest);
            }else{
                serviceRequest.requestsPagination(servletRequest);
            }
            serviceRequest.forwardToPage(ForwardPagesPaths.ALL_APPLICATIONS.toString(),
                    servletRequest, servletResponse);
        }finally {
            serviceRequest.closeConnections();
        }
    }
}
