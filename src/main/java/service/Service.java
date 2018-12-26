package service;

import model.dao.factory.AbstractDAO;
import model.dao.factory.FactoryDao;
import model.entity.Request;

import java.util.List;

public class Service {
    private ServiceClient serviceClient = new ServiceClient();
    private ServiceRequest serviceRequest = new ServiceRequest();
    private FactoryDao dao;

    public void makeOrder(String clientId, Request request){
        request.setClientId(Integer.valueOf(clientId));
        serviceRequest.addRequest(request);
    }

    public int getNumOfRows(AbstractDAO dao){
        return dao.getNumOfRows();
    }

    public List getEntitiesFromTo(AbstractDAO dao, int from, int to){
        return dao.findFromTo(from,to);
    }
//    public int getNumOfRows(){
//
//    }
}
