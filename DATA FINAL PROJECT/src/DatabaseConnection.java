import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/RestaurantReservationSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    private DatabaseConnection() {}

    private static void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the database successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Database Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection conn = getConnection();

        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.out.println("Failed to close the connection.");
                e.printStackTrace();
            }
        }
    }
}
