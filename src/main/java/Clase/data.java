package Clase;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;

public class data {

    public static void initialize() {
        BonRepository bonRepository = BonRepository.getInstance();
        ClientRepository clientRepository = ClientRepository.getInstance();
        ProductRepository productRepository = ProductRepository.getInstance();
        EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
        AdminRepository adminRepository = AdminRepository.getInstance();

        // Create some products with names and stock
        Product milk = new Product("3381374195170", "Dairy", "Milk", 1.50, 100); // Price per unit, initial stock
        Product bread = new Product("3234454078736", "Bakery", "Bread", 2.00, 50);
        Product eggs = new Product("2467189881307", "Poultry", "Eggs", 3.00, 200);

        productRepository.addProduct(milk);
        productRepository.addProduct(bread);
        productRepository.addProduct(eggs);

        // Create some clients
        Client client1 = new Client("John", "Doe", LocalDate.of(1990, 5, 24), "john.doe@example.com", "1923456789005", "0712345678", "CLI001", "password123");
        Client client2 = new Client("Jane", "Smith", LocalDate.of(1988, 8, 15), "jane.smith@example.com", "2923456789006", "0798765432", "CLI002", "password456");

        clientRepository.addClient(client1);
        clientRepository.addClient(client2);

        // Create some employees
        Employee employee1 = new Employee("EMP001", "Alice", "empPassword1");
        Employee employee2 = new Employee("EMP002", "Bob", "empPassword2");

        employeeRepository.addEmployee(employee1);
        employeeRepository.addEmployee(employee2);

        // Create some admins
        Admin admin1 = new Admin("ADM001", "Charlie", "adminPassword1");
        Admin admin2 = new Admin("ADM002", "Dave", "adminPassword2");

        adminRepository.addAdmin(admin1);
        adminRepository.addAdmin(admin2);

        // Create receipts for client1
        SimpleEntry<Product, Integer> item1 = new SimpleEntry<>(milk, 2);
        SimpleEntry<Product, Integer> item2 = new SimpleEntry<>(bread, 3);
        SimpleEntry<Product, Integer> item3 = new SimpleEntry<>(eggs, 12);

        Reciept reciept1 = new Reciept("BON001", LocalDate.now(), Arrays.asList(item1, item2));
        Reciept reciept2 = new Reciept("BON002", LocalDate.now(), Arrays.asList(item2, item3));
        Reciept reciept3 = new Reciept("BON003", LocalDate.now().minusDays(1), Arrays.asList(item1, item3));

        bonRepository.addBon(reciept1);
        bonRepository.addBon(reciept2);
        bonRepository.addBon(reciept3);

        client1.addReceipt(reciept1);
        client1.addReceipt(reciept2);
        client1.addReceipt(reciept3);

        // Create receipts for client2
        SimpleEntry<Product, Integer> item4 = new SimpleEntry<>(milk, 1);
        SimpleEntry<Product, Integer> item5 = new SimpleEntry<>(bread, 2);
        SimpleEntry<Product, Integer> item6 = new SimpleEntry<>(eggs, 6);

        Reciept reciept4 = new Reciept("BON004", LocalDate.now(), Arrays.asList(item4, item5));
        Reciept reciept5 = new Reciept("BON005", LocalDate.now(), Arrays.asList(item5, item6));
        Reciept reciept6 = new Reciept("BON006", LocalDate.now().minusDays(1), Arrays.asList(item4, item6));

        bonRepository.addBon(reciept4);
        bonRepository.addBon(reciept5);
        bonRepository.addBon(reciept6);

        client2.addReceipt(reciept4);
        client2.addReceipt(reciept5);
        client2.addReceipt(reciept6);
    }
}
