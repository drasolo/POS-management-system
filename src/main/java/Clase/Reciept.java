package Clase;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Reciept {
    protected List<SimpleEntry<Product, Integer>> Products;
    protected String BonID;
    protected LocalDate DataCumparare;
    protected Client client; // Adăugăm referința către client

    // Getters
    public List<SimpleEntry<Product, Integer>> getProductEntries() {
        return Products;
    }

    public String getBonID() {
        return BonID;
    }

    public LocalDate getPurchaseDate() {
        return DataCumparare;
    }

    public Client getClient() { // Adăugăm metoda getClient
        return client;
    }

    public void setClient(Client client) { // Adăugăm metoda setClient
        this.client = client;
    }

    public String getId() { // Adăugăm metoda getId
        return BonID;
    }

    public void setId(String bonID) { // Adăugăm metoda setId
        this.BonID = bonID;
    }

    // Constructors
    public Reciept() {
        this.Products = new ArrayList<>();
        this.DataCumparare = LocalDate.now();
        int nextId = BonRepository.getInstance().getNextReceiptNumber();
        this.BonID = "BON" + String.format("%03d", nextId);
    }

    public Reciept(int numOfBons) {
        this();
        try {
            this.BonID = "BON" + String.format("%03d", numOfBons);
        } catch (Exception e) {
            System.err.println("Failed to format BonID: " + e.getMessage());
            this.BonID = "BON000";
        }
    }

    public Reciept(String BonID, LocalDate DataCumparare, List<SimpleEntry<Product, Integer>> tupleList) {
        this.BonID = BonID;
        this.DataCumparare = DataCumparare;
        this.Products = new ArrayList<>(tupleList);
    }

    public Reciept(String BonID, LocalDate DataCumparare) {
        this();
        this.BonID = BonID;
        this.DataCumparare = DataCumparare;
    }

    // Methods to add and remove products
    public void addProduct(Product productToVerify, int quantity) {
        boolean found = false;
        for (SimpleEntry<Product, Integer> entry : Products) {
            if (entry.getKey().equals(productToVerify)) {
                entry.setValue(entry.getValue() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            Products.add(new SimpleEntry<>(productToVerify, quantity));
        }
    }

    public void removeProduct(String productCode, int quantity) {
        for (int i = 0; i < Products.size(); i++) {
            SimpleEntry<Product, Integer> entry = Products.get(i);
            if (entry.getKey().getBarcode().equals(productCode)) {
                int newQuantity = entry.getValue() - quantity;
                if (newQuantity > 0) {
                    entry.setValue(newQuantity);
                } else {
                    Products.remove(i);
                }
                return;
            }
        }
        System.out.println("Product not found in bon.");
    }

    // Methods to get spending and purchase count by category
    public Map<String, Double> getSpendingByCategory() {
        Map<String, Double> spendingByCategory = new HashMap<>();
        for (SimpleEntry<Product, Integer> entry : Products) {
            String category = entry.getKey().getCategoryId();
            double amount = entry.getKey().getPrice() * entry.getValue();
            spendingByCategory.merge(category, amount, Double::sum);
        }
        return spendingByCategory;
    }

    public Map<String, Integer> getPurchaseCountByCategory() {
        Map<String, Integer> purchaseCountByCategory = new HashMap<>();
        for (SimpleEntry<Product, Integer> entry : Products) {
            String category = entry.getKey().getCategoryId();
            int count = entry.getValue();
            purchaseCountByCategory.merge(category, count, Integer::sum);
        }
        return purchaseCountByCategory;
    }
}
