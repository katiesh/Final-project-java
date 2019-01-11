package model.dao.factory;

import model.entity.Request;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO extends AbstractDAO<Request> {
    final static Logger logger = Logger.getLogger(RequestDAO.class);
    public static final String SELECT_ALL_REQUESTS = "SELECT* FROM finalproject.requests";

    public RequestDAO(){
        super();
    }

    private List<Request> parseSet(ResultSet resultSet) throws SQLException {
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

    @Override
    public List<Request> findAll() {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_REQUESTS);
            return parseSet(result);
        } catch (SQLException e){
            logger.error("Error statement requests");
        }
        return null;
    }

    public List<Request> findFromTo(int from, int to){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS +" LIMIT ?,?")){
            statement.setInt(1,from);
            statement.setInt(2,to);
           return parseSet(statement.executeQuery());
        }catch(SQLException e){
            logger.error("Error prepared statement requests find from to");
        }
        return null;
    }

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

    @Override
    public Request findEntityById(int id) {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_REQUESTS+" where id = " + id);
            return parseSet(result).get(0);
        }catch (SQLException e){
            logger.error("Error statement requests by id");
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM finalproject.requests WHERE id = " + id);
            return true;
        }catch (SQLException e){
            logger.error("Error requests delete by id");
        }
        return false;
    }

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
            logger.error("PreparedStatement requests create");
        }
        return false;
    }

    public boolean updateStatus(Request request){
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("UPDATE finalproject.requests SET status = \'processed\' WHERE id  = " +
                    request.getId());
            return true;
        }catch (SQLException e){
            logger.error("Error statement requests update status");
        }
        return false;
    }

    public List<Request> findRequestByStatus(String status){
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS +
                " WHERE status = ?")){
            statement.setString(1,status);
            ResultSet result = statement.executeQuery();
            return parseSet(result);
        }catch (SQLException e){
            logger.error("Error statement requests by status");
        }
        return null;
    }

    public List<Request> findRequestsFromToById(int from, int to, int clientId){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS +" WHERE clientId = ? ORDER BY id DESC LIMIT ?,?")){
            statement.setInt(1,clientId);
            statement.setInt(2,from);
            statement.setInt(3,to);
            return parseSet(statement.executeQuery());
        }catch(SQLException e){
            logger.error("Error prepared statement requests find from to");
        }
        return null;
    }

    public List<Request> findNewRequestsFromTo(int from, int to){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS +" WHERE status = \"waiting to be processed\" ORDER BY id DESC LIMIT ?,?")){
            statement.setInt(1,from);
            statement.setInt(2,to);
            return parseSet(statement.executeQuery());
        }catch(SQLException e){
            logger.error("Error prepared statement requests find new from to");
        }
        return null;
    }
}
