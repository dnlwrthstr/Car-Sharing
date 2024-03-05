package carsharing.car;

import carsharing.company.Company;
import carsharing.util.ConsoleInput;
import carsharing.util.DbConnectionManager;

import java.util.List;

public class CarManager {
    private static final CarDAO carDAO = new CarDAOImpl(DbConnectionManager.getConnection());
    private static final int EXIT_CHOICE = 0;
    private static final int LIST_CARS_CHOICE = 1;
    private static final int CREATE_CAR_CHOICE = 2;
    private static final String INVALID_INPUT_MESSAGE = "Invalid option. Try again";

    private CarManager() {
    }

    public static void manageCars(Company selectedCompany) {
        while (true) {
            printMenu(selectedCompany);
            int choice = ConsoleInput.nextInt();

            if (choice == EXIT_CHOICE)
                return;

            switch (choice) {
                case LIST_CARS_CHOICE:
                    displayCarList(selectedCompany);
                    break;

                case CREATE_CAR_CHOICE:
                    createCar(selectedCompany);
                    break;

                default:
                    System.out.println(INVALID_INPUT_MESSAGE);
                    break;
            }
        }
    }

    private static void createCar(Company selectedCompany) {
        System.out.println();
        System.out.println("Enter the car name:");
        try {
            String carName = ConsoleInput.nextLine();
            carDAO.insert(new Car(carName, selectedCompany.getId()));
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid name!");
        }
    }

    private static void displayCarList(Company selectedCompany) {
        System.out.println();
        if (carDAO.isEmpty(selectedCompany.getId())) {
            System.out.println("The car list is empty!");
            return;
        }

        List<Car> cars = carDAO.getByCompanyId(selectedCompany.getId());
        System.out.println(selectedCompany.getName() + " cars:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i).getName());
        }
    }

    private static void printMenu(Company selectedCompany) {
        System.out.println();
        System.out.println(selectedCompany.getName() + " company:");
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }
}
