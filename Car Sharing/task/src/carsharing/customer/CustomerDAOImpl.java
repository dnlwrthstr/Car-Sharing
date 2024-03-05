package carsharing.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private final Connection connection;

    public CustomerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Customer getById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int customerId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer rentedCarId = resultSet.getObject("rented_car_id", Integer.class);
                return new Customer(customerId, name, rentedCarId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve car by id", e);
        }
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int companyId = resultSet.getInt("rented_car_id");
                Customer customer = new Customer(id, name, companyId);
                customers.add(customer);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to retrieve customers", e);
        }
        return customers;
    }

    @Override
    public void insert(Customer customer) {
        String sql = "INSERT INTO Customer(name) VALUES(?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("The customer was added!");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert a customer", e);
        }
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE Customer SET name = ?, rented_car_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setInt(2, customer.getRentedCarId());
            preparedStatement.setInt(3, customer.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update a customer", e);
        }
    }

    @Override
    public void resetCar(Customer customer) {
        String sql = "UPDATE Customer SET rented_car_id = NULL WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update a customer", e);
        }
    }

    @Override
    public boolean isEmpy() {
        String sql = "SELECT COUNT(*) FROM customer";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count == 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check if customer table is empty", e);
        }
    }
}
