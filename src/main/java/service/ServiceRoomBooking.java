package service;

import model.dao.factory.BookingDAO;
import model.dao.factory.FactoryDao;
import model.dao.factory.RoomDAO;
import model.entity.Request;
import util.Parser;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

public class ServiceRoomBooking extends Service {
    private BookingDAO bookingDAO;
    private RoomDAO roomDAO;

    public ServiceRoomBooking(){
        bookingDAO = (BookingDAO)FactoryDao.createDAO("booking");
        roomDAO = (RoomDAO)FactoryDao.createDAO("room");
    }

    public void roomsByBookingsDatePagination(HttpServletRequest servletRequest, Date dateFrom, Date dateTo){
        setCurrentPageRecordsPerPage(servletRequest);
        setNumOfPages(getNumOfRows(dateFrom, dateTo), servletRequest);
        findRoomsBookingsForCurrentPage((Integer)(servletRequest.getAttribute("currentPage"))
                ,(Integer)(servletRequest.getAttribute("recordsPerPage")), dateFrom,dateTo,servletRequest );
    }

    public void roomsByAllRequiredParamsPagination(Request requestItem, HttpServletRequest servletRequest){
        setCurrentPageRecordsPerPage(servletRequest);
        setNumOfPages(getNumOfRowsByAllRequiredParams(requestItem), servletRequest);
        findRoomsByAllRequiredParametersForCurrentPage((Integer)(servletRequest.getAttribute("currentPage"))
                ,(Integer)(servletRequest.getAttribute("recordsPerPage")), requestItem, servletRequest);
    }

    private int getNumOfRows(Date dateFrom, Date dateTo){
        return getNumOfRows(roomDAO) - getRoomsIdsByDate(dateFrom, dateTo).size();
    }

    private int getNumOfRowsByAllRequiredParams(Request requestItem){
        return roomDAO.getNumOfRowsByAllRequestParameter(requestItem,
                Parser.convertListToString
                        (getRoomsIdsByDate(requestItem.getDateFrom(),requestItem.getDateTo())));
    }


    private List<Integer> getRoomsIdsByDate(Date from, Date to){
        return bookingDAO.findRoomsIdByDate(from, to);
    }

    private void findRoomsBookingsForCurrentPage(int currentPage, int recordsPerPage,
                                                 Date dateFrom, Date dateTo, HttpServletRequest servletRequest){
        servletRequest.setAttribute("rooms",
                roomDAO.findByNotSuitableIdsFromTo(Parser.convertListToString(getRoomsIdsByDate(dateFrom, dateTo)),
                (currentPage-1)*recordsPerPage,recordsPerPage));
    }

    private void findRoomsByAllRequiredParametersForCurrentPage(int currentPage, int recordsPerPage,
                                                                Request requestItem, HttpServletRequest servletRequest){
        servletRequest.setAttribute("rooms",
                roomDAO.findByAllRequestParameterFromTo(requestItem,Parser.convertListToString
                        (getRoomsIdsByDate(requestItem.getDateFrom(),requestItem.getDateTo())), (currentPage-1)*recordsPerPage,recordsPerPage));
    }

    @Override
    public void closeConnections() {
        roomDAO.close();
        bookingDAO.close();
    }
}
