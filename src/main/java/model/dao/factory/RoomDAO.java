package model.dao.factory;

import model.entity.Room;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends AbstractDAO<Room> {
    final static Logger logger = Logger.getLogger(RoomDAO.class);
    public static final String SELECT_ALL_ROOMS = "SELECT* FROM finalproject.rooms";

    public RoomDAO(){
        super();
    }

    private List<Room> parseSet(ResultSet resultSet) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (resultSet.next()){
            Room newRoom = new Room();
            newRoom.setId(resultSet.getInt(1));
            newRoom.setNumOfPlaces(resultSet.getInt(2));
            newRoom.setClassOfRoom(resultSet.getString(3));
            newRoom.setPrice(resultSet.getDouble(4));
            rooms.add(newRoom);
        }
        return rooms;
    }

    @Override
    public List<Room> findAll() {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_ROOMS);
            return parseSet(result);
        } catch (SQLException e){
            logger.error("Error statement rooms");
        }
        return null;
    }

    @Override
    public Room findEntityById(int id) {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_ROOMS+"where id=" + id);
            return parseSet(result).get(0);
        }catch (SQLException e){
            logger.error("Error statement rooms by id");
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM finalproject.rooms WHERE id = " + id);
            return true;
        }catch (SQLException e){
            logger.error("Error statement rooms delete by id");
        }
        return false;
    }

    @Override
    public boolean create(Room entity) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO finalproject.bookings " +
                "(numOfPlaces, classOfRoom, price) VALUES (?,?,?)")){
            statement.setInt(2,entity.getNumOfPlaces());
            statement.setString(3, entity.getClassOfRoom());
            statement.setDouble(4, entity.getPrice());
            statement.execute();
        }catch (SQLException e){
            logger.error("Statement bookings create");
        }
        return false;
    }

    private String setFindQueryByNotSuitableIds(List<Integer> roomsId){
        StringBuilder newSQLStatement = new StringBuilder(10);
        if(roomsId!=null){
            newSQLStatement.append(SELECT_ALL_ROOMS + " WHERE id <> " + roomsId.get(0));
        }
        for (int i = 1; i < roomsId.size(); i++) {
            newSQLStatement.append("AND id <> " + roomsId.get(i));

            }
        return newSQLStatement.toString();
    }
    public List<Room> findByNotSuitableIds(List<Integer> roomsId){
        String query = setFindQueryByNotSuitableIds(roomsId);
        if(query!=null){
            try(Statement statement = connection.createStatement()){
                ResultSet result = statement.executeQuery(query);
               return parseSet(result);
            }catch (SQLException e){
                logger.error("Error rooms by not suitable id");
            }
        }
        return null;
    }

    public List<Room> findByClassAndNotSuitableIds(String classOfRooms, List<Integer> roomsId){
        String query = setFindQueryByNotSuitableIds(roomsId) + "AND classOfRooms = " + classOfRooms;
        if(query!=null){
            try(Statement statement = connection.createStatement()){
                ResultSet result = statement.executeQuery(query);
                return parseSet(result);
            }catch (SQLException e){
                logger.error("Error rooms by not suitable id and classOfRoom");
            }
        }
        return null;
    }

    public List<Room> findByNumOfPlacesAndNotSuitableIds(int numOfPlaces, List<Integer> roomsId){
        String query = setFindQueryByNotSuitableIds(roomsId) + "AND numOfPlaces = " + numOfPlaces;
        if(query!=null){
            try(Statement statement = connection.createStatement()){
                ResultSet result = statement.executeQuery(query);
                return parseSet(result);
            }catch (SQLException e){
                logger.error("Error rooms by not suitable id and number of places");
            }
        }
        return null;
    }

    public List<Room> findByClassAndNumOfPlacesAndNotSuitableIds(String classOfRooms,
                                                                 List<Integer> roomsId,
                                                                 int numOfPlaces){
        String query = setFindQueryByNotSuitableIds(roomsId) + "AND classOfRooms = " + classOfRooms
                + "AND numOfPlaces = " + numOfPlaces;
        if(query!=null){
            try(Statement statement = connection.createStatement()){
                ResultSet result = statement.executeQuery(query);
                return parseSet(result);
            }catch (SQLException e){
                logger.error("Error rooms by not suitable id and classOfRoom and numOfPlaces");
            }
        }
        return null;
    }

    public List<Room> findByClassAndNumOfPlaces(String classOfRoom, int numOfPlaces){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ROOMS + "where numOfPlaces = "
                    + numOfPlaces +
                    "AND classOfRoom = " + classOfRoom);
            return parseSet(resultSet);

        } catch (SQLException e) {
            logger.error("Error statement rooms by class and number of places");
        }
        return null;
    }

    public List<Room> findByClass (String classOfRoom){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ROOMS +
                    " classOfRoom = " + classOfRoom);
            return parseSet(resultSet);
        }catch (SQLException e){
            logger.error("Error statement rooms by class of rooms");
        }
        return null;
    }

    public List<Room> findByNumOfPlaces (int numOfPlaces){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ROOMS +
                    " numOfPlaces = " + numOfPlaces);
            return parseSet(resultSet);
        }catch (SQLException e){
            logger.error("Error statement rooms by number of places");
        }
        return null;
    }

    public int getNumOfRows(){
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT COUNT(id) AS count FROM finalproject.rooms;");
            if(result.next())
                return  result.getInt("count");
        }catch (SQLException e){
            logger.error("Error statement rooms num of rows");
        }
        return 0;
    }

    public List<Room> findFromTo(int from, int to){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROOMS +" LIMIT ?,?")){
            statement.setInt(1,from);
            statement.setInt(2,to);
            return parseSet(statement.executeQuery());
        }catch(SQLException e){
            logger.error("Error prepared statement rooms find from to");
        }
        return null;
    }
//
//    @Override
//    public boolean delete(Room entity) {
//        try(Statement statement = connection.createStatement()){
//            statement.executeQuery("DELETE FROM rooms WHERE id = " + id);
//            return true;
//        }catch (SQLException e){
//
//        }
//        return false;
//    }

//    @Override
//    public Room update(Room entity) {
//        try{
//        }catch (SQLException e){
//
//        }
//        return null;
//    }
}
