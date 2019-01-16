package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.implementation.BookingDAO;
import model.entity.Booking;
import util.constants.TypesDAO;

import java.sql.Date;
import java.util.List;

/**
 * provides services for bookings
 * @author Kateryna Shkulova
 */
public class BookingService {
    /**
     * field dao
     */
    private BookingDAO dao;

    /**
     * constructor without parameters
     */
    public BookingService() {
        dao = (BookingDAO) FactoryDao.getDAO(TypesDAO.BOOKING);
    }

    protected BookingService(BookingDAO dao){
        this.dao = dao;
    }
    /**
     * gets bookings for current page
     * @param currentPage is the current page
     * @param recordsPerPage is the number of records per page
     * @return found list of bookings
     */
    public List<Booking> findBookingsForCurrentPage(int currentPage, int recordsPerPage){
        return dao.findWithOffsetFromPosition((currentPage - 1) * recordsPerPage, recordsPerPage);
    }

    /**
     * gets ids of booked rooms by dates from and to
     * @param from is a date from
     * @param to is a date to
     * @return
     */
    public List<Integer> getRoomsIdsByDate(Date from, Date to){
        return dao.findRoomsIdByDate(from, to);
    }

    /**
     * gets number of rows
     * return number of rows
     */
    public int getNumOfRows(){
        return dao.getNumOfRows();
    }

    /**
     * gets booking by request id
     * @param requestId is the request id
     * @return found booking {@link BookingDAO#findByRequestId(int)}
     */
    public Booking findByRequestId(int requestId){
        return dao.findByRequestId(requestId);
    }
}
