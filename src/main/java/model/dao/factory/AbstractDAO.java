package model.dao.factory;

import model.dao.connection.ConnectionMySQL;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO <T> {
    final static Logger logger = Logger.getLogger(AbstractDAO.class);
//    final static Logger logger = (Logger) Logger.getLogger(String.valueOf(AbstractDAO.class));
    protected Connection connection;
    public AbstractDAO(){
         connection = new ConnectionMySQL().getConnection();
    }
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Error close connection");
        }
    }
    public abstract List<T> findAll();
    public abstract T findEntityById(int id);
    public abstract boolean delete(int id);
    public abstract boolean create(T entity);
    public abstract int getNumOfRows();
    public abstract List<T> findFromTo(int from, int to);
//    public abstract boolean delete (T entity);
//    public abstract T update(T entity);

}
