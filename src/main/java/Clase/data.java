package Clase;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.AbstractMap.SimpleEntry;

public class data {


    public static void initialize() {
        BonRepository bonRepository = BonRepository.getInstance();
        ClientRepository clientRepository = ClientRepository.getInstance();
        ProductRepository productRepository = ProductRepository.getInstance();


        Product milk = new Product("3381374195170", "Dairy", 1.50);  // Assuming price is per unit
        Product bread = new Product("3234454078736", "Bakery", 2.00);
        Product eggs = new Product("2467189881307", "Poultry", 3.00);

        productRepository.addProduct(milk);
        productRepository.addProduct(bread);
        productRepository.addProduct(eggs);

        SimpleEntry<Product, Integer> item1 = new SimpleEntry<>(milk, 2);
        SimpleEntry<Product, Integer> item2 = new SimpleEntry<>(bread, 3);
        SimpleEntry<Product, Integer> item3 = new SimpleEntry<>(eggs, 12);

        // Create Bon instances
        Reciept reciept1 = new Reciept("BON001", LocalDate.now(), Arrays.asList(item1, item2));
        Reciept reciept2 = new Reciept("BON002", LocalDate.now(), Arrays.asList(item2, item3));
        Reciept reciept3 = new Reciept("BON003", LocalDate.now().minusDays(1), Arrays.asList(item1, item3));

        // Add Bons to the repository
        bonRepository.addBon(reciept1);
        bonRepository.addBon(reciept2);
        bonRepository.addBon(reciept3);

        Client client1 = new Client("John", "Doe", LocalDate.of(1990, 5, 24), "john.doe@example.com", "1923456789005", "0712345678", "CLI001", "password123");
        clientRepository.addClient(client1);
//        Client client2 = new Client("Jane", "Smith", LocalDate.of(1988, 8, 15), "jane.smith@example.com", "2923456789006", "0798765432", "CLI002", "password456");
//        ClientRepository.addClient(client2);
    }
}
