package Clase;

import java.util.Scanner;

public class Employee {
    private String employeeID;
    private String name;
    private String password;

    public Employee(String employeeID, String name, String password) {
        this.employeeID = employeeID;
        this.name = name;
        this.password = password;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void changeProductDetails(Product product, Scanner scanner) {
        System.out.println("Modify details for Product: " + product.getBarcode());

        System.out.print("Enter new name (or press enter to keep " + product.getName() + "): ");
        scanner.nextLine(); // consume the leftover newline
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            product.setName(newName);
        }

        System.out.print("Enter quantity to add to current stock (current stock: " + product.getStock() + "): ");
        String additionalStock = scanner.nextLine();
        if (!additionalStock.isEmpty()) {
            try {
                int stockToAdd = Integer.parseInt(additionalStock);
                product.setStock(product.getStock() + stockToAdd);
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock format. Keeping old stock.");
            }
        }

        System.out.print("Enter new price (or press enter to keep " + product.getPrice() + "): ");
        String newPriceStr = scanner.nextLine();
        if (!newPriceStr.isEmpty()) {
            try {
                double newPrice = Double.parseDouble(newPriceStr);
                product.setPrice(newPrice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Keeping old price.");
            }
        }

        System.out.print("Enter new discount (or press enter to keep " + product.getDiscount() + "): ");
        String newDiscountStr = scanner.nextLine();
        if (!newDiscountStr.isEmpty()) {
            try {
                int newDiscount = Integer.parseInt(newDiscountStr);
                product.setDiscount(newDiscount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid discount format. Keeping old discount.");
            }
        }

        System.out.println("Product details updated successfully.");
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
