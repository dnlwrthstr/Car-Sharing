package carsharing.company;

import java.util.List;

public interface CompanyDAO {
    List<Company> getAll();

    boolean isEmpty();
    void insert(Company company);
    Company getById(int id);
}
