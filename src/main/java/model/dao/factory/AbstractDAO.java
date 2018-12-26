package model.dao.factory;

import model.dao.connection.ConnectionMySQL;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDAO <T> {
//    final static Logger logger = (Logger) Logger.getLogger(String.valueOf(AbstractDAO.class));
    protected Connection connection;
    public AbstractDAO(){
         connection = new ConnectionMySQL().getConnection();
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
