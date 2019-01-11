package service;

import model.dao.factory.BookingDAO;
import model.dao.factory.FactoryDao;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

public class ServiceBookings extends Service {
    private BookingDAO dao;

    public ServiceBookings() {
        dao = (BookingDAO) FactoryDao.createDAO("booking");
    }

    public List<Integer> getByDate(Date from, Date to){
        return dao.findRoomsIdByDate(from, to);
    }

//    public boolean create(Booking booking){
//        return dao.create(booking);
//    }

    public void findBookingsForCurrentPage(int currentPage, int recordsPerPage, HttpServletRequest servletRequest){
        servletRequest.setAttribute("bookings", getEntitiesForCurrentPage(dao,currentPage,recordsPerPage));
    }

    public void bookingsPagination(HttpServletRequest servletRequest){
        setCurrentPageRecordsPerPage(servletRequest);
        setNumOfPages(getNumOfRows(dao), servletRequest);
        findBookingsForCurrentPage((Integer)(servletRequest.getAttribute("currentPage"))
                ,(Integer)(servletRequest.getAttribute("recordsPerPage")), servletRequest);
    }

    @Override
    public void closeConnections() {
        dao.close();
    }

//    public List<Booking> getBookingFromTo(int from, int to){
//        return getEntitiesFromTo(dao, from,to);
//    }
}
