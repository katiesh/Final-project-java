package controller.command.implementation;

import controller.command.Command;
import model.entity.Booking;
import service.BookingService;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * implements interface ICommand{@link Command}
 *
 * @author Kateryna Shkulova
 */
public class AllBookings extends Command {
    /**
     * field service bookings
     */
    BookingService bookingService;

    /**
     * constructor without parameters
     */
    public AllBookings() {
        bookingService = new BookingService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        if(servletRequest.isUserInRole("admin")){
            setCurrentPageRecordsPerPage(servletRequest);
            setNumOfPages(bookingService.getNumOfRows(), servletRequest);
            List<Booking> bookingsForPage = bookingService.findBookingsForCurrentPage((Integer) servletRequest.getAttribute("currentPage"),
                    (Integer) servletRequest.getAttribute("recordsPerPage"));
            if(bookingsForPage.isEmpty()){
                servletRequest.setAttribute("nothingFound", "true");
            }
            servletRequest.setAttribute("bookings", bookingsForPage);
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .ALL_BOOKINGS.toString()).forward(servletRequest, servletResponse);
        } else{
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .ERROR.toString()).forward(servletRequest, servletResponse);
        }
    }
}
