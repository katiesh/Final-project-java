package controller.command.implamantation;

import controller.command.ICommand;
import service.ServiceRequestBooking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBooking implements ICommand {
    private ServiceRequestBooking serviceRequestBooking;

    public AddBooking() {
        serviceRequestBooking = new ServiceRequestBooking();
    }

    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try{
            serviceRequestBooking.createBookingAndChangeRequestStatus(servletRequest,servletResponse);
        }finally {
            serviceRequestBooking.closeConnections();
        }
    }
}
