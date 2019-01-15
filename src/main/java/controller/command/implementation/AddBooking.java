package controller.command.implementation;

import controller.command.Command;
import org.apache.log4j.Logger;
import service.ServiceRequestBooking;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * implements interface ICommand{@link Command}
 * @author Kateryna Shkulova
 */
public class AddBooking extends Command {
    /**
     * logger field
     */
    Logger logger = Logger.getLogger(AddBooking.class);
    /**
     * field service request and booking
     */
    private ServiceRequestBooking serviceRequestBooking;

    /**
     * constructor without parameters
     */
    public AddBooking() {
        serviceRequestBooking = new ServiceRequestBooking();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        if(servletRequest.getMethod().equalsIgnoreCase("post")){
            String requestId = servletRequest.getParameter("requestId") ;
            String roomId = servletRequest.getParameter("roomId");
            String roomPrice = servletRequest.getParameter("roomPrice");
            try {
                if(serviceRequestBooking.createBookingAndChangeRequestStatus(requestId, roomId, roomPrice)){
                    servletRequest.getRequestDispatcher(ForwardPagesPaths
                            .BOOKING_IS_CREATED.toString()).forward(servletRequest, servletResponse);
                }else{
                    servletRequest.getRequestDispatcher(ForwardPagesPaths
                            .ERROR.toString()).forward(servletRequest, servletResponse);
                }
            } catch (SQLException e) {
                logger.error(e);
                servletRequest.getRequestDispatcher(ForwardPagesPaths
                        .ERROR.toString()).forward(servletRequest, servletResponse);
            }
        } else {
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .MAIN_PAGE.toString()).forward(servletRequest, servletResponse);
        }
    }
}
