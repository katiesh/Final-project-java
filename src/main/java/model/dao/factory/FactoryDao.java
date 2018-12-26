package model.dao.factory;

import exception.UnExistTypeDAO;
import org.apache.log4j.Logger;

public class FactoryDao{
    final static Logger logger = Logger.getLogger(FactoryDao.class);
    public static AbstractDAO createDAO(String type){
        TypesDAO typeDAO = TypesDAO.valueOf(type.toUpperCase());
        switch (typeDAO){
            case BOOKING:{
                return new BookingDAO();
            }
            case ROOM: {
                return new RoomDAO();
            }
            case CLIENT:{
                return new ClientDAO();
            }
            case REQUEST:{
                return new RequestDAO();
            }
            default:{
                try {
                    throw new UnExistTypeDAO("no such type of DAO");
                } catch (UnExistTypeDAO unExistTypeDAO) {
                    logger.error("Error" + unExistTypeDAO.getMessage() + type);
                }
            }
        }
        return null;
    }
}
