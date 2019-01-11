package controller.command.implamantation;

import controller.command.ICommand;
import model.entity.Client;
import service.ServiceClient;
import service.ServiceRequestBooking;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientApplications implements ICommand {

    private ServiceClient serviceClient;
    private ServiceRequestBooking serviceRequestBooking;

    public ClientApplications() {
        serviceClient = new ServiceClient();
        serviceRequestBooking = new ServiceRequestBooking();
    }

    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try{
            Client client = serviceClient.getClientByEmailOrTel(servletRequest.getParameter("clientData"));
            if(client!=null){
                serviceRequestBooking.requestsBookingsPagination(servletRequest, client.getId());
                serviceClient.forwardToPage(ForwardPagesPaths.CLIENT_APPLICATIONS_AND_BOOKINGS.toString(),
                        servletRequest,servletResponse);
            }else{
                serviceClient.forwardToPage(ForwardPagesPaths.ERROR.toString(),servletRequest,servletResponse);
            }
        }finally {
            serviceRequestBooking.closeConnections();
            serviceClient.closeConnections();
        }
    }
}
