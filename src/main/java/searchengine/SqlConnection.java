package searchengine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import io.github.cdimascio.dotenv.Dotenv;

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
        Dotenv dotenv = Dotenv.load();

        // Class.forName(...) is needed for initializing the driver as jdbc
        // For more info: https://jdbc.postgresql.org/documentation/81/load.html
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
                dotenv.get("DB_CONNECTION_URL"),
                dotenv.get("DB_CONNECTION_USERNAME"),
                dotenv.get("DB_CONNECTION_PASSWORD"));
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
