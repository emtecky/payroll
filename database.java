import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
    String url = "jdbc:sqlite:employees.db";
    Connection connection = DriverManager.getConnection(url);
    Database() throws SQLException {
        throw new SQLException();
    }
    
}
