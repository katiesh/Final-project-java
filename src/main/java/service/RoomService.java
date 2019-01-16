package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.implementation.RoomDAO;
import model.entity.Request;
import model.entity.Room;
import util.Parser;
import util.constants.TypesDAO;

import java.util.List;

/**
 * provides services with bookings and rooms
 * @author Kateryna Shkulova
 */
public class RoomService {
    /**
     * field room dao
     */
    private RoomDAO dao;

    /**
     * constructor without parameters
     */
    public RoomService(){
        dao = (RoomDAO)FactoryDao.getDAO(TypesDAO.ROOM);
    }

    public RoomService(RoomDAO dao) {
        this.dao = dao;
    }

    /**
     * gets the number of rows without rooms which are not available
     * @param roomsIds is the list of ids of rooms which are not available
     * @return number of rows
     */
    public int getNumOfRowsWithoutNotAvailableRooms(List <Integer> roomsIds){
        return dao.getNumOfRows() - roomsIds.size();
    }

    /**
     * gets number of rows which match request and are available
     * @param requestItem is the request for which rooms are finding
     * @param roomsIds is the list of ids of rooms which are not available
     * @return number of found rows
     */
    public int getNumOfRowsByAllRequiredParams(Request requestItem, List <Integer> roomsIds){
        return dao.getNumOfRowsByRequestParameters(requestItem,
                Parser.convertListToString(roomsIds));
    }

    /**
     * gets available rooms for the page
     * @param currentPage is the current page
     * @param recordsPerPage records  per page
     * @param roomsIds is the list of ids of rooms which are not available
     */
    public List<Room> findRoomsByNotSuitbleIdsForCurrentPage(int currentPage, int recordsPerPage,
                                                              List <Integer> roomsIds){
        return dao.findByNotSuitableIdsFromPositionWithOffset(Parser.convertListToString(roomsIds),
                (currentPage-1)*recordsPerPage,recordsPerPage);
    }

    /**
     * gets rooms that are matching request parameters and are available
     * @param currentPage is the current page
     * @param recordsPerPage is the number of records per page
     * @param requestItem is the request for which rooms are found
     * @param roomsIds is the list of ids of rooms which are not available
     */
    public List<Room> findRoomsByRequestParametersForCurrentPage(int currentPage, int recordsPerPage,
                                                            Request requestItem, List<Integer> roomsIds){
        return dao.findByRequestParametersFromPositionWithOffset(requestItem,Parser.convertListToString
                        (roomsIds), (currentPage-1)*recordsPerPage,recordsPerPage);
    }
}
