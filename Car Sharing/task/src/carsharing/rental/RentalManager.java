package carsharing.rental;

import carsharing.car.Car;
import carsharing.car.CarDAO;
import carsharing.car.CarDAOImpl;
import carsharing.company.Company;
import carsharing.company.CompanyDAO;
import carsharing.company.CompanyDAOImpl;
import carsharing.company.CompanyManager;
import carsharing.customer.Customer;
import carsharing.customer.CustomerDAO;
import carsharing.customer.CustomerDAOImpl;
import carsharing.util.ConsoleInput;
import carsharing.util.DbConnectionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RentalManager {

    private static final CustomerDAO customerDAO = new CustomerDAOImpl(DbConnectionManager.getConnection());
    private static final CarDAO carDAO = new CarDAOImpl(DbConnectionManager.getConnection());
    private static final CompanyDAO companyDAO = new CompanyDAOImpl(DbConnectionManager.getConnection());

    private static final Map<Integer, Consumer<Customer>> ACTIONS = new HashMap<>() {{
        put(1, RentalManager::rentCar);
        put(2, RentalManager::returnCar);
        put(3, RentalManager::showRentedCar);
    }};

    private RentalManager() {
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
    }

    public static void manage(Customer selectedCustomer) {
        while (true) {
            displayMenu();
            int choice = ConsoleInput.nextInt();
            if (choice == 0)
                return;

            ACTIONS.getOrDefault(choice, x ->
                    System.out.println("\nInvalid option. Try again")).accept(selectedCustomer);
        }
    }

    public static void showRentedCar(Customer selectedCustomer) {
        Customer customer = customerDAO.getById(selectedCustomer.getId());
        System.out.println();
        if (customer.getRentedCarId() == null) {
            System.out.println("You didn't rent a car!");
        } else {
            Car car = carDAO.getById(selectedCustomer.getRentedCarId());
            System.out.println();
            if (car != null) {
                Company company = CompanyManager.getCompany(car);
                System.out.println("Your rented car:");
                System.out.println(car.getName());
                System.out.println("Company:");
                System.out.println(company.getName());
            }
        }
    }


    public static void rentCar(Customer selectedCustomer) {
        System.out.println();
        Customer customer = customerDAO.getById(selectedCustomer.getId());
        Company selectedCompany;
        if (customer.getRentedCarId() != null) {
            System.out.println("You've already rented a car!");
            return;
        }

        System.out.println();

        if (companyDAO.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }

        while (true) {

            List<Company> companies = companyDAO.getAll();
            System.out.println("Choose a company:");
            for (int i = 0; i < companies.size(); i++) {
                Company company = companies.get(i);
                System.out.println((i + 1) + ". " + company.getName());
            }
            System.out.println("0. Back");

            int choice = ConsoleInput.nextInt();

            if (choice == 0) {
                return;
            }

            if (choice > 0 && choice <= companies.size()) {
                selectedCompany = companies.get(choice - 1);
                assignCar(selectedCustomer, selectedCompany);
                return;
            } else {
                System.out.println();
                System.out.println("Invalid option. Try again");
            }
        }
    }

    private static void assignCar(Customer selectedCustomer, Company selectedCompany) {
        while (true) {
            List<Car> availableCars = carDAO.getAvailable(selectedCompany.getId());
            System.out.println();
            System.out.println("Choose a car:");
            for (int i = 0; i < availableCars.size(); i++) {
                Car car = availableCars.get(i);
                System.out.println((i + 1) + ". " + car.getName());
            }
            System.out.println("0. Back");

            int choice = ConsoleInput.nextInt();
            if (choice == 0) {
                return;
            }

            System.out.println();
            if (choice > 0 && choice <= availableCars.size()) {
                Car selectedCar = availableCars.get(choice - 1);
                selectedCustomer.setRentedCarId(selectedCar.getId());
                customerDAO.update(selectedCustomer);
                System.out.println("You rented '" + selectedCar.getName() + "'");
                break;
            } else {
                System.out.println("Invalid option. Try again");
            }
        }
    }

    public static void returnCar(Customer selectedCustomer) {
        Customer customer = customerDAO.getById(selectedCustomer.getId());
        System.out.println();
        if (customer.getRentedCarId() != null) {
            customerDAO.resetCar(selectedCustomer);
            System.out.println("You've returned a rented car!");
            selectedCustomer.setRentedCarId(null);
        } else {
            System.out.println("You didn't rent a car!");
        }
    }
}
