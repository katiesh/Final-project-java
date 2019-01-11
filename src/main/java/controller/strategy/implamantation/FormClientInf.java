package controller.strategy.implamantation;

import controller.strategy.ICommand;
import service.ServiceClient;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormClientInf implements ICommand {

    private ServiceClient serviceClient;

    public FormClientInf() {
        serviceClient = new ServiceClient();
    }

    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try{
            if(serviceClient.validateClient(servletRequest)) {
                if(serviceClient.create(servletRequest)) {
                    servletRequest.getSession().setAttribute("clientId",
                            serviceClient.getClientId(servletRequest.getParameter("email")));
                    serviceClient.forwardToPage(ForwardPagesPaths.FORM_CLIENT_ROOM.toString(),
                            servletRequest,servletResponse);
                }
                else{
                    serviceClient.forwardToPage(ForwardPagesPaths.DATA_NOT_SENT.toString(),
                            servletRequest,servletResponse);
                }
            }
            else{
                //???????????? ??????
                serviceClient.forwardToPage(ForwardPagesPaths.WRONG_DATA.toString(),
                        servletRequest,servletResponse);
            }
        } finally {
            serviceClient.closeConnections();
        }
    }
}
