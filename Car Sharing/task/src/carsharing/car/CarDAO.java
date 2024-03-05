package carsharing.car;

import java.util.List;

public interface CarDAO {

        List<Car> getByCompanyId(int companyId);
        List<Car> getAvailable(int companyId);
        void insert(Car car);
        Car getById(int id);
        boolean isEmpty(int companyId);
}
