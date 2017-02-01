package usecases.merger;

import java.sql.SQLException;

/**
 * Created by sivag on 1/2/17.
 */
public class ConnectionFactory<T> {
    T connection = null;

    /**
     *
     * @param dbType
     * @return
     * @throws SQLException
     */
    public T getConnection(String dbType) throws SQLException {
        if (dbType.equals("mysql")) {
            connection = (T) MySQLConnection.get().getConnection();
        }
        // Statements to get db connection
        else
        {
            connection = (T) MySQLConnection.get().getConnection();
        }
        return connection;
    }
}
