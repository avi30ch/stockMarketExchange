import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/login");
    // Enter your database username and password in the DB_USER and DB_PASSWORD environment variables.
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (USER == null || USER.isBlank() || PASSWORD == null) {
            throw new SQLException("Set the DB_USER and DB_PASSWORD environment variables.");
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver is unavailable.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
