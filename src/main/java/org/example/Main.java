package org.example;

import Clase.*;

import java.util.Map;
import java.util.Scanner;

public class Main {
    static BonRepository bonRepository = BonRepository.getInstance();
    static ClientRepository clientRepository = ClientRepository.getInstance();
    static ProductRepository productRepository = ProductRepository.getInstance();
    static EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
    static AdminRepository adminRepository = AdminRepository.getInstance();
    static NotificationService notificationService = new NotificationService();
    static ClientService clientService = new ClientService();
    static ProductService productService = new ProductService();
    static ReceiptService receiptService = new ReceiptService();
    static EmployeeService employeeService = new EmployeeService();
    static Scanner sc = new Scanner(System.in);

    private static Client loggedInClient;
    private static Employee loggedInEmployee;
    private static Admin loggedInAdmin;

    public static void main(String[] args) {
        data.initialize();

        int option;
        boolean running = true;

        while (running) {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Take action as:");
            System.out.println("\t 1 -> Client");
            System.out.println("\t 2 -> Employee");
            System.out.println("\t 3 -> Admin");
            System.out.println("\t 4 -> Guest");
            System.out.println("\t 0 -> Exit Program");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    handleClientActions();
                    break;
                case 2:
                    handleEmployeeActions();
                    break;
                case 3:
                    handleAdminActions();
                    break;
                case 4:
                    handleGuestActions();
                    break;
                case 0:
                    running = false;
                    System.out.println("\nThank you for using the program. Have a great day!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void handleClientActions() {
        if (loginClient()) {
            System.out.println("Login successful!");
            int option;
            boolean running = true;
            while (running) {
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Client Actions:");
                System.out.println("\t 1 -> Interogare Bonuri");
                System.out.println("\t 2 -> Adaugare Bon");
                System.out.println("\t 3 -> Statistici");
                System.out.println("\t 4 -> Detalii Cont");
                System.out.println("\t 5 -> Subscribe to Notifications");
                System.out.println("\t 6 -> Unsubscribe from Notifications");
                System.out.println("\t 0 -> Exit Client Mode");
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                option = sc.nextInt();

                switch (option) {
                    case 1:
                        interogareBonuri();
                        break;
                    case 2:
                        adaugareBon();
                        break;
                    case 3:
                        statistici();
                        break;
                    case 4:
                        detaliiCont();
                        break;
                    case 5:
                        notificationService.subscribe(loggedInClient);
                        break;
                    case 6:
                        notificationService.unsubscribe(loggedInClient);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting Client Mode.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Login failed!");
        }
    }

    private static void registerGuestAsClient(Guest guest) {
        sc.nextLine(); // Consume the newline leftover
        boolean registered = false;
        while (!registered) {
            Client newClient = guest.registerAsClient(sc);
            if (validateClient(newClient)) {
                clientService.addClient(newClient);
                notificationService.subscribe(newClient); // Correctly reference the instance method
                System.out.println("Registration successful! You are now a client.");
                registered = true;
            } else {
                System.out.println("Invalid details. Please re-enter your information.");
            }
        }
    }

    private static boolean validateClient(Client client) {
        return Client.validateEmail(client.getClientEmail()) &&
                Client.validateCNP(client.getCNP()) &&
                Client.validateTelephoneNumber(client.getClientPhoneNumber());
    }

    private static void handleGuestActions() {
        Guest guest = new Guest();
        int option;
        boolean running = true;
        while (running) {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Guest Actions:");
            System.out.println("\t 1 -> Add product to receipt");
            System.out.println("\t 2 -> Remove product from receipt");
            System.out.println("\t 0 -> Finish and Register");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    addProductToGuestReceipt(guest);
                    break;
                case 2:
                    removeProductFromGuestReceipt(guest);
                    break;
                case 0:
                    running = false;
                    registerGuestAsClient(guest);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addProductToGuestReceipt(Guest guest) {
        System.out.print("Enter product code: ");
        String productCode = sc.next();
        System.out.print("Enter quantity to add: ");
        int quantityToAdd = sc.nextInt();
        Product product = productService.getProductByBarcode(productCode);
        if (product != null) {
            guest.addProductToReceipt(product, quantityToAdd);
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void removeProductFromGuestReceipt(Guest guest) {
        System.out.print("Enter product code: ");
        String productCode = sc.next();
        System.out.print("Enter quantity to remove: ");
        int quantityToRemove = sc.nextInt();
        guest.removeProductFromReceipt(productCode, quantityToRemove);
        System.out.println("Product removed successfully.");
    }

    private static boolean loginClient() {
        sc.nextLine();  // Consume the newline leftover
        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Client client = clientRepository.getClientByEmail(email);
        if (client != null && client.checkPassword(password)) {
            loggedInClient = client; // Store the logged-in client
            AuditService.getInstance().logAction("Client login: " + email);
            return true;
        } else {
            if (client == null) {
                System.out.println("No client found with the email: " + email);
            } else {
                System.out.println("Incorrect password.");
            }
            AuditService.getInstance().logAction("Failed client login: " + email);
            return false;
        }
    }


    private static void interogareBonuri() {
        if (loggedInClient == null) {
            System.out.println("No client is currently logged in.");
            return;
        }
        int option;
        while (true) {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("<Interogare Bonuri>");
            int i = 1;
            for (Reciept reciept : loggedInClient.getReceipts()) {
                System.out.println("\t " + i + " -> " + reciept.getBonID());
                i++;
            }
            System.out.println("\t 0 -> Exit Interogare");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            option = sc.nextInt();
            if (option == 0) {
                System.out.println("Exiting Interogare Bonuri.");
                break;
            } else if (option > 0 && option <= loggedInClient.getReceipts().size()) {
                Reciept selectedReciept = loggedInClient.getReceipts().get(option - 1);
                System.out.println("You selected Bon: " + selectedReciept.getBonID());
                System.out.println("Items in Bon " + selectedReciept.getBonID() + ":");
                int itemIndex = 1;
                for (var item : selectedReciept.getProductEntries()) {
                    System.out.println("\t" + itemIndex + ") Product: " + item.getKey().getBarcode() + ", Quantity: " + item.getValue());
                    itemIndex++;
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void adaugareBon() {
        if (loggedInClient == null) {
            System.out.println("No client is currently logged in.");
            return;
        }
        int option;
        Reciept newReciept = new Reciept();
        receiptService.addReceipt(newReciept);
        loggedInClient.addReceipt(newReciept);
        System.out.println("New Bon created with ID: " + newReciept.getBonID());
        AuditService.getInstance().logAction("Created new receipt for client: " + loggedInClient.getEmail());

        boolean running = true;
        while (running) {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("<Adaugare Bon>");
            System.out.println("\t 1 -> Adaugare produs pe bon");
            System.out.println("\t 2 -> Stergere produs de pe bon");
            System.out.println("\t 0 -> Inchidere Bon");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            option = sc.nextInt();
            switch (option) {
                case 1:
                    adaugareProdusPeBon(newReciept);
                    AuditService.getInstance().logAction("Added product to receipt: " + newReciept.getBonID());
                    break;
                case 2:
                    stergereProdusDePeBon(newReciept);
                    AuditService.getInstance().logAction("Removed product from receipt: " + newReciept.getBonID());
                    break;
                case 0:
                    running = false;
                    System.out.println("Inchidere Bon.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void adaugareProdusPeBon(Reciept reciept) {
        System.out.println("Adding product to the bon.");
        System.out.print("Enter product code: ");
        String productCode = sc.next();
        System.out.print("Enter quantity to add: ");
        int quantityToAdd = sc.nextInt();
        reciept.addProduct(productService.getProductByBarcode(productCode), quantityToAdd);
        System.out.println("Product added successfully.");
    }

    private static void stergereProdusDePeBon(Reciept reciept) {
        System.out.println("Removing product from the bon.");
        System.out.print("Enter product code: ");
        String productCode = sc.next();
        System.out.print("Enter quantity to remove: ");
        int quantityToRemove = sc.nextInt();
        reciept.removeProduct(productCode, quantityToRemove);
        System.out.println("Product removed successfully.");
    }

    private static void statistici() {
        if (loggedInClient == null) {
            System.out.println("No client is currently logged in.");
            return;
        }
        int option;
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Statistici");
        System.out.println("\t 1 -> In functie de suma cheltuita");
        System.out.println("\t 2 -> In functie de nr de achizitii");
        System.out.println("\t 0 -> Exit Meniu Statistici");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        option = sc.nextInt();
        switch (option) {
            case 1:
                statisticiSumaCheltuita();
                break;
            case 2:
                statisticiNrAchizitii();
                break;
            case 0:
                System.out.println("Exiting Meniu Statistici.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void statisticiSumaCheltuita() {
        if (loggedInClient == null) {
            System.out.println("No client is currently logged in.");
            return;
        }
        Map<String, Double> spendingByCategory = loggedInClient.getCategorySpending();
        System.out.println("Spending by category:");
        spendingByCategory.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> System.out.println("Category: " + entry.getKey() + ", Amount: " + entry.getValue()));
    }

    private static void statisticiNrAchizitii() {
        if (loggedInClient == null) {
            System.out.println("No client is currently logged in.");
            return;
        }
        Map<String, Integer> purchasesByCategory = loggedInClient.getCategoryPurchases();
        System.out.println("Purchases by category:");
        purchasesByCategory.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> System.out.println("Category: " + entry.getKey() + ", Count: " + entry.getValue()));
    }

    private static void detaliiCont() {
        if (loggedInClient == null) {
            System.out.println("No client is currently logged in.");
            return;
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Detalii cont");
        System.out.println("\t 1. Name: " + loggedInClient.getName());
        System.out.println("\t 2. Surname: " + loggedInClient.getSurname());
        System.out.println("\t 3. Email: " + loggedInClient.getEmail());
        System.out.println("\t 4. CNP: " + loggedInClient.getCNP());
        System.out.println("\t 5. PhoneNumber: " + loggedInClient.getPhoneNumber());
        System.out.println("\t 6. ID: " + loggedInClient.getID());
        System.out.println("\t 7. Password: " + loggedInClient.getPassword());
        System.out.println("\t 8. Register Date: " + loggedInClient.getRegisterDate());
        System.out.println("\t 9. Birth Date: " + loggedInClient.getBirthDate());
        System.out.println("\t 0 -> Exit Details");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");

        int option = sc.nextInt();
        switch (option) {
            case 5:
                System.out.print("Enter new phone number: ");
                String newPhoneNumber = sc.next();
                if (Client.validateTelephoneNumber(newPhoneNumber)) {
                    loggedInClient.setClientPhoneNumber(newPhoneNumber);
                    System.out.println("Phone number updated successfully.");
                } else {
                    System.out.println("Invalid phone number format. Please try again.");
                }
                break;
            case 7:
                System.out.print("Enter current password: ");
                String currentPassword = sc.next();
                System.out.print("Enter new password: ");
                String newPassword = sc.next();
                if (loggedInClient.checkPassword(currentPassword)) {
                    loggedInClient.setClientPassword(newPassword);
                    System.out.println("Password changed successfully.");
                } else {
                    System.out.println("Incorrect current password.");
                }
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static boolean loginEmployee() {
        sc.nextLine();  // Consume the newline leftover
        System.out.print("Enter Employee ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null && employee.checkPassword(password)) {
            loggedInEmployee = employee; // Store the logged-in employee
            return true;
        } else {
            if (employee == null) {
                System.out.println("No employee found with the ID: " + id);
            } else {
                System.out.println("Incorrect password.");
            }
            return false;
        }
    }

    private static boolean loginAdmin() {
        sc.nextLine();  // Consume the newline leftover
        System.out.print("Enter Admin ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Admin admin = adminRepository.getAdminById(id);
        if (admin != null && admin.checkPassword(password)) {
            loggedInAdmin = admin; // Store the logged-in admin
            return true;
        } else {
            if (admin == null) {
                System.out.println("No admin found with the ID: " + id);
            } else {
                System.out.println("Incorrect password.");
            }
            return false;
        }
    }

    private static void handleEmployeeActions() {
        if (loginEmployee()) {
            System.out.println("Login successful!");
            int option;
            boolean running = true;
            while (running) {
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Employee Actions:");
                System.out.println("\t 1 -> Change Product Details");
                System.out.println("\t 2 -> View Product Details");
                System.out.println("\t 0 -> Exit Employee Mode");
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                option = sc.nextInt();

                switch (option) {
                    case 1:
                        System.out.print("Enter product barcode: ");
                        String barcode = sc.next();
                        Product product = productService.getProductByBarcode(barcode);
                        if (product != null) {
                            loggedInEmployee.changeProductDetails(product, sc);
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 2: // New case for viewing product details
                        System.out.print("Enter product barcode: ");
                        barcode = sc.next();
                        product = productService.getProductByBarcode(barcode);
                        if (product != null) {
                            loggedInEmployee.visualizeProductDetails(product);
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting Employee Mode.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Login failed!");
        }
    }

    private static void handleAdminActions() {
        if (loginAdmin()) {
            System.out.println("Login successful!");
            int option;
            boolean running = true;
            while (running) {
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Admin Actions:");
                System.out.println("\t 1 -> Change Product Details");
                System.out.println("\t 2 -> Change Client Details");
                System.out.println("\t 3 -> View Product Details");
                System.out.println("\t 4 -> Add Employee");
                System.out.println("\t 5 -> Delete Employee");
                System.out.println("\t 6 -> Change Employee Details");
                System.out.println("\t 0 -> Exit Admin Mode");
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                option = sc.nextInt();

                switch (option) {
                    case 1:
                        System.out.print("Enter product barcode: ");
                        String barcode = sc.next();
                        Product product = productService.getProductByBarcode(barcode);
                        if (product != null) {
                            loggedInAdmin.changeProductDetails(product, sc);
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter client email: ");
                        String email = sc.next();
                        Client client = clientRepository.getClientByEmail(email);
                        if (client != null) {
                            loggedInAdmin.changeClientDetails(client, sc);
                        } else {
                            System.out.println("Client not found.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter product barcode: ");
                        barcode = sc.next();
                        product = productService.getProductByBarcode(barcode);
                        if (product != null) {
                            loggedInAdmin.visualizeProductDetails(product);
                        } else {
                            System.out.println("Product not found.");
                        }
                        break;
                    case 4: // Change to pass EmployeeService
                        loggedInAdmin.addEmployee(employeeService, sc);
                        break;
                    case 5: // Change to pass EmployeeService
                        loggedInAdmin.deleteEmployee(employeeService, sc);
                        break;
                    case 6:
                        System.out.print("Enter employee ID: ");
                        String employeeID = sc.next();
                        Employee employee = employeeService.getEmployeeById(employeeID);
                        if (employee != null) {
                            loggedInAdmin.changeEmployeeDetails(employee, sc);
                        } else {
                            System.out.println("Employee not found.");
                        }
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting Admin Mode.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Login failed!");
        }
    }
}
