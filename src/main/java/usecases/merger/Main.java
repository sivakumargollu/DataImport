package usecases.merger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by sivag on 31/1/17.
 */
public class Main {

    /**
     * @param employeeList
     * @return
     */
    Properties properties = null;
    Connection con = null;
    /**
     * Load  properties from config file.
     */

    public Main(){

        try {
            FileInputStream inputStream = new FileInputStream("resources/application.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * @param employeeList
     * @return Set of employees deleted from internal application's database.
     */
    private HashSet<Long> merge(List<Employee> employeeList) {
        Iterator<Employee> iterator = employeeList.iterator();
        HashSet<Long> removeList = new HashSet<Long>();
        synchronized (this) {
            while (iterator.hasNext()) {
                Employee employee = iterator.next();
                try {
                    //Either update existing or insert new if not exists

                    if (!update(employee))
                        removeList.add(employee.getCode());
                    // Remove all records which are available in db but not in excluded list.
                    remove(removeList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return removeList;
    }

    /**
     * Removes given list of employees.
     * @param removeList
     * @return
     * @throws SQLException
     *
     */
    private boolean remove(HashSet<Long> removeList) throws SQLException{
       Iterator<Long> iterator = removeList.iterator();
       String ids = "";
       while (iterator.hasNext()){
           ids += iterator.next()+",";
       }
       //truncate leading comma ","
        String sql = "delete from Employee where code in "+ids.replaceAll(",$","");
        Connection conn = getDbConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        boolean status =  stmt.execute();
        stmt.close();
        conn.close();
        return status;

    }

    /**
     * Perform insert or update operation
     * @param employee
     * @return
     * @throws SQLException
     */
    private boolean update(Employee employee) throws SQLException {

        String sql = "replace into Employee('code','name','department','phone') values (?,?,?,?)";
        Connection conn = getDbConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1,employee.getCode());
        stmt.setString(2,employee.getName());
        stmt.setString(3,employee.getDepartment());
        stmt.setLong(4,employee.getPhone());
        stmt.execute();
        boolean status = stmt.getUpdateCount() > 0 || stmt.getResultSet() != null;
        stmt.close();
        conn.close();
        return status;
    }

    /**
     *
     * @param <T>
     * @return Connection db configuration and dbtype
     */
    private <T extends Object> T getDbConnection() {
        String dbtype = properties.getProperty("dbtype");
        T connection = null;
        if(con != null){
            return (T) con;
        }
        else {
            ConnectionFactory<Connection> factory = new ConnectionFactory<Connection>();
            try {
                connection = (T) factory.getConnection(dbtype);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }


}
