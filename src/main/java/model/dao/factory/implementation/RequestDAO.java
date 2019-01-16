package model.dao.factory.implementation;

import model.dao.factory.AbstractDAO;
import model.entity.Request;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides operations with table requests from database
 *  @author Kateryna Shkulova
 */
public class RequestDAO extends AbstractDAO<Request> {
    /**
     * field logger
     */
    private static final Logger logger = Logger.getLogger(RequestDAO.class);
    /**
     * field select all requests is an sql request which select all from table
     */
    private static final String SELECT_ALL_REQUESTS = "SELECT* FROM finalproject.requests ";

    /**
     * constructor without parameters
     */
    public RequestDAO(){
        super();
    }

    /**
     * constructor with one parameter
     * @param connection is a connection to db
     */
    public RequestDAO(Connection connection){
        super(connection);
    }
    /**
     * {@inheritDoc}
     */
    private
    List<Request> parseSet(ResultSet resultSet) throws SQLException {
        List<Request> requests = new ArrayList<>();
        while (resultSet.next()){
            Request newRequest = new Request();
            newRequest.setId(resultSet.getInt(1));
            newRequest.setClientId(resultSet.getInt(5));
            newRequest.setDateFrom(resultSet.getDate(3));
            newRequest.setDateTo(resultSet.getDate(4));
            newRequest.setNumOfPlaces(resultSet.getInt(6));
            newRequest.setClassOfRoom(resultSet.getString(2));
            newRequest.setStatus(resultSet.getString(7));
            requests.add(newRequest);
        }
        return requests;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Request> findWithOffsetFromPosition(int from, int amount){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS +" ORDER BY id DESC LIMIT ?,?")){
            statement.setInt(1,from);
            statement.setInt(2, amount);
           return parseSet(statement.executeQuery());
        }catch(SQLException e){
            logger.error("Error prepared statement requests find from to");
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumOfRows(){
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT COUNT(id) AS count FROM finalproject.requests;");
            if(result.next())
            return  result.getInt("count");
        }catch (SQLException e){
            logger.error("Error statement requests num of rows");
        }
        return 0;
    }

    /**
     * gets number of rows of the table with definite id
     * @param clientId is a definite id
     * @return number of found rows
     */
    public int getNumOfRowsByClientId(int clientId){
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT COUNT(id) AS count FROM finalproject.requests WHERE clientId = " + clientId);
            if(result.next())
                return  result.getInt("count");
        }catch (SQLException e){
            logger.error("Error statement requests num of rows");
        }
        return 0;
    }

    /**
     * gets number of rows of new requests(request which are not processed)
     * @return number of found rows
     */
    public int getNumOfRowsForNewRequests(){
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT COUNT(id) AS count FROM finalproject.requests" +
                    " WHERE status = \"waiting to be processed\"");
            if(result.next())
                return  result.getInt("count");
        }catch (SQLException e){
            logger.error("Error statement requests num of rows for new requests");
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Request findEntityById(int id) {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_REQUESTS+" where id = " + id);
            List<Request> requests = parseSet(result);
            if(!requests.isEmpty())
            return requests.get(0);
        }catch (SQLException e){
            logger.error("Error statement requests by id");
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Request entity) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO finalproject.requests " +
                "(classOfRoom, dateFrom, dateTo, clientId, numOfPlaces) VALUES (?,?,?,?,?)")){
            statement.setString(1,entity.getClassOfRoom());
            statement.setDate(2,entity.getDateFrom());
            statement.setDate(3, entity.getDateTo());
            statement.setInt(4,entity.getClientId());
            statement.setInt(5, entity.getNumOfPlaces());
            statement.execute();
            return true;
        }catch (SQLException e){
            logger.error("PreparedStatement requests createBooking");
        }
        return false;
    }

    /**
     * update status of request and sets it as 'processed'
     * @param request is the request which status have to be updated
     * @return true if the status was updated
     * @return false if the status was not updated
     */
    public boolean updateStatus(Request request) throws SQLException {
        try(Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate("UPDATE finalproject.requests SET status = \'processed\' WHERE id  = " +
                    request.getId());
            return true;
        }catch (SQLException e){
            logger.error(e);
            connection.rollback();
            throw e;
        }
    }

    public void setAutoCommitTrue() throws SQLException {
        connection.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

//    public List<Request> findRequestByStatus(String status){
//        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS +
//                " WHERE status = ?")){
//            statement.setString(1,status);
//            ResultSet result = statement.executeQuery();
//            return parseSet(result);
//        }catch (SQLException e){
//            logger.error("Error statement requests by status");
//        }
//        return null;
//    }

    /**
     * gets definite number of requests from definite position by client's id
     * @param position is the start position(row) in the table
     * @param offset the number of rows which will be got
     * @param clientId is client's id
     * @return list of found requests{@link Request}
     */
    public List<Request> findRequestsWithOffsetFromPositionByClientId(int position, int offset, int clientId){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS +" WHERE clientId = ? ORDER BY id DESC LIMIT ?,?")){
            statement.setInt(1,clientId);
            statement.setInt(2, position);
            statement.setInt(3, offset);
            return parseSet(statement.executeQuery());
        }catch(SQLException e){
            logger.error("Error prepared statement requests find from to");
        }
        return null;
    }
    /**
     * gets definite number of new requests(which are not processed) from definite position
     * @param position is the start position(row) in the table
     * @param offset the number of rows which will be got
     * @return list of found requests{@link Request}
     */
    public List<Request> findNewRequestsWithOffsetFromPosition(int position, int offset){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS +" WHERE status = \"waiting to be processed\" ORDER BY id DESC LIMIT ?,?")){
            statement.setInt(1, position);
            statement.setInt(2, offset);
            return parseSet(statement.executeQuery());
        }catch(SQLException e){
            logger.error("Error prepared statement requests find new from to");
        }
        return null;
    }

    public List<Request> getAll(){
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_REQUESTS);
            return parseSet(result);
        }catch (SQLException e){
            logger.error("Error statement requests get all");
            return null;
        }
    }

    public boolean delete(Request request) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE from finalproject.requests where id = ?")) {
            preparedStatement.setInt(1, request.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error requests statement delete");
            return false;
        }
    }
}
