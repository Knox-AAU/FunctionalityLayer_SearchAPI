package searchengine;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface ISqlConnection {
    public Statement createStatement() throws SQLException;
    public void close() throws SQLException;
}
