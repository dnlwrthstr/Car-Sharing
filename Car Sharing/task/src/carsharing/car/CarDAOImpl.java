package carsharing.car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    private final Connection connection;

    public CarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    private Car createCarFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int companyId = resultSet.getInt("company_id");
        return new Car(id, name, companyId);
    }

    @Override
    public Car getById(int id) {
        String sql = "SELECT * FROM car WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int carId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int companyId = resultSet.getInt("company_id");
                return new Car(carId, name, companyId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve car by id", e);
        }
    }

    @Override
    public List<Car> getByCompanyId(int companyId) {
        String sql = "SELECT * FROM car WHERE company_id = ?";
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(createCarFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to retrieve cars", e);
        }
        return cars;
    }


    @Override
    public boolean isEmpty(int companyId) {
        String sql = "SELECT COUNT(*) FROM car WHERE company_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check if car table is empty", e);
        }
    }

    @Override
    public List<Car> getAvailable(int selectedCompanyId) {
        String sql = "SELECT * FROM car WHERE id NOT IN ( " +
                "SELECT rented_car_id FROM customer WHERE rented_car_id IS NOT NULL) " +
                "AND company_id = ?";
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, selectedCompanyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer companyId = resultSet.getInt("company_id");
                Car car = new Car(id, name, companyId);
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to retrieve cars", e);
        }
        return cars;
    }

    @Override
    public void insert(Car car) {
        String sql = "INSERT INTO Car(name, company_id) VALUES(?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompanyId());
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("The car was added!");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert a car", e);
        }
    }
}
