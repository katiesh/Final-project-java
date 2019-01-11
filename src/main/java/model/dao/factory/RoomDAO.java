package model.dao.factory;

import model.entity.Request;
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
            ResultSet result = statement.executeQuery(SELECT_ALL_ROOMS+" where id=" + id);
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

    private String setFindQueryByNotSuitableIds(String roomsId){
        String newSQLStatement;
        if(roomsId!=null){
            newSQLStatement = SELECT_ALL_ROOMS + " where id NOT IN " + roomsId;
        }
        else {
            newSQLStatement = SELECT_ALL_ROOMS;
        }
        return newSQLStatement;
    }

    public List<Room> findByNotSuitableIdsFromTo(String roomsId, int from, int to){
        try(PreparedStatement statement = connection.prepareStatement(
                setFindQueryByNotSuitableIds(roomsId) +" LIMIT ?,?")){
            statement.setInt(1,from);
            statement.setInt(2,to);
            return parseSet(statement.executeQuery());
        }catch (SQLException e){
                logger.error("Error rooms by not suitable id");
            }
        return null;
    }

    public List<Room> findByAllRequestParameterFromTo(Request request, String roomsId, int from, int to){
        String notInRoomsIds = new String();
        if(roomsId!=null) {
            notInRoomsIds = " and id not in " + roomsId;
        }
        try(PreparedStatement statement = connection.prepareStatement(
                SELECT_ALL_ROOMS +" where numOfPlaces = ? " + notInRoomsIds +
                        " and classOfRoom = ? LIMIT ?,?")){
            statement.setInt(1,request.getNumOfPlaces());
            statement.setString(2,request.getClassOfRoom());
            statement.setInt(3,from);
            statement.setInt(4,to);
            return parseSet(statement.executeQuery());
        }catch (SQLException e){
            logger.error("Error rooms by not suitable id, numOfPlaces and classOfRoom");
        }
        return null;
    }

    public int getNumOfRowsByAllRequestParameter(Request request, String roomsId){
        String notInRoomsIds = new String();
        if(roomsId!=null){
            notInRoomsIds = " and id not in " + roomsId;
        }
        try(PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id)" +
                "  AS count FROM finalproject.rooms WHERE numOfPlaces = ?" +
                notInRoomsIds + " and classOfRoom = ?;")){
            statement.setInt(1, request.getNumOfPlaces());
            statement.setString(2, request.getClassOfRoom());
            ResultSet result = statement.executeQuery();
            if(result.next())
                return  result.getInt("count");
        }catch (SQLException e){
            logger.error("Error statement rooms num of rows");
        }
        return 0;
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
