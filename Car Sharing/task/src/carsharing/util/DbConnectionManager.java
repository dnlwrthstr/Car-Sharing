package carsharing.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {

    private static final String DB_DIR = "jdbc:h2:./src/carsharing/db/";
    //private static final String DB_DIR = "jdbc:h2:tcp://localhost:9092/~/h2/car_sharing/";
    private static String dbFileName = "default";

    private DbConnectionManager() {
    }

    public static void setDbFileName(String databaseFileName) {
        dbFileName = databaseFileName;
    }
    public static Connection getConnection() {
        String url = DB_DIR + dbFileName;
        Connection connection;
        try {
            //connection = DriverManager.getConnection(url, "sa", "");
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to establish database connection", e);
        }return connection;
    }
}
