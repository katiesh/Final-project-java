package model.dao.factory.implementation;

import model.dao.factory.FactoryDao;
import model.entity.Booking;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import util.constants.TypesDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestBookingDAO {
    private static Booking booking;
    private static BookingDAO bookingDAO;
    @BeforeClass
    public static void init(){
        bookingDAO = (BookingDAO) FactoryDao.getDAO(TypesDAO.BOOKING);
        booking = new Booking();
        booking.setRoomId(2);
        booking.setRequestId(9);
        booking.setDateFrom(new Date(1548806400000L));
        booking.setDateTo(new Date(1548892800000L));
        booking.setClientId(2);
        booking.setPrice(35);
        booking.setId(11);
    }

    @Test
    public void testFindEntityById(){
        Assert.assertEquals(booking, bookingDAO.findEntityById(booking.getId()));
    }

    @Test
    public void testFindByRequestId(){
        Assert.assertEquals(booking, bookingDAO.findByRequestId(booking.getRequestId()));
    }

    @Test
    public void testFindRoomsIdsByDate(){
        List<Integer> expectedRoomsIds = new ArrayList<>();
        expectedRoomsIds.add(2);
        expectedRoomsIds.add(18);
        expectedRoomsIds.add(13);
        Assert.assertEquals(expectedRoomsIds,
                bookingDAO.findRoomsIdByDate(booking.getDateFrom(), booking.getDateTo()));
    }

    @Test
    public void testGetNumOfRows(){
        List<Booking> bookings = bookingDAO.getAll();
        Assert.assertEquals(bookings.size(), bookingDAO.getNumOfRows());
    }

    @Test
    public void testFindWithOffsetFromPosition(){
        List <Booking> bookings = bookingDAO.getAll();
        List<Booking> expected = new ArrayList<>();
        for (int i = bookings.size()/3; i > bookings.size()/9 ; i--) {
            expected.add(bookings.get(i));
        }
        Assert.assertEquals(expected,
                bookingDAO.findWithOffsetFromPosition(bookings.size()-bookings.size()/3-1, expected.size()));
    }

    @Test
    public void testCreateDelete() throws SQLException {
        Booking newBooking = new Booking();
        newBooking.setClientId(2);
        newBooking.setRequestId(21);
        newBooking.setRoomId(25);
        newBooking.setPrice(35);
        newBooking.setDateFrom(new Date(1551571200000L));
        newBooking.setDateTo(new Date(1551744000000L));
        boolean created = false;
        try{
            bookingDAO.create(newBooking);
            bookingDAO.connCommit();
            for (Booking bookingIter:bookingDAO.getAll()) {
                newBooking.setId(bookingIter.getId());
                if(bookingIter.equals(newBooking)){
                    created = true;
                    break;
                }
            }
        }catch (SQLException e){
            bookingDAO.rollback();
        }finally {
            bookingDAO.setAutoCommitTrue();
        }
        bookingDAO.delete(newBooking);
        Assert.assertTrue(created && bookingDAO.findEntityById(newBooking.getId())==null);

    }

}
