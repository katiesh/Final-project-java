package controller.command.implementation;

import model.entity.Booking;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import service.BookingService;
import util.constants.ForwardPagesPaths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

public class TestAllBookings {

    private static AllBookings allBookings;
    private static HttpServletRequest servletRequest;
    private static HttpServletResponse servletResponse;
    private static RequestDispatcher requestDispatcher;
    private static BookingService bookingService;

    @BeforeClass
    public static void init(){
        allBookings = new AllBookings();
        servletRequest = Mockito.mock(HttpServletRequest.class);
        servletResponse = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
        bookingService = Mockito.mock(BookingService.class);
        allBookings.bookingService = bookingService;
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        List<Booking> bookings = Arrays.asList(new Booking(), new Booking());
        Mockito.when(servletRequest.isUserInRole("admin")).thenReturn(true);
        Mockito.when(bookingService.getNumOfRows()).thenReturn(7);
        Mockito.when(servletRequest.getAttribute("currentPage")).thenReturn(1);
        Mockito.when(servletRequest.getAttribute("recordsPerPage")).thenReturn(3);
        Mockito.when(bookingService.findBookingsForCurrentPage(anyInt(), anyInt())).thenReturn(bookings);
        Mockito.when(servletRequest.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        allBookings.execute(servletRequest,servletResponse);
        Mockito.verify(servletRequest).isUserInRole("admin");
        Mockito.verify(bookingService).getNumOfRows();
        Mockito.verify(bookingService).findBookingsForCurrentPage(1,3);
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .ALL_BOOKINGS.toString());
        Mockito.verify(requestDispatcher).forward(servletRequest,servletResponse);
    }

}
