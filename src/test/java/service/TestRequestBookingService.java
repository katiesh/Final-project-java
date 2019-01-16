package service;

import model.dao.factory.implementation.BookingDAO;
import model.dao.factory.implementation.RequestDAO;
import model.entity.Booking;
import model.entity.Request;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;

public class TestRequestBookingService {

    private static RequestDAO requestDAO;
    private static BookingDAO bookingDAO;
    private static RequestBookingService requestBookingService;

    @BeforeClass()
    public static void init(){
        requestDAO = Mockito.mock(RequestDAO.class);
        bookingDAO = Mockito.mock(BookingDAO.class);
        requestBookingService = new RequestBookingService(bookingDAO,requestDAO);
    }

    @Test
    public void testCreateAndChangeStatus() throws SQLException {
        String roomId = "1";
        String price = "100.0";
        Request request = new Request();
        request.setId(2);
        request.setClientId(3);
        request.setDateFrom(new Date(1548806400000L));
        request.setDateTo(new Date(1548892800000L));
        Booking booking = new Booking();
        booking.setId(1);
//        booking.setRequestId(2);
//        booking.setDateFrom(request.getDateFrom());
//        booking.setDateTo(request.getDateTo());
//        booking.setPrice();
        Mockito.when(bookingDAO.create(any(Booking.class))).thenReturn(true);
        Mockito.when(requestDAO.findEntityById(anyInt())).thenReturn(request);
        Mockito.when(requestDAO.updateStatus(any(Request.class))).thenReturn(true);
        Boolean result = requestBookingService.createBookingAndChangeRequestStatus("2", roomId, price);
        Mockito.verify(requestDAO).findEntityById(anyInt());
        Mockito.verify(bookingDAO).create(any(Booking.class));
        Mockito.verify(requestDAO).updateStatus(any(Request.class));
        Mockito.verify(bookingDAO).connCommit();
        Mockito.verify(bookingDAO).setAutoCommitTrue();
        Assert.assertTrue(result);

    }
}
