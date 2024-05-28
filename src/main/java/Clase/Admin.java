package Clase;

import java.util.Scanner;

public class Admin extends Employee {

    public Admin(String employeeID, String name, String password) {
        super(employeeID, name, password);
    }

    public void changeClientDetails(Client client, Scanner scanner) {
        System.out.println("Modify details for Client: " + client.getClientID());

        System.out.print("Enter new name (or press enter to keep " + client.getClientName() + "): ");
        scanner.nextLine(); // consume the leftover newline
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            client.setClientName(newName);
        }

        System.out.print("Enter new surname (or press enter to keep " + client.getClientSurname() + "): ");
        String newSurname = scanner.nextLine();
        if (!newSurname.isEmpty()) {
            client.setClientSurname(newSurname);
        }

        System.out.print("Enter new email (or press enter to keep " + client.getClientEmail() + "): ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) {
            client.setClientEmail(newEmail);
        }

        System.out.print("Enter new phone number (or press enter to keep " + client.getClientPhoneNumber() + "): ");
        String newPhoneNumber = scanner.nextLine();
        if (!newPhoneNumber.isEmpty()) {
            client.setClientPhoneNumber(newPhoneNumber);
        }

        System.out.println("Client details updated successfully.");
    }

    public void changeEmployeeDetails(Employee employee, Scanner scanner) {
        System.out.println("Modify details for Employee: " + employee.getEmployeeID());

        System.out.print("Enter new name (or press enter to keep " + employee.getName() + "): ");
        scanner.nextLine(); // consume the leftover newline
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            employee.setName(newName);
        }

        System.out.print("Enter new password (or press enter to keep current password): ");
        String newPassword = scanner.nextLine();
        if (!newPassword.isEmpty()) {
            employee.setPassword(newPassword);
        }

        System.out.println("Employee details updated successfully.");
    }

    public void addEmployee(EmployeeRepository employeeRepository, Scanner scanner) {
        System.out.print("Enter new employee ID: ");
        String employeeID = scanner.next();
        System.out.print("Enter new employee name: ");
        scanner.nextLine(); // consume the leftover newline
        String name = scanner.nextLine();
        System.out.print("Enter new employee password: ");
        String password = scanner.nextLine();

        Employee newEmployee = new Employee(employeeID, name, password);
        employeeRepository.addEmployee(newEmployee);

        System.out.println("Employee added successfully.");
    }

    public void deleteEmployee(EmployeeRepository employeeRepository, Scanner scanner) {
        System.out.print("Enter employee ID to delete: ");
        String employeeID = scanner.next();
        Employee employee = employeeRepository.getEmployeeById(employeeID);

        if (employee != null) {
            employeeRepository.deleteEmployee(employeeID);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void visualizeProductDetails(Product product) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Product Details:");
        System.out.println("\tBarcode: " + product.getBarcode());
        System.out.println("\tName: " + product.getName());
        System.out.println("\tCategory: " + product.getCategoryId());
        System.out.println("\tPrice: " + product.getPrice());
        System.out.println("\tStock: " + product.getStock());
        System.out.println("\tDiscount: " + product.getDiscount() + "%");
        System.out.println("----------------------------------------------------------------");
    }
}
