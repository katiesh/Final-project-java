package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.RoomDAO;
import model.entity.Room;

import java.util.List;

public class ServiceRoom {
    private RoomDAO dao;

    public ServiceRoom() {
        dao = (RoomDAO) FactoryDao.createDAO("room");
    }

    public List<Room> getAllRooms(){
        return dao.findAll();
    }

    public List<Room> getSuitableRooms(String classOFRoom, List<Integer> ids, int numOfPlaces){
        return dao.findByClassAndNumOfPlacesAndNotSuitableIds(classOFRoom, ids, numOfPlaces);
    }

    public int getNumOfRows(){
        return dao.getNumOfRows();
    }

    public List<Room> findRooms(int currentPage, int recordsPerPage){
        return dao.findFromTo((currentPage-1)*recordsPerPage,recordsPerPage);
    }

}
