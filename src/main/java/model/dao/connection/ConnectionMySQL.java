package model.dao.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionMySQL {
    final static Logger logger = Logger.getLogger(ConnectionMySQL.class);
    private static volatile DataSource dataSource;

    private static DataSource getDataSource() {
        if(dataSource == null){
            synchronized (ConnectionMySQL.class){
                if(dataSource == null){
                    BasicDataSource ds = new BasicDataSource();
                    ResourceBundle resource = ResourceBundle.getBundle("database");
                    ds.setDriverClassName(resource.getString("driver"));
                    ds.setUrl(resource.getString("url"));
                    ds.setUsername(resource.getString("user"));
                    ds.setPassword(resource.getString("password"));
                    ds.setMinIdle(Integer.valueOf(resource.getString("min")));
                    ds.setMaxIdle(Integer.valueOf(resource.getString("max")));
                    ds.setMaxOpenPreparedStatements(Integer.valueOf(resource.getString("statements")));
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }

    public Connection getConnection(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
