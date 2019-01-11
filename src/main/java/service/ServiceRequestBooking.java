package service;

import model.dao.factory.BookingDAO;
import model.dao.factory.FactoryDao;
import model.dao.factory.RequestDAO;
import model.entity.Booking;
import model.entity.Request;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServiceRequestBooking extends Service{
    private RequestDAO requestDAO;
    private BookingDAO bookingDAO;

    public ServiceRequestBooking() {
        requestDAO = (RequestDAO) FactoryDao.createDAO("request");
        bookingDAO = (BookingDAO) FactoryDao.createDAO("booking");
    }

    public void requestsBookingsPagination(HttpServletRequest servletRequest, int clientId){
        setCurrentPageRecordsPerPage(servletRequest);
        setNumOfPages(getNumOfRowsByClientId(clientId), servletRequest);
        findRequestsBookingsForCurrentPage((Integer)(servletRequest.getAttribute("currentPage"))
                ,(Integer)(servletRequest.getAttribute("recordsPerPage")), servletRequest, clientId );
    }

    private void findRequestsBookingsForCurrentPage(Integer currentPage, Integer recordsPerPage, HttpServletRequest servletRequest, int clientId) {
        List<Request> requestList = findRequestsFromToById(currentPage,
                recordsPerPage, clientId);
        List<Booking> bookings = new ArrayList<>();
        for (Iterator<Request> iterator = requestList.iterator(); iterator.hasNext();) {
            Request requestItem = iterator.next();
            if (requestItem.getStatus().equals("processed")) {
                bookings.add(findByRequestId(requestItem.getId()));
                iterator.remove();
            }
        }
        servletRequest.setAttribute("requests", requestList);
        servletRequest.setAttribute("bookings", bookings);

    }

    public Booking findByRequestId(int requestId){
        return bookingDAO.findByRequestId(requestId);
    }

    private List<Request> findRequestsFromToById(int currentPage, int recordsPerPage, int clientId){
        return requestDAO.findRequestsFromToById((currentPage-1)*recordsPerPage,recordsPerPage,clientId);
    }

    private int getNumOfRowsByClientId(int clientId){
        return requestDAO.getNumOfRowsByClientId(clientId);
    }

    public Request getRequestById(int id){
        return requestDAO.findEntityById(id);
    }

    public boolean create(int roomId, Request requestItem, double roomPrice){
        Booking booking = new Booking();
        booking.setRoomId(roomId);
        booking.setRequestId(requestItem.getId());
        booking.setClientId(requestItem.getClientId());
        booking.setDateFrom(requestItem.getDateFrom());
        booking.setDateTo(requestItem.getDateTo());
        booking.setPrice(calculatePrice(requestItem.getDateFrom(),requestItem.getDateTo(), roomPrice));
        return bookingDAO.create(booking);
    }

    private double calculatePrice(Date dateFrom, Date dateTo, double pricePreDay){
        int days = (int)(dateTo.getTime()-dateFrom.getTime())/(1000*60*60*24);
        return days*pricePreDay;
    }

    private void changeStatus(Request request){
        requestDAO.updateStatus(request);
    }

    public void createBookingAndChangeRequestStatus(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        int requestId = Integer.valueOf(servletRequest.getParameter("requestId"));
        int roomId = Integer.valueOf(servletRequest.getParameter("roomId"));
        double roomPrice = Double.valueOf(servletRequest.getParameter("roomPrice"));
        Request requestItem = getRequestById(requestId);
        if(create(roomId,requestItem, roomPrice)) {
            changeStatus(requestItem);
            forwardToPage(ForwardPagesPaths.BOOKING_IS_CREATED.toString(), servletRequest, servletResponse);
        }else{
            forwardToPage(ForwardPagesPaths.DATA_NOT_SENT.toString(), servletRequest, servletResponse);
        }
    }

    @Override
    public void closeConnections() {
        requestDAO.close();
        bookingDAO.close();
    }
}
