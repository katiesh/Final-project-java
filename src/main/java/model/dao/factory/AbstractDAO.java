package model.dao.factory;

import model.dao.connection.ConnectionMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract class provides general methods for tables from database
 * @param <T> entity
 * @author Kateryna Shkulova
 */
public abstract class AbstractDAO <T> {
    /**
     * field connection to database
     */
    protected Connection connection;

    /**
     * constructor without parameters
     * sets connection as {@link ConnectionMySQL#getConnection()}
     */
    public AbstractDAO(){
         connection = new ConnectionMySQL().getConnection();
    }

    public AbstractDAO(Connection connection){
        this.connection = connection;
    }

    public Connection getConnection(){
        return connection;
    }

    /**
     * gets entity from database by its id
     * @param id id of the entity
     * @return found entity
     */
    public abstract T findEntityById(int id);

    /**
     * adds the entity to database
     * @param entity entity which have to be added
     * @return true if the entity is added to database
     * @return false if the entity is not added to database
     */
    public abstract boolean create(T entity) throws SQLException;

    /**
     * gets number of all rows in table from database
     * @return
     */
    public abstract int getNumOfRows();

    /**
     * gets defined number of entities from defined position from table
     * @param from is the start position(row)
     * @param amount is the number of entities to get
     * @return list of found entities
     */
    public abstract List<T> findWithOffsetFromPosition(int from, int amount);
    /**
     * parse ResultSet to the list of entities
     * @param resultSet result set of the query
     * @return list of entities
     * @throws SQLException
     */
}
