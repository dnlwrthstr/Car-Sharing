package carsharing.customer;

import carsharing.util.ConsoleInput;
import carsharing.util.DbConnectionManager;

import java.util.List;

import static carsharing.rental.RentalManager.manage;

public class CustomerManager {

    private static final CustomerDAO customerDAO = new CustomerDAOImpl(DbConnectionManager.getConnection());

    private CustomerManager() {
    }

    public static void createCustomer() {
        System.out.println();
        System.out.println("Enter the customer name:");
        try {
            String customerName = ConsoleInput.nextLine();
            customerDAO.insert(new Customer(customerName));
        } catch (IllegalArgumentException e) {
            System.out.println("Not a valid Name!");
        }
    }

    public static void listCustomers() {

        System.out.println();
        if (customerDAO.isEmpy()) {
            System.out.println("The customer list is empty!");
            return;
        }

        List<Customer> customers = customerDAO.getAll();
        System.out.println("Customer list:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).getName());
        }
        System.out.println("0. Back");


        int choice = ConsoleInput.nextInt();
        if (choice > 0 && choice <= customers.size()) {

            Customer selectedCustomer = customers.get(choice - 1);
            manage(selectedCustomer);
        } else if (choice != 0) {
            System.out.println("Invalid option. Try again");
        }
    }


}
