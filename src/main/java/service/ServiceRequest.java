package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.RequestDAO;
import model.entity.Request;
import org.apache.log4j.Logger;
import util.Parser;
import util.Validator;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public class ServiceRequest extends Service {
    public static Logger logger = Logger.getLogger(ServiceRequest.class);
    private RequestDAO dao;
    public ServiceRequest() {
        dao = (RequestDAO) FactoryDao.createDAO("request");
    }

    private boolean makeOrder(String clientId, Request request){
        request.setClientId(Integer.valueOf(clientId));
        return dao.create(request);
    }

    public boolean validateRequestForm(HttpServletRequest request)  {
            return (Validator.isCorrectNameSurname(request.getParameter("class"))&&
                    Validator.isCorrectNumOfPlaces(request.getParameter("numOfPlaces"))&&
                    Validator.isCorrectDate(request.getParameter("dateFrom"))!=null &&
                    Validator.isCorrectDate(request.getParameter("dateFrom"), request.getParameter("dateTo"))!=null&&
                    Validator.isCorrectInt(request.getSession().getAttribute("clientId").toString()));
    }

    private void findAllRequestsForCurrentPage(int currentPage, int recordsPerPage, HttpServletRequest servletRequest){
//        return dao.findFromTo((currentPage-1)*recordsPerPage,recordsPerPage);
        servletRequest.setAttribute("requests", getEntitiesForCurrentPage(dao,currentPage, recordsPerPage));
    }

    private void findNewRequestsForCurrentPage(int currentPage, int recordsPerPage, HttpServletRequest servletRequest){
        servletRequest.setAttribute("requests",dao.findNewRequestsFromTo((currentPage-1)*recordsPerPage, recordsPerPage));
    }

    public Request getRequestById(int id){
        return dao.findEntityById(id);
    }

    public void requestsPagination(HttpServletRequest servletRequest){
        setCurrentPageRecordsPerPage(servletRequest);
        setNumOfPages(getNumOfRows(dao), servletRequest);
        findAllRequestsForCurrentPage((Integer)(servletRequest.getAttribute("currentPage"))
                ,(Integer)(servletRequest.getAttribute("recordsPerPage")), servletRequest);
    }

    public void newRequestsPagination(HttpServletRequest servletRequest){
        setCurrentPageRecordsPerPage(servletRequest);
        setNumOfPages(dao.getNumOfRowsForNewRequests(), servletRequest);
        findNewRequestsForCurrentPage((Integer)(servletRequest.getAttribute("currentPage"))
                ,(Integer)(servletRequest.getAttribute("recordsPerPage")), servletRequest);

    }

    public boolean create(HttpServletRequest servletRequest){
        Request request = new Request();
        request.setClassOfRoom(servletRequest.getParameter("class"));
        request.setNumOfPlaces(Integer.valueOf(servletRequest.getParameter("numOfPlaces")));
        try {
            request.setDateFrom(Parser.parseDateFromStringToSql(servletRequest.getParameter("dateFrom")));
            request.setDateTo(Parser.parseDateFromStringToSql(servletRequest.getParameter("dateTo")));
        } catch (ParseException e) {
            logger.error("Error create parseExc");
        }
        return makeOrder(servletRequest.getSession().getAttribute("clientId").toString(), request);
    }

    @Override
    public void closeConnections() {
        dao.close();
    }
}
