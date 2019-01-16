package model.dao.factory.implementation;

import model.dao.factory.AbstractDAO;
import model.entity.Booking;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides operations with table bookings from database
 * @author Kateryna Shkulova
 */
public class BookingDAO extends AbstractDAO<Booking> {
    /**
     * field logger
     */
    private static final Logger logger = Logger.getLogger(BookingDAO.class);
    /**
     * field select all bookings is a SQL request which select all from table
     */
    private static final String SELECT_ALL_BOOKINGS = "SELECT* FROM finalproject.bookings";

    /**
     * constructor without parameters
     */
    public BookingDAO() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    private List<Booking> parseSet(ResultSet resultSet) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        while (resultSet.next()){
            Booking newBooking = new Booking();
            newBooking.setId(resultSet.getInt(1));
            newBooking.setClientId(resultSet.getInt(5));
            newBooking.setDateFrom(resultSet.getDate(3));
            newBooking.setDateTo(resultSet.getDate(4));
            newBooking.setRoomId(resultSet.getInt(2));
            newBooking.setRequestId(resultSet.getInt(6));
            newBooking.setStatus(resultSet.getString(7));
            newBooking.setPrice(resultSet.getDouble(8));
            bookings.add(newBooking);
        }
        return bookings;
    }

    /**
     *{@inheritDoc}
     * @return booking {@link Booking}
     */
    @Override
    public Booking findEntityById(int id) {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_BOOKINGS+" where id=" + id);
            List<Booking> bookings = parseSet(result);
            if(!bookings.isEmpty())
            return bookings.get(0);
        }catch (SQLException e){
            logger.error("Error statement bookings by id");
        }
        return null;
    }

    /**
     * get booking by request's id
     * @param requestId id of the request
     * @return found booking {@link Booking}
     */
    public Booking findByRequestId(int requestId){
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_BOOKINGS+" where requestId = " + requestId );
            List<Booking> bookings = parseSet(result);
            if(bookings!=null)
            return bookings.get(0);
        }catch (SQLException e){
            logger.error("Error statement bookings by id");
        }
        return null;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean create(Booking entity) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO finalproject.bookings " +
                "(roomId, dateFrom, dateTo, clientId, requestId, price) VALUES (?,?,?,?,?,?)")){
            connection.setAutoCommit(false);
            statement.setInt(1,entity.getRoomId());
            statement.setDate(2,entity.getDateFrom());
            statement.setDate(3, entity.getDateTo());
            statement.setInt(4,entity.getClientId());
            statement.setInt(5, entity.getRequestId());
            statement.setDouble(6, entity.getPrice());
            statement.execute();
            return true;
        }catch (SQLException e){
            logger.error("PreparedStatement bookings createBooking");
            connection.rollback();
        }
        return false;
    }

    /**
     * gets List of booked room's ids by date from and to
     * @param dateFrom date from
     * @param dateTo date to
     * @return List of room's ids
     */
    public List<Integer> findRoomsIdByDate(Date dateFrom, Date dateTo){
        List<Integer> roomsId = new ArrayList<>();
//        try(Statement statement = connection.createStatement()){
//            ResultSet result = statement.executeQuery( SELECT_ALL_BOOKINGS + " where (dateFrom > " + dateFrom +
//                    " OR dateFrom <= " + dateTo + ") AND (dateTo >= " + dateFrom + " OR dateTo >= " + dateTo);
           try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKINGS + " where (dateFrom >= ? and dateFrom < " +
                   "?) OR (dateTo < ? AND dateTo > ?)")){
               preparedStatement.setDate(1, dateFrom);
               preparedStatement.setDate(2, dateTo);
               preparedStatement.setDate(3,dateFrom);
               preparedStatement.setDate(4,dateTo);
               ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                roomsId.add(result.getInt(2));
            }
        }catch(SQLException e){
            logger.error("Statement booking by date");
        }
        return roomsId;
    }

//    public List<Integer> findRoomsIdsByRequestId(int requestId){
//        List<Integer> roomsId = new ArrayList<>();
//        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKINGS + " where requestId = ?")){
//            preparedStatement.setInt(1, requestId);
//            ResultSet result = preparedStatement.executeQuery();
//            while(result.next()){
//                roomsId.add(result.getInt(2));
//            }
//        }catch(SQLException e){
//            logger.error("Statement booking by date");
//        }
//        return roomsId;
//    }
//
//    public List<Booking> findBookingsByStatus(String status){
//        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKINGS + " WHERE status = ?")){
//            statement.setString(7,status);
//            ResultSet result = statement.executeQuery();
//            return parseSet(result);
//        }catch (SQLException e){
//            logger.error("Error statement bookings by status");
//        }
//        return null;
//    }

//    public boolean updateStatus(Booking booking,String status){
//        try (PreparedStatement statement = connection.prepareStatement("UPDATE finalproject.bookings SET status = ? " +
//                "WHERE  id = ?")){
//            statement.setString(1,status);
//            statement.setInt(2,booking.getId());
//            statement.executeUpdate();
//            return true;
//        }catch (SQLException e){
//            logger.error("Error statement bookings update status");
//        }
//        return false;
//    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int getNumOfRows(){
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT COUNT(id) AS count FROM finalproject.bookings;");
            if(result.next())
                return  result.getInt("count");
        }catch (SQLException e){
            logger.error("Error statement requests num of rows");
        }
        return 0;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Booking> findWithOffsetFromPosition(int from, int amount){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKINGS +" ORDER BY id DESC LIMIT ?, ?")){
            statement.setInt(1,from);
            statement.setInt(2, amount);
            return parseSet(statement.executeQuery());
        }catch(SQLException e){
            logger.error("Error prepared statement bookings find from to");
        }
        return null;
    }
    public void connCommit() throws SQLException {
        connection.commit();
    }

    public void setAutoCommitTrue() throws SQLException {
        connection.setAutoCommit(true);
    }
    public void rollback() throws SQLException {
        connection.rollback();
    }

    public List<Booking> getAll(){
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_BOOKINGS);
            return parseSet(result);
        }catch (SQLException e){
            logger.error("Error statement booking get all");
            return null;
        }
    }

    public boolean delete(Booking booking){
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE from finalproject.bookings where id = ?")){
            preparedStatement.setInt(1, booking.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error bookings statement delete");
            return false;
        }
    }


}
