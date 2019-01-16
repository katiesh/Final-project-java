package service;

import model.dao.factory.implementation.BookingDAO;
import model.entity.Booking;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

public class TestServiceBooking {
    private static BookingService bookingService;
    private static BookingDAO bookingDAO;

    @BeforeClass
    public static void init(){
        bookingDAO = Mockito.mock(BookingDAO.class);
        bookingService = new BookingService(bookingDAO);
    }

    @Test
    public void testFindWithOffsetFromPosition(){
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        List<Booking> bookings = Arrays.asList(booking1, booking2);
        Mockito.when(bookingDAO.findWithOffsetFromPosition(anyInt(),anyInt())).thenReturn(bookings);
        List<Booking> result = bookingService.findBookingsForCurrentPage(3,3);
        Mockito.verify(bookingDAO).findWithOffsetFromPosition(6,3);
        Mockito.verifyNoMoreInteractions(bookingDAO);
        Assert.assertEquals(bookings, result);
    }

    @Test
    public void testGetRoomsIdsByDate(){
        List<Integer> roomsIds = Arrays.asList(1,3,4,5);
        Mockito.when(bookingDAO.findRoomsIdByDate(any(Date.class), any(Date.class))).thenReturn(roomsIds);
        List<Integer> result = bookingService.getRoomsIdsByDate(new Date(1), new Date(2));
        Mockito.verify(bookingDAO).findRoomsIdByDate(new Date(1), new Date(2));
        Mockito.verifyNoMoreInteractions(bookingDAO);
        Assert.assertEquals(roomsIds, result);
    }

    @Test
    public void testGetNumOfRows(){
        int rows = 5;
        Mockito.when(bookingDAO.getNumOfRows()).thenReturn(rows);
        int result = bookingService.getNumOfRows();
        Mockito.verify(bookingDAO).getNumOfRows();
        Mockito.verifyNoMoreInteractions(bookingDAO);
        Assert.assertEquals(rows, result);
    }

    @Test
    public void testFindByRequestId(){
        Booking booking = new Booking();
        int requestId = 5;
        Mockito.when(bookingDAO.findByRequestId(anyInt())).thenReturn(booking);
        Booking result = bookingService.findByRequestId(requestId);
        Mockito.verify(bookingDAO).findByRequestId(requestId);
        Mockito.verifyNoMoreInteractions(bookingDAO);
        Assert.assertEquals(booking, result);

    }
}
