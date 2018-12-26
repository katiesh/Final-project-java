package service;

import model.dao.factory.BookingDAO;
import model.dao.factory.FactoryDao;
import model.entity.Booking;

import java.sql.Date;
import java.util.List;

public class ServiceBookings {
    private BookingDAO dao;

    public ServiceBookings() {
        dao = (BookingDAO) FactoryDao.createDAO("booking");
    }

    public List<Integer> getByDate(Date from, Date to){
        return dao.findRoomsIdByDate(from, to);
    }

    public boolean create(Booking booking){
        return dao.create(booking);
    }

    public int getNumOfRows(){
        return dao.getNumOfRows();
    }

    public List<Booking> findBookings(int currentPage, int recordsPerPage){
        return dao.findFromTo((currentPage-1)*recordsPerPage, recordsPerPage);
    }

    public Booking findBookingById(int id){
        return dao.findEntityById(id);
    }

    public Booking findByRequestId(int requestId){
        return dao.findByRequestId(requestId);
    }
}
