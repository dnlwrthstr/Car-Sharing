package carsharing.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    private final Connection connection;

    public CompanyDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Company> getAll() {
        String sql = "SELECT * FROM Company";
        List<Company> companies = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Company company = new Company(id, name);
                companies.add(company);
            }
            connection.commit();
        } catch (SQLException e) {
            // handle the error
            System.out.println(e.getMessage());
        }
        return companies;
    }

    public boolean isEmpty() {
        String sql = "SELECT COUNT(*) FROM Company";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void insert(Company company) {
        String sql = "INSERT INTO Company(name) VALUES(?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("The company was created!");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert company", e);
        }
    }

    @Override
    public Company getById(int companyId) {
        String sql = "SELECT * FROM company WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, companyId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                return new Company(id, name);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve car by id", e);
        }
    }
}
