package carsharing.company;

import carsharing.car.Car;
import carsharing.car.CarManager;
import carsharing.util.ConsoleInput;
import carsharing.util.DbConnectionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyManager {
    private static final CompanyDAO companyDAO = new CompanyDAOImpl(DbConnectionManager.getConnection());

    private static final Map<Integer, Runnable> operations = new HashMap<>();

    static {
        operations.put(1, CompanyManager::showCompanyList);
        operations.put(2, CompanyManager::createCompany);
    }

    private CompanyManager() {
    }

    public static void manageCompanies() {
        while (true) {
            printMenu();
            int choice = ConsoleInput.nextInt();
            if (choice == 0)
                return;
            Runnable operation = operations.get(choice);
            if (operation != null) {
                operation.run();
            } else {
                System.out.println("Invalid option. Try again");
            }
        }
    }


    private static void printMenu() {
        System.out.println();
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

    public static Company getCompany(Car car) {
        return companyDAO.getById(car.getCompanyId());
    }

    private static void createCompany() {
        System.out.println();
        System.out.println("Enter the company name:");
        try {
            String name = ConsoleInput.nextLine();
            companyDAO.insert(new Company(name));
        } catch (IllegalArgumentException e) {
            System.out.println("Not a valid name!");
        }
    }

    private static void showCompanyList() {
        List<Company> companies = companyDAO.getAll();
        System.out.println();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose the company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println((i + 1) + ". " + companies.get(i).getName());
            }
            System.out.println("0. Back");

            int choice = ConsoleInput.nextInt();

            if (choice == 0)
                return;

            if (choice > 0 && choice <= companies.size()) {
                Company selectedCompany = companies.get(choice - 1);

                CarManager.manageCars(selectedCompany);
            } else {
                System.out.println("Invalid option. Try again");
            }
        }
    }
}
