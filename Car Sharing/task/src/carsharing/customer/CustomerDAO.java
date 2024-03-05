package carsharing.customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getAll();
    Customer getById(int id);
    void insert(Customer customer);
    void update(Customer customer);
    void resetCar(Customer customer);
    boolean isEmpy();
}
