package model.dao.factory.implementation;

import model.dao.factory.FactoryDao;
import model.entity.Request;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import util.constants.TypesDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestRequestDAO {

    private static Request request;
    private static RequestDAO requestDAO;

    @BeforeClass
    public static void init(){
        requestDAO = (RequestDAO) FactoryDao.getDAO(TypesDAO.REQUEST);
        request = new Request();
        request.setClientId(2);
        request.setDateFrom(new Date(1548806400000L));
        request.setDateTo(new Date(1548892800000L));
        request.setClassOfRoom("standart");
        request.setNumOfPlaces(3);
        request.setStatus("waiting to be processed");
    }

    @Test
    public void createDelete(){
        boolean created = false;
        requestDAO.create(request);
        for (Request curRequest:requestDAO.getAll()) {
            request.setId(curRequest.getId());
            if(request.equals(curRequest)){
                created = true;
                requestDAO.delete(curRequest);
                break;
            }
        }
        Assert.assertTrue(created && requestDAO.findEntityById(request.getId())==null );
    }

    @Test
    public void testGetNumOfRows(){
        List<Request> requests = requestDAO.getAll();
        Assert.assertEquals(requests.size(), requestDAO.getNumOfRows());
    }

    @Test
    public void testFindWithOffsetFromPosition(){
        List <Request> requests = requestDAO.getAll();
        List<Request> expected = new ArrayList<>();
        for (int i = requests.size()/2; i >= requests.size()/4 ; i--) {
            expected.add(requests.get(i));
        }
        Assert.assertEquals(expected,
                requestDAO.findWithOffsetFromPosition(requests.size()-requests.size()/2-1, expected.size()));
    }

    @Test
    public void testGetNumOfRowsByClientId(){
        List<Request> requests = requestDAO.getAll();
        int clientID = 2;
        int expected = 0;
        for (Request curReq:requests) {
            if(curReq.getClientId() == clientID){
                expected++;
            }
        }
        Assert.assertEquals(expected, requestDAO.getNumOfRowsByClientId(clientID));
    }

    @Test
    public void testGetNumOfRowsForNewRequests(){
        List<Request> requests = requestDAO.getAll();
        int expected = 0;
        for (Request curReq:requests) {
            if(curReq.getStatus().equalsIgnoreCase("waiting to be processed")){
                expected++;
            }
        }
        Assert.assertEquals(expected, requestDAO.getNumOfRowsForNewRequests());
    }

    @Test
    public void testFindWithOffsetFromPositionByClientId(){
        List<Request> requestsByClientID = new ArrayList<>();
        int clientID = 2;
        for (Request curReq:requestDAO.getAll()) {
            if(curReq.getClientId() == clientID){
                requestsByClientID.add(curReq);
            }
        }
        List<Request> expected = null;
        int size = 0;
        if(!requestsByClientID.isEmpty()){
            expected = new ArrayList<>();
            for (int i = requestsByClientID.size()/2; i >= requestsByClientID.size()/4 ; i--) {
                expected.add(requestsByClientID.get(i));
            }
            size = expected.size();
        }
        Assert.assertEquals(expected,
                requestDAO.findRequestsWithOffsetFromPositionByClientId(
                        requestsByClientID.size()-requestsByClientID.size()/2-1,
                        size, clientID));
    }

    @Test
    public void testFindNewRequestsWithOffsetFromPosition(){
        List<Request> newRequests = new ArrayList<>();
        for (Request curReq:requestDAO.getAll()) {
            if(curReq.getStatus().equalsIgnoreCase("waiting to be processed")){
                newRequests.add(curReq);
            }
        }
        List<Request> expected = null;
        int size = 0;
        if(!newRequests.isEmpty()){
            expected = new ArrayList<>();
            for (int i = newRequests.size()/2; i >= newRequests.size()/4 ; i--) {
                expected.add(newRequests.get(i));
            }
            size = expected.size();
        }
        Assert.assertEquals(expected,
                requestDAO.findNewRequestsWithOffsetFromPosition(
                        newRequests.size()-newRequests.size()/2-1,
                        size));
    }

    @Test
    public void testUpdateStatus() throws SQLException {
        requestDAO.create(request);
        boolean changed = false;
        for (Request curRequest:requestDAO.getAll()) {
            request.setId(curRequest.getId());
            if(request.equals(curRequest)){
                try {
                    requestDAO.updateStatus(curRequest);
                    requestDAO.commit();
                    changed = (requestDAO.findEntityById(curRequest.getId()).getStatus()).equalsIgnoreCase("processed");
                    break;
                } catch (SQLException e) {
                    requestDAO.rollback();
                    e.printStackTrace();
                }finally {
                    requestDAO.setAutoCommitTrue();
                    requestDAO.delete(curRequest);
                }

            }
        }
        Assert.assertTrue(changed);
    }
}
