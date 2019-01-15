package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.implementation.BookingDAO;
import model.dao.factory.implementation.RequestDAO;
import model.entity.Booking;
import model.entity.Request;
import util.Validator;
import util.constants.TypesDAO;

import java.sql.Date;
import java.sql.SQLException;

/**
 * provides services with requests and bookings
 * @author Kateryna Shkulova
 */
public class ServiceRequestBooking {
    /**
     * field requestDAO
     */
    private RequestDAO requestDAO;
    /**
     * field bookingDAO
     */
    private BookingDAO bookingDAO;

    /**
     * constructor without parameters
     */
    public ServiceRequestBooking() {
        bookingDAO = (BookingDAO) FactoryDao.getDAO(TypesDAO.BOOKING);
        requestDAO = new RequestDAO(bookingDAO.getConnection());
    }


    /**
     * get the request by its id
     * @param id is the id of request
     * @return found request
     */
    private Request getRequestById(int id){
        return requestDAO.findEntityById(id);
    }

    /**
     * add the booking to database
     * <p>
     *     sets the booking with parameters from servlet request
     * </p>
     * @param roomId is the room id
     * @param requestItem is the request
     * @param roomPrice is the price of the room
     * @return the result of creation booking in datav=base
     */
    private boolean createBooking(int roomId, Request requestItem, double roomPrice) throws SQLException {
        Booking booking = new Booking();
        booking.setRoomId(roomId);
        booking.setRequestId(requestItem.getId());
        booking.setClientId(requestItem.getClientId());
        booking.setDateFrom(requestItem.getDateFrom());
        booking.setDateTo(requestItem.getDateTo());
        booking.setPrice(calculatePrice(requestItem.getDateFrom(),requestItem.getDateTo(), roomPrice));
        return bookingDAO.create(booking);
    }

    /**
     * calculate the price for booking
     * @param dateFrom date from
     * @param dateTo date to
     * @param pricePreDay price per day
     * @return calculated price
     */
    private double calculatePrice(Date dateFrom, Date dateTo, double pricePreDay){
        int days = (int)(dateTo.getTime()-dateFrom.getTime())/(1000*60*60*24);
        return days*pricePreDay;
    }

    /**
     * change the request status to processed
     * @param request is the request which status should be changed
     */
    private void changeRequestStatusToProcessed(Request request) throws SQLException {
        requestDAO.updateStatus(request);
    }

    /**
     * create booking and change the status of request for which the booking was created
     * @param strRequestId is string with request id
     * @param strRoomId is string with room id
     * @param strRoomPrice is string with room price
     * @return true if booking is created and status is changed
     */
    public boolean createBookingAndChangeRequestStatus(String strRequestId, String strRoomId, String strRoomPrice) throws SQLException {
        if(Validator.isCorrectInt(strRequestId) && Validator.isCorrectInt(strRoomId) &&
        Validator.isCorrectDouble(strRoomPrice)){
            int requestId = Integer.valueOf(strRequestId);
            int roomId = Integer.valueOf(strRoomId);
            double roomPrice = Double.valueOf(strRoomPrice);
            Request requestItem = getRequestById(requestId);
            if(createBooking(roomId,requestItem, roomPrice)) {
                try{
                    changeRequestStatusToProcessed(requestItem);
                    bookingDAO.connCommit();
                    return true;
                }catch (SQLException e){
                    bookingDAO.rollback();
                    return false;
                }
                finally {
                    bookingDAO.setAutoCommitTrue();
                }
            }
        }
        return false;
    }
}
