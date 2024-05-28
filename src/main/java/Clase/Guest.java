package Clase;

import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Guest {
    private Reciept currentReciept;

    public Guest() {
        this.currentReciept = new Reciept();
    }

    public Reciept getCurrentReciept() {
        return currentReciept;
    }

    public void addProductToReceipt(Product product, int quantity) {
        currentReciept.addProduct(product, quantity);
    }

    public void removeProductFromReceipt(String productCode, int quantity) {
        currentReciept.removeProduct(productCode, quantity);
    }

    public Client registerAsClient(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your CNP: ");
        String cnp = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter your desired password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your birth date (YYYY-MM-DD): ");
        String birthDateStr = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(birthDateStr);

        Client newClient = new Client(name, surname, birthDate, email, cnp, phoneNumber, generateClientID(), password);
        newClient.addReceipt(currentReciept);

        return newClient;
    }

    private String generateClientID() {
        return "CLI" + String.format("%03d", ClientRepository.getInstance().getAllClients().size() + 1);
    }
}
