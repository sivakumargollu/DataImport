package usecases.merger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by sivag on 31/1/17.
 */
public class MySQLConnection {

    static Connection connection = null;
    Properties properties = null;
    String dbUrl = "";
    static MySQLConnection obj = null;

    public static MySQLConnection get() {
        if(obj == null){
            obj =  new MySQLConnection();
            return obj;
        }
        else {
            return obj;
        }
    }

    /**
     * Initializes driver class and properties files.
     */
    private MySQLConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            InputStream inputStream = new FileInputStream(properties.getProperty("database.properties"));
            Properties properties = new Properties();
            properties.load(inputStream);
            dbUrl = "jdbc:mysql://"+properties.get("host") +":"+properties.get("port")+"/"+properties.get("database");
        }
        catch (Exception cnfe){

        }
    }

    /**
     * Returns new connection every time
     * @return
     * @throws SQLException
     */
    public  Connection create() throws SQLException {
        return DriverManager.getConnection(dbUrl,
                properties.getProperty("user"),properties.getProperty("password"));
    }

    /**
     * Return singleton object
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if(connection == null){
            synchronized (this){
                if(connection ==  null) {
                    connection = DriverManager.getConnection(dbUrl,
                            properties.getProperty("user"), properties.getProperty("password"));
                }
            }
        }
        return connection;
    }

}
