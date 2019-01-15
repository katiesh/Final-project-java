package model.dao.factory.implementation;

import model.dao.factory.AbstractDAO;
import model.entity.Request;
import model.entity.Room;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Provides operations with table requests from database
 *  @author Kateryna Shkulova
 */
public class RoomDAO extends AbstractDAO<Room> {
    /**
     * field logger
     */
    private static final Logger logger = Logger.getLogger(RoomDAO.class);
    /**
     * field select all rooms is a SQL request which select all from table
     */
    public static final String SELECT_ALL_ROOMS = "SELECT* FROM finalproject.rooms";

    /**
     * constructor without parameters
     */
    public RoomDAO(){
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Room> parseSet(ResultSet resultSet) throws SQLException {
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Room entity) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO finalproject.bookings " +
                "(numOfPlaces, classOfRoom, price) VALUES (?,?,?)")){
            statement.setInt(2,entity.getNumOfPlaces());
            statement.setString(3, entity.getClassOfRoom());
            statement.setDouble(4, entity.getPrice());
            statement.execute();
        }catch (SQLException e){
            logger.error("Statement bookings createBooking");
        }
        return false;
    }

    /**
     * sets sql query to get entities except not matched ids
     * @param roomsId is a string with ids which are not matched
     * @return sql querry when there are ids which not match
     * @return null when there are m=not such ids
     */
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

    /**
     * gets definite number of rows from table from definite position
     * except rows where id mathed one of the ids from not matched ids
     * @param roomsId is a string with ids which are not matched
     * @param position is the start position(row) in the table
     * @param offset is the number of rows which will be got
     * @return list of found rooms {@link Room}
     */
    public List<Room> findByNotSuitableIdsFromPositionWithOffset(String roomsId, int position, int offset){
        try(PreparedStatement statement = connection.prepareStatement(
                setFindQueryByNotSuitableIds(roomsId) +" LIMIT ?,?")){
            statement.setInt(1, position);
            statement.setInt(2, offset);
            return parseSet(statement.executeQuery());
        }catch (SQLException e){
                logger.error("Error rooms by not suitable id");
            }
        return null;
    }

    /**
     * definite number of rows from table from definite position
     * where number of places and class of room matches request parameters
     * and except rows where id matched one of the ids from not matched ids
     * @param request is the request which parameters the result have to be matched
     * @param roomsId is a string with ids which are not matched
     * @param position is the start position(row) in the table
     * @param offset is the number of rows which will be got
     * @return list of found rooms {@link Room}
     */
    public List<Room> findByRequestParametersFromPositionWithOffset(Request request, String roomsId, int position, int offset){
        String notInRoomsIds = new String();
        if(roomsId!=null) {
            notInRoomsIds = " and id not in " + roomsId;
        }
        try(PreparedStatement statement = connection.prepareStatement(
                SELECT_ALL_ROOMS +" where numOfPlaces = ? " + notInRoomsIds +
                        " and classOfRoom = ? LIMIT ?,?")){
            statement.setInt(1,request.getNumOfPlaces());
            statement.setString(2,request.getClassOfRoom());
            statement.setInt(3, position);
            statement.setInt(4, offset);
            return parseSet(statement.executeQuery());
        }catch (SQLException e){
            logger.error("Error rooms by not suitable id, numOfPlaces and classOfRoom");
        }
        return null;
    }

    /**
     * gets number of rows where number of places and class of room matches request parameters
     * and except rows where id matched one of the ids from not matched ids
     * @param request  is the request which parameters the result have to be matched
     * @param roomsId  is a string with ids which are not matched
     * @return number of found rows
     */
    public int getNumOfRowsByRequestParameters(Request request, String roomsId){
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

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> findWithOffsetFromPosition(int from, int amount){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROOMS +" LIMIT ?,?")){
            statement.setInt(1,from);
            statement.setInt(2, amount);
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
