package model.dao.factory.implementation;

import model.dao.factory.AbstractDAO;
import model.entity.Client;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides operations with table clients from database
 * @author Kateryna Shkulova
 */
public class ClientDAO extends AbstractDAO<Client> {
    /**
     * field logger
     */
     private static final Logger logger = Logger.getLogger(ClientDAO.class);
    /**
     * field select all clients is a SQL request which select all from table
     */
    private static final String SELECT_ALL_CLIENTS = "SELECT* FROM clients";

    /**
     * constructor without parameters
     */
    public ClientDAO() {
        super();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    protected List<Client> parseSet(ResultSet resultSet) throws SQLException {
        List<Client> clients = new ArrayList<>();
        while (resultSet.next()){
            Client newClient = new Client();
            newClient.setId(resultSet.getInt(1));
            newClient.setEmail(resultSet.getString(5));
            newClient.setName(resultSet.getString(2));
            newClient.setSurname(resultSet.getString(3));
            newClient.setTelNumber(resultSet.getString(4));

            clients.add(newClient);
        }
        return clients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client findEntityById(int id) {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_CLIENTS+" where id=" + id);
            List<Client> client = parseSet(result);
            if(!client.isEmpty())
            return client.get(0);
        }catch (SQLException e){
            logger.error("Error statement clients by id");
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Client entity) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO clients " +
                "(name, surname, telNumber, email) VALUES (?,?,?,?)")){
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getSurname());
            statement.setString(3, entity.getTelNumber());
            statement.setString(4,entity.getEmail());
            statement.execute();
            return true;
        }catch (SQLException e){
            logger.error("PreparedStatement bookings createBooking");
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumOfRows() {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT COUNT(id) AS count FROM finalproject.clients;");
            if(result.next())
                return  result.getInt("count");
        }catch (SQLException e){
            logger.error("Error statement requests num of rows");
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findWithOffsetFromPosition(int from, int amount) {
        return null;
    }

    /**
     * gets entity client {@link Client} by its telephone number
     * @param tel is a telephone number
     * @return found entity {@link Client}
     */
    public Client findEntityByTel(String tel){
        if(tel!=null) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CLIENTS + " where telNumber=?")) {
                    statement.setString(1,tel);
                    ResultSet result = statement.executeQuery();
                List<Client> client = parseSet(result);
                if(!client.isEmpty()){
                        return client.get(0);
                    }
            } catch (SQLException e) {
                logger.error("Error statement clients by telephone");
            }
        }
        return null;
    }

    /**
     * gets entity client {@link Client} by its email
     * @param email is an email
     * @return found entity {@link Client}
     */
    public Client findEntityByEmail(String email){
        if(email!=null){
        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CLIENTS+" where email = ?")){
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();
            List<Client> client = parseSet(result);
            if(!client.isEmpty()){
                return client.get(0);
            }
            }catch (SQLException e){
            logger.error("Error statement clients by email");
            }
        }
        return null;
    }

}
