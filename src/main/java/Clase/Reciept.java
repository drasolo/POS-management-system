package Clase;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class Reciept {
    protected List<SimpleEntry<Product, Integer>> products ;//Name of the product and its quantity bought on that reciept
    protected String BonID;
    protected LocalDate DataCumparare;

    // Getter for tupleList
    public List<SimpleEntry<Product, Integer>> getTupleList() {
        return products;
    }

    // Getter for BonID
    public String getBonID() {
        return BonID;
    }

    // Getter for DataCumparare
    public LocalDate getDataCumparare() {
        return DataCumparare;
    }

    // Constructors
    public Reciept() {
        this.products = new ArrayList<>();
        this.DataCumparare = LocalDate.now();
        int nextId = BonRepository.getInstance().getNextReceiptNumber();
        this.BonID = "BON" + String.format("%03d", nextId); // Use the next receipt number for BonID
    }

    public Reciept(int numOfBons) {
        this(); // Initialize default values
        try {
            this.BonID = "BON" + String.format("%03d", numOfBons);
        } catch (Exception e) {
            System.err.println("Failed to format BonID: " + e.getMessage());
            this.BonID = "BON000"; // Fallback BonID
        }
    }

    public Reciept(String BonID, LocalDate DataCumparare, List<SimpleEntry<Product, Integer>> tupleList) {
        this.BonID = BonID;
        this.DataCumparare = DataCumparare;
        this.products = new ArrayList<>(tupleList); // Ensure a new list is created from the provided list
    }

    public Reciept(String BonID, LocalDate DataCumparare) {
        this();
        this.BonID = BonID;
        this.DataCumparare = DataCumparare;
    }




    public void addProduct(Product productToVerify, int quantity) {
        boolean found = false;
        for (SimpleEntry<Product, Integer> entry : products) {
            if (entry.getKey().equals(productToVerify)) {
                entry.setValue(entry.getValue() + quantity); // Update existing product quantity
                found = true;
                break;
            }
        }
        if (!found) {
            products.add(new SimpleEntry<>(productToVerify, quantity)); // Add new product if not found
        }
    }


    public void removeProduct(String productCode, int quantity) {
        for (int i = 0; i < products.size(); i++) {
            SimpleEntry<Product, Integer> entry = products.get(i);
            if (entry.getKey().equals(productCode)) {
                int newQuantity = entry.getValue() - quantity;
                if (newQuantity > 0) {
                    entry.setValue(newQuantity); // Update the quantity
                } else {
                    products.remove(i); // Remove the product if the new quantity is zero or less
                }
                return; // Exit after processing the found product
            }
        }
        System.out.println("Product not found in bon."); // Product was not found
    }






}
