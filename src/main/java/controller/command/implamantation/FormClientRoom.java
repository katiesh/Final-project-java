package controller.command.implamantation;

import controller.command.ICommand;
import service.ServiceRequest;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormClientRoom implements ICommand {

    private ServiceRequest serviceRequest;

    public FormClientRoom() {
        serviceRequest = new ServiceRequest();
    }

    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try{
            if(serviceRequest.validateRequestForm(servletRequest)) {
                if(serviceRequest.create(servletRequest)) {
                    serviceRequest.forwardToPage(ForwardPagesPaths.THANKS_PAGE.toString(),
                            servletRequest, servletResponse);
                }else{
                    serviceRequest.forwardToPage(ForwardPagesPaths.DATA_NOT_SENT.toString(),
                            servletRequest, servletResponse);
                }
            }else{
                serviceRequest.forwardToPage(ForwardPagesPaths.WRONG_DATA.toString(),
                        servletRequest,servletResponse);
            }
        } finally {
            serviceRequest.closeConnections();
        }
    }
}
