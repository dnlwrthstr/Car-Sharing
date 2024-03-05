package carsharing.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbCreator {
    private static final String CREATE_COMPANY_TABLE =
            "CREATE TABLE IF NOT EXISTS COMPANY(ID INT PRIMARY KEY AUTO_INCREMENT," +
                    " NAME VARCHAR UNIQUE NOT NULL)";

    private static final String CREATE_CAR_TABLE =
            "CREATE TABLE IF NOT EXISTS CAR(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR UNIQUE NOT NULL, " +
                    "COMPANY_ID INT NOT NULL, " +
                    "CONSTRAINT fk_car_company FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";

    private static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE IF NOT EXISTS CUSTOMER(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR UNIQUE NOT NULL, " +
                    "RENTED_CAR_ID INT, " +
                    "CONSTRAINT fk_customer_car FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";

    private DbCreator() {
    }

    public static void createTables() {
        Connection connection = DbConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(CREATE_COMPANY_TABLE);
            statement.executeUpdate(CREATE_CAR_TABLE);
            statement.executeUpdate(CREATE_CUSTOMER_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create database tables", e);
        }
    }
}
