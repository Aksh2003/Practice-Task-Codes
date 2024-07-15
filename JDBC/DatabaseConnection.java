
import java.sql.*;

public enum DatabaseConnection {
    INSTANCE;

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/login_schema";
    private final String JDBC_USER = "root";
    private final String JDBC_PASSWORD = "root";
    private Connection connection;

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    
}
