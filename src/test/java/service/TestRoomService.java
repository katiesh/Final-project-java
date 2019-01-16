package service;

import model.dao.factory.implementation.RoomDAO;
import model.entity.Request;
import model.entity.Room;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;

public class TestRoomService {

    private static RoomDAO roomDAO;
    private static RoomService roomService;

    @BeforeClass
    public static void init(){
        roomDAO = Mockito.mock(RoomDAO.class);
        roomService = new RoomService(roomDAO);
    }

    @Test
    public void testGetNumOfRowsWithoutNotAvailableRooms(){
        int rows = 7;
        List<Integer> roomsIds = Arrays.asList(1,2,3);
        Mockito.when(roomDAO.getNumOfRows()).thenReturn(rows);
        int result = roomService.getNumOfRowsWithoutNotAvailableRooms(roomsIds);
        Mockito.verify(roomDAO).getNumOfRows();
        Assert.assertEquals(rows - roomsIds.size(),result);
    }

    @Test
    public void testGetNumOfRowsByALLRequiredParams(){
        int rows = 7;
        List<Integer> roomsIds = Arrays.asList(1,2,3);
        Request request = new Request();
        request.setId(1);
        Mockito.when(roomDAO.getNumOfRowsByRequestParameters(any(Request.class), anyString()))
                .thenReturn(rows);
        int result = roomService.getNumOfRowsByAllRequiredParams(request, roomsIds);
        Mockito.verify(roomDAO).getNumOfRowsByRequestParameters(any(Request.class), anyString());
        Assert.assertEquals(rows, result);
    }

    @Test
    public void testFindRoomsByNotSuitbleIdsForCurrentPage(){
        Room room1 = new Room();
        Room room2 = new Room();
        room1.setId(1);
        room2.setId(2);
        List<Room> rooms = Arrays.asList(room1,room2);
        List<Integer> roomsIds = Arrays.asList(1,2,3);
        Mockito.when(roomDAO.findByNotSuitableIdsFromPositionWithOffset(anyString(), anyInt(), anyInt())).thenReturn(rooms);
        List<Room> result = roomService.findRoomsByNotSuitbleIdsForCurrentPage(3,3 , roomsIds);
        Mockito.verify(roomDAO).findByNotSuitableIdsFromPositionWithOffset( anyString(), anyInt(),anyInt());
        Assert.assertEquals(rooms, result);
    }

    @Test
    public void testFindRoomsByRequestParametersForCurrentPage(){
        Room room1 = new Room();
        Room room2 = new Room();
        room1.setId(1);
        room2.setId(2);
        Request request = new Request();
        request.setId(1);
        List<Room> rooms = Arrays.asList(room1,room2);
        List<Integer> roomsIds = Arrays.asList(1,2,3);
        Mockito.when(roomDAO.findByRequestParametersFromPositionWithOffset(any(Request.class), anyString(), anyInt(), anyInt())).thenReturn(rooms);
        List<Room> result = roomService.findRoomsByRequestParametersForCurrentPage(3,3 ,request, roomsIds);
        Mockito.verify(roomDAO).findByRequestParametersFromPositionWithOffset(any(Request.class), anyString(), anyInt(),anyInt());
        Assert.assertEquals(rooms, result);
    }
}
