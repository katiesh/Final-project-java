package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.RoomDAO;
import model.entity.Room;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ServiceRoom extends Service {
    private RoomDAO dao;

    public ServiceRoom() {
        dao = (RoomDAO) FactoryDao.createDAO("room");
    }

    public List<Room> getAllRooms(){
        return dao.findAll();
    }

    public int getNumOfRows(){
        return dao.getNumOfRows();
    }

    public void findRoomsForCurrentPage(int currentPage, int recordsPerPage, HttpServletRequest servletRequest){
        servletRequest.setAttribute("rooms", getEntitiesForCurrentPage(dao,currentPage,recordsPerPage));
        //return dao.findFromTo((currentPage-1)*recordsPerPage,recordsPerPage);
    }

    public void roomsPagination(HttpServletRequest servletRequest){
        setCurrentPageRecordsPerPage(servletRequest);
        setNumOfPages(getNumOfRows(dao), servletRequest);
        findRoomsForCurrentPage((Integer)(servletRequest.getAttribute("currentPage"))
                ,(Integer)(servletRequest.getAttribute("recordsPerPage")), servletRequest);
    }

    public List<Room> getByIds(List<Integer> ids){
        List<Room> rooms = new ArrayList<>();
        for (Integer id: ids) {
            rooms.add(dao.findEntityById(id));
        }
        return rooms;
    }

    @Override
    public void closeConnections() {
        dao.close();
    }
}
