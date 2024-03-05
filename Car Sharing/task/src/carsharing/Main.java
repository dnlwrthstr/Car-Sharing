package carsharing;

import carsharing.util.ConsoleInput;
import carsharing.util.DbConnectionManager;
import carsharing.util.DbCreator;

import static carsharing.company.CompanyManager.manageCompanies;
import static carsharing.customer.CustomerManager.createCustomer;
import static carsharing.customer.CustomerManager.listCustomers;

public class Main {
    public static void main(String[] args) {
        handleCommandLineArgs(args);
        DbCreator.createTables();
        runMenu();
    }

    /**
     * Handle Command Line arguments.
     *
     * @param args command line arguments
     */
    private static void handleCommandLineArgs(String[] args) {
        String databaseFileName = "default";
        int argsLength = args.length;
        for (int i = 0; i < argsLength; i++) {
            if ("-databaseFileName".equals(args[i]) && i + 1 < argsLength) {
                databaseFileName = args[i + 1];
            }
        }
        DbConnectionManager.setDbFileName(databaseFileName);
    }

    /**
     * Run the interactive menu.
     */
    private static void runMenu() {
        while (true) {
            printMenu();
            if (!handleInput())
                break;
        }
    }

    /**
     * Print the options menu.
     */
    private static void printMenu() {
        System.out.println();
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }

    /**
     * Handle user input for the menu.
     */
    private static boolean handleInput() {
        switch (ConsoleInput.nextInt()) {
            case 1:
                manageCompanies();
                break;
            case 2:
                listCustomers();
                break;
            case 3:
                createCustomer();
                break;
            case 0:
                return false;
            default:
                System.out.println("Invalid option. Try again");
                break;
        }
        return true;
    }
}