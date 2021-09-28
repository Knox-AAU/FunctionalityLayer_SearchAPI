package searchengine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnection implements ISqlConnection {

    public SqlConnection() throws SQLException, ClassNotFoundException {
        this.connection = getConnection();
    }

    private Connection connection;

    /** Initialises the postgresql driver and gets a connection to the database
     * @throws ClassNotFoundException
     * @throws java.sql.SQLException
     * @return Connection to wordcount
     */
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        //Class.forName(...) is needed for initializing the driver as jdbc
        // for more info: https://jdbc.postgresql.org/documentation/81/load.html
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/wordcount",
                "postgres",
                "1234");
    }

    /** Returns the java.sql.Connection.createStatement() on the wrapped class
     * @return java.sql.Statement
     */
    @Override
    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    /** Calls the java.sql.Connection.close() on the wrapped class
     */
    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
