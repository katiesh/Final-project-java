package controller.strategy.implamantation;

import controller.strategy.ICommand;
import service.ServiceBookings;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllBookings implements ICommand {

    private ServiceBookings serviceBookings;

    public AllBookings() {
        serviceBookings = new ServiceBookings();
    }

    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try{
            serviceBookings.bookingsPagination(servletRequest);
            serviceBookings.forwardToPage(ForwardPagesPaths.ALL_BOOKINGS.toString(),
                    servletRequest,servletResponse);
        }finally {
            serviceBookings.closeConnections();
        }
    }
}
