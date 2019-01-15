package model.dao.factory;

import model.dao.factory.implementation.BookingDAO;
import model.dao.factory.implementation.ClientDAO;
import model.dao.factory.implementation.RequestDAO;
import model.dao.factory.implementation.RoomDAO;
import util.constants.TypesDAO;

import java.util.HashMap;
import java.util.Map;

/**
 * Is a factory of DAOs
 * @author Kateryna Shkulova
 */
public class FactoryDao{
    /**
     * map of dao class's instances
     */
    private static Map<TypesDAO,AbstractDAO> daoMap;

    /**
     * static block which sets map of dao class's instances {@link #daoMap}
     */
    static{
        daoMap = new HashMap<>();
        daoMap.put(TypesDAO.BOOKING, new BookingDAO());
        daoMap.put(TypesDAO.ROOM, new RoomDAO());
        daoMap.put(TypesDAO.CLIENT, new ClientDAO());
        daoMap.put(TypesDAO.REQUEST, new RequestDAO());
    }

    /**
     * gets the dao class from dao class's instances {@link #daoMap} by its key
     * @param key is a dao identifier
     * @return dao entity
     */
    public static AbstractDAO getDAO(TypesDAO key){
        return daoMap.get(key);
    }
}
