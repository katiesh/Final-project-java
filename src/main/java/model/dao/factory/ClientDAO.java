package model.dao.factory;

import model.entity.Client;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends AbstractDAO<Client> {
    final static Logger logger = Logger.getLogger(ClientDAO.class);
    public static final String SELECT_ALL_CLIENTS = "SELECT* FROM clients";
    public ClientDAO() {
        super();
    }

    private List<Client> parseSet(ResultSet resultSet) throws SQLException {
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

    @Override
    public List<Client> findAll() {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_CLIENTS);
            return parseSet(result);
        }catch (SQLException e){
            logger.error("Error statement clients");
        }
        return null;
    }

    @Override
    public Client findEntityById(int id) {
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL_CLIENTS+"where id=" + id);
            List<Client> client = parseSet(result);
            if(!client.isEmpty())
            return client.get(0);
        }catch (SQLException e){
            logger.error("Error statement clients by id");
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM clients WHERE id = " + id);
            return true;
        }catch (SQLException e){
            logger.error("Error statement clients delete by id");
        }
        return false;
    }

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
            logger.error("PreparedStatement bookings create");
        }
        return false;
    }

    @Override
    public int getNumOfRows() {
        return 0;
    }

    @Override
    public List<Client> findFromTo(int from, int to) {
        return null;
    }

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

    public Client findEntity(Client client){
        if(client!=null){
            try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CLIENTS+" where email = ? " +
                    "OR telNumber = ?")){
                statement.setString(2,client.getTelNumber());
                statement.setString(1,client.getEmail());
                ResultSet result = statement.executeQuery();
                return parseSet(result).get(0);
            }catch (SQLException e){
                logger.error("Error statement clients by email");
            }
        }
        return null;
    }
}
