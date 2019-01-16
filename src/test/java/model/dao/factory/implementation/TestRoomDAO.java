package model.dao.factory.implementation;

import model.dao.factory.FactoryDao;
import model.entity.Request;
import model.entity.Room;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import util.Parser;
import util.constants.TypesDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestRoomDAO {

    private static Room room;
    private static RoomDAO roomDAO;

    @BeforeClass
    public static void init(){
        roomDAO = (RoomDAO) FactoryDao.getDAO(TypesDAO.ROOM);
        room = new Room();
        room.setClassOfRoom("standart+");
        room.setNumOfPlaces(2);
        room.setPrice(120);
    }

    @Test
    public void testCreateDelete(){
        boolean created = false;
        roomDAO.create(room);
        for (Room curRoom:roomDAO.getAll()) {
            room.setId(curRoom.getId());
            if(room.equals(curRoom)){
                created = true;
                roomDAO.delete(curRoom);
                break;
            }
        }
        Assert.assertTrue(created && roomDAO.findEntityById(room.getId())==null );
    }

    @Test
    public void testFindByNotSuitableIdsFromPositionWithOffset(){
        List<Integer> notAvailableRooms = new ArrayList<>();
        notAvailableRooms.add(2);
        notAvailableRooms.add(13);
        List<Room> availableRooms = getAvailableRooms(notAvailableRooms);
        List<Room> expected = getExpectedRoomsFromTo(availableRooms.size()/4,
                availableRooms.size()/2, availableRooms);
        int size;
        if(expected.isEmpty()){
            size = 0;
        }else{
            size = expected.size();
        }
        Assert.assertEquals(expected,
                roomDAO.findByNotSuitableIdsFromPositionWithOffset(
                        Parser.convertListToString(notAvailableRooms),
                        availableRooms.size()/4, size));

    }

    @Test
    public void testFindByRequestParametersFromPositionWithOffset(){
        List<Integer> notAvailableRooms = new ArrayList<>();
        notAvailableRooms.add(2);
        notAvailableRooms.add(13);
        List<Room> availableRooms = getAvailableRooms(notAvailableRooms);
        Request request = new Request();
        request.setNumOfPlaces(2);
        request.setClassOfRoom("econom");
        availableRooms = getRoomsByRequest(request, availableRooms);
        List<Room> expected = getExpectedRoomsFromTo(availableRooms.size()/4,
                availableRooms.size()/2, availableRooms);
        int size;
        if(expected.isEmpty()){
            size = 0;
        }else{
            size = expected.size();
        }
        Assert.assertEquals(expected,
                roomDAO.findByRequestParametersFromPositionWithOffset(request,
                        Parser.convertListToString(notAvailableRooms),
                        availableRooms.size()/4, size));

    }

    @Test
    public void testFindFromPositionWithOffset(){
        List<Room> allRooms = roomDAO.getAll();
        List<Room> expected = getExpectedRoomsFromTo(allRooms.size()/4,
                allRooms.size()/2, allRooms);
        int size;
        if(expected.isEmpty()){
            size = 0;
        }else{
            size = expected.size();
        }
        Assert.assertEquals(expected,
                roomDAO.findWithOffsetFromPosition(allRooms.size()/4, size));
    }

    @Test
    public void testGetNumOfRows(){
        Assert.assertEquals(roomDAO.getAll().size(), roomDAO.getNumOfRows());
    }

    @Test
    public void testGetNumOfRowsByRequestParameters(){
        List<Integer> notAvailableRooms = new ArrayList<>();
        notAvailableRooms.add(2);
        notAvailableRooms.add(13);
        Request request = new Request();
        request.setNumOfPlaces(2);
        request.setClassOfRoom("econom");
        List<Room> availableRooms = getAvailableRooms(notAvailableRooms);
        availableRooms = getRoomsByRequest(request, availableRooms);
        int size;
        if(availableRooms.isEmpty()){
            size = 0;
        }else{
            size = availableRooms.size();
        }
        Assert.assertEquals(size, roomDAO.getNumOfRowsByRequestParameters(request,
                Parser.convertListToString(notAvailableRooms)));
    }

    private List<Room> getAvailableRooms(List<Integer> notAvailableRooms){
        List<Room> availableRooms = roomDAO.getAll();
        Iterator<Room> roomIterator = availableRooms.listIterator();
        while(roomIterator.hasNext()) {
            Room curRoom = roomIterator.next();
            if(notAvailableRooms.contains(curRoom.getId())){
                roomIterator.remove();
            }
        }
        return availableRooms;
    }

    private List<Room> getRoomsByRequest(Request request, List<Room> availableRooms){
        Iterator<Room> roomIterator = availableRooms.listIterator();
        while(roomIterator.hasNext()) {
            Room curRoom = roomIterator.next();
            if(!curRoom.getClassOfRoom().equalsIgnoreCase(request.getClassOfRoom()) ||
                    curRoom.getNumOfPlaces()!=request.getNumOfPlaces()){
                roomIterator.remove();
            }
        }
        return availableRooms;
    }

    private List<Room> getExpectedRoomsFromTo(int from, int to,
                                                              List<Room> allRooms){
        List<Room> expected = null;
        if(!allRooms.isEmpty()){
            expected = new ArrayList<>();
            for (int i = from; i < to ; i++) {
                expected.add(allRooms.get(i));
            }
        }
        return expected;
    }
}
