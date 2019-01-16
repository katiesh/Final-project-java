package controller.command.implementation;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import service.RequestBookingService;
import util.constants.ForwardPagesPaths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;

public class TestAddBooking {

    private static final String requestId = "2";
    private static final String roomId = "1";
    private static final String price = "100.0";;

    private static AddBooking addBooking;
    private static HttpServletRequest servletRequest;
    private static HttpServletResponse servletResponse;
    private static RequestDispatcher requestDispatcher;
    private static RequestBookingService requestBookingService;

    @BeforeClass
    public static void init() throws SQLException {
        addBooking = new AddBooking();
        servletRequest = Mockito.mock(HttpServletRequest.class);
        servletResponse = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
        requestBookingService = Mockito.mock(RequestBookingService.class);
        addBooking.requestBookingService = requestBookingService;
    }

//    @AfterClass
//    public static void forward() throws ServletException, IOException {
//        Mockito.verify(requestDispatcher).forward(servletRequest,servletResponse);
//    }

    @Test
    public void testDoPost() throws ServletException, IOException, SQLException {
        Mockito.when(servletRequest.getMethod()).thenReturn("post");
        Mockito.when(servletRequest.getParameter("requestId")).thenReturn(requestId);
        Mockito.when(servletRequest.getParameter("roomId")).thenReturn(roomId);
        Mockito.when(servletRequest.getParameter("roomPrice")).thenReturn(price);
        Mockito.when(requestBookingService.createBookingAndChangeRequestStatus(anyString(),
                anyString(), anyString())).thenReturn(true);
        Mockito.when(servletRequest.getRequestDispatcher(ForwardPagesPaths
                .BOOKING_IS_CREATED.toString())).thenReturn(requestDispatcher);
        addBooking.execute(servletRequest,servletResponse);
        Mockito.verify(requestBookingService).createBookingAndChangeRequestStatus(requestId,
                roomId, price);
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .BOOKING_IS_CREATED.toString());
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(servletRequest.getMethod()).thenReturn("get");
        Mockito.when(servletRequest.getRequestDispatcher(ForwardPagesPaths
                .MAIN_PAGE.toString())).thenReturn(requestDispatcher);
        addBooking.execute(servletRequest,servletResponse);
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .MAIN_PAGE.toString());
    }
}
