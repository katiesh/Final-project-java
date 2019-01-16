package service;

import model.dao.factory.implementation.RequestDAO;
import model.entity.Request;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class TestRequestService {

    private static RequestDAO requestDAO;
    private static RequestService requestService;

    @BeforeClass
    public static void init(){
        requestDAO = Mockito.mock(RequestDAO.class);
        requestService = new RequestService(requestDAO);
    }

    @Test
    public void testValidateRequestForm(){
        Assert.assertFalse(requestService.validateRequestForm("Class", "2", "11.12.2018", "sfg", new Object() ));
    }

    @Test
    public void testFindAllRequestsForCurrentPage(){
        Request request1 = new Request();
        Request request2 = new Request();
        request1.setId(1);
        request2.setId(2);
        List<Request> requests = Arrays.asList(request1,request2);
        Mockito.when(requestDAO.findWithOffsetFromPosition(anyInt(), anyInt())).thenReturn(requests);
        List<Request> result = requestService.findAllRequestsForCurrentPage(3,3);
        Mockito.verify(requestDAO).findWithOffsetFromPosition(6,3);
        Assert.assertEquals(requests, result);
    }

    @Test
    public void testFindNewRequestForCurrentPage(){
        Request request1 = new Request();
        Request request2 = new Request();
        request1.setId(1);
        request2.setId(2);
        List<Request> requests = Arrays.asList(request1,request2);
        Mockito.when(requestDAO.findNewRequestsWithOffsetFromPosition(anyInt(), anyInt())).thenReturn(requests);
        List<Request> result = requestService.findNewRequestsForCurrentPage(3,3);
        Mockito.verify(requestDAO).findNewRequestsWithOffsetFromPosition(6,3);
        Assert.assertEquals(requests, result);
    }

    @Test
    public void testGetRequestById(){
        Request request = new Request();
        int id = 1;
        request.setId(id);
        Mockito.when(requestDAO.findEntityById(anyInt())).thenReturn(request);
        Request result = requestService.getRequestById(id);
        Mockito.verify(requestDAO).findEntityById(id);
        Assert.assertEquals(request,result);
    }

    @Test
    public void testGetNumOfRows(){
        int rows = 5;
        Mockito.when(requestDAO.getNumOfRows()).thenReturn(rows);
        int result = requestService.getNumOfRows();
        Mockito.verify(requestDAO).getNumOfRows();
        Assert.assertEquals(rows,result);
    }

    @Test
    public void testGetNumOfRowsForNewRequests(){
        int rows = 3;
        Mockito.when(requestDAO.getNumOfRowsForNewRequests()).thenReturn(rows);
        int result = requestService.getNumOfRowsForNewRequests();
        Mockito.verify(requestDAO).getNumOfRowsForNewRequests();
        Assert.assertEquals(rows,result);
    }

    @Test
    public void testCreate(){
        Request request = new Request();
        String classOfRoom = "class";
        String numOfPlaces = "4";
        String dateFrom = "11.02.2019";
        String dateTo = "12.02.2019";
        Integer clientId = 3;
        request.setClassOfRoom(classOfRoom);
        Mockito.when(requestDAO.create(any(Request.class))).thenReturn(true);
        boolean result = requestService.create(classOfRoom,numOfPlaces,dateFrom,dateTo,clientId);
        Mockito.verify(requestDAO).create(any(Request.class));
        Assert.assertTrue(result);
    }

    @Test
    public void testFindRequestsForPageByClientId(){
        int clientId = 1;
        Request request1 = new Request();
        Request request2 = new Request();
        request1.setId(1);
        request2.setId(2);
        List<Request> requests = Arrays.asList(request1,request2);
        Mockito.when(requestDAO.findRequestsWithOffsetFromPositionByClientId(anyInt(), anyInt(), anyInt())).thenReturn(requests);
        List<Request> result = requestService.findRequestsForPageByClientId(3,3 , clientId);
        Mockito.verify(requestDAO).findRequestsWithOffsetFromPositionByClientId(6,3, clientId);
        Assert.assertEquals(requests, result);
    }

    @Test
    public void testGetNumOfRowsByClientId(){
        int rows = 3;
        int clientId = 1;
        Mockito.when(requestDAO.getNumOfRowsByClientId(anyInt())).thenReturn(rows);
        int result = requestService.getNumOfRowsByClientId(clientId);
        Mockito.verify(requestDAO).getNumOfRowsByClientId(clientId);
        Assert.assertEquals(rows,result);
    }
}
