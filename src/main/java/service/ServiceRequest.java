package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.RequestDAO;
import model.entity.Request;
import util.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ServiceRequest {
    private RequestDAO dao;
    public ServiceRequest() {
        dao = (RequestDAO) FactoryDao.createDAO("request");
    }

    public void addRequest(Request request){
        dao.create(request);
    }

    public boolean validateRequestForm(HttpServletRequest request)  {
            return (Validator.isCorrectNameSurname(request.getParameter("class"))&&
                    Validator.isCorrectNumOfPlaces(request.getParameter("numOfPlaces"))&&
                    Validator.isCorrectDate(request.getParameter("dateFrom"))!=null &&
                    Validator.isCorrectDate(request.getParameter("dateFrom"), request.getParameter("dateTo"))!=null&&
                    Validator.isCorrectInt(request.getSession().getAttribute("clientId").toString()));
    }

    public List<Request> findRequests(int currentPage, int recordsPerPage){
        return dao.findFromTo((currentPage-1)*recordsPerPage,recordsPerPage);
    }

    public int getNumOfRows(){
        return dao.getNumOfRows();
    }

    public Request getRequestById(int id){
        return dao.findEntityById(id);
    }

    public void changeStatus(Request request){
        dao.updateStatus(request);
    }

    public List<Request> findRequestsFromToById(int currentPage, int recordsPerPage, int clientId){
        return dao.findRequestsFromToById((currentPage-1)*recordsPerPage,recordsPerPage,clientId);
    }

    public int getNumOfRowsByClientId(int clientId){
        return dao.getNumOfRowsByClientId(clientId);
    }

}
