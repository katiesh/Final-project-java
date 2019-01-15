package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.implementation.RequestDAO;
import model.entity.Request;
import util.Parser;
import util.Validator;
import util.constants.TypesDAO;

import java.util.List;

/**
 * provides services with requests
 */
public class ServiceRequest {
    /**
     * field dao
     */
    private RequestDAO dao;

    /**
     * constructor without parameters
     */
    public ServiceRequest() {
        dao = (RequestDAO) FactoryDao.getDAO(TypesDAO.REQUEST);
    }

    /**
     * validate request data
     * @param classOfRoom is input class of the room
     * @param numOfPlaces is input number of places
     * @param dateFrom is input date from
     * @param dateTo is input date to
     * @param clientId is client id
     * @return true if the validation is passed
     */
    public boolean validateRequestForm(String classOfRoom, String numOfPlaces, String dateFrom,
                                       String dateTo, Object clientId) {
        return (Validator.isCorrectClassOfRoom(classOfRoom) &&
                Validator.isCorrectNumOfPlaces(numOfPlaces) &&
                Validator.isCorrectDate(dateFrom) &&
                Validator.isCorrectDate(dateFrom, dateTo) &&
                Validator.isCorrectInt(clientId));
    }

    /**
     * gets requests for current page
     * @param currentPage is the current page
     * @param recordsPerPage is the number of records per page
     */
    public List<Request> findAllRequestsForCurrentPage(int currentPage, int recordsPerPage) {
        return dao.findWithOffsetFromPosition((currentPage - 1) * recordsPerPage, recordsPerPage);
    }
    /**
     * gets new requests for current page
     * @param currentPage is the current page
     * @param recordsPerPage is the number of records per page
     */
    public List<Request> findNewRequestsForCurrentPage(int currentPage, int recordsPerPage) {
        return dao.findNewRequestsWithOffsetFromPosition(
                (currentPage - 1) * recordsPerPage, recordsPerPage);
    }

    /**
     * gets request by its id
     * @param id is the request id
     * @return found request
     */
    public Request getRequestById(int id) {
        return dao.findEntityById(id);
    }

    /**
     * @return number of all rows
     */
    public int getNumOfRows(){
        return dao.getNumOfRows();
    }

    /**
     * @return number of rows with new requests
     */
    public int getNumOfRowsForNewRequests(){
        return dao.getNumOfRowsForNewRequests();
    }

    /**
     * adds the request to database
     * @param classOfRoom is input class of the room
     * @param numOfPlaces is input number of places
     * @param dateFrom is input date from
     * @param dateTo is input date to
     * @param clientId is client id
     * @return true if the request is added to database
     */
    public boolean create(String classOfRoom, String numOfPlaces, String dateFrom, String dateTo, Object clientId ) {
        Request request = new Request();
        request.setClassOfRoom(classOfRoom);
        request.setNumOfPlaces(Integer.valueOf(numOfPlaces));
        request.setDateFrom(Parser.parseDateFromStringToSql(dateFrom));
        request.setDateTo(Parser.parseDateFromStringToSql(dateTo));
        request.setClientId((Integer)clientId);
        return dao.create(request);
    }

    /**
     * gets requests by client id for the page
     * <p>
     *     {@link RequestDAO#findRequestsWithOffsetFromPositionById(int, int, int)}
     *     define the start row as (currentPage-1)*recordsPerPage
     *     define the number of request to get as number of records per page
     * </p>
     * @param currentPage is the current page
     * @param recordsPerPage is the number of records per page
     * @param clientId is the client id
     * @return
     */
    public List<Request> findRequestsForPageById(int currentPage, int recordsPerPage, int clientId){
        return dao.findRequestsWithOffsetFromPositionById((currentPage-1)*recordsPerPage,
                recordsPerPage,clientId);

    }

    /**
     * @param clientId is the client id
     * @return number of rows by client id {@link RequestDAO#getNumOfRowsByClientId(int)}
     */
    public int getNumOfRowsByClientId(int clientId){
        return dao.getNumOfRowsByClientId(clientId);
    }

}
