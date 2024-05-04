package Clase;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BonRepository {
    public List<Reciept> reciepts = new CopyOnWriteArrayList<>(); // thread-safe version of ArrayList
    private static int receiptCounter = 0;  // Static counter to keep track of the number of receipts


    // Singleton pattern to ensure only one repository instance
    public static BonRepository instance = null;
    public String getNumberOfBons;

    public static BonRepository getInstance() {
        if (instance == null) {
            instance = new BonRepository();
        }
        return instance;
    }

    // Method to add a Bon
    public void addBon(Reciept reciept) {
        reciepts.add(reciept);
        receiptCounter++;

    }

    public int getNextReceiptNumber() {
        return receiptCounter + 1;  // Provide the next number for a new receipt
    }

    // Method to retrieve all Bons
    public List<Reciept> getAllBons() {
        return Collections.unmodifiableList(reciepts); // this makes the list read-only for the caller
    }

    // Method to find a Bon by ID
    public Reciept findBonById(String bonId) {
        return reciepts.stream().filter(b -> b.getBonID().equals(bonId)).findFirst().orElse(null);
    }

    // Method to remove a Bon
    public boolean removeBon(Reciept reciept) {
        return reciepts.remove(reciept);


    }
    public int getNumberOfBons() {
        return reciepts.size();  // Return the size of the bons list
    }

}
