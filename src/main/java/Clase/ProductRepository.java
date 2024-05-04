package Clase;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {

    private static ProductRepository instance = null;
    private static Map<String, Product> products; // Non-static to be instance-specific

    private ProductRepository() {
        products = new HashMap<>(); // Ensure products is initialized when instance is created
    }

    public static synchronized ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    // Method to get a Product by its barcode
    public static Product getProductByBarcode(String barcode) {
        return products.get(barcode);
    }

    // Method to add a Product to the repository
    public void addProduct(Product product) {
        products.put(product.getBarcode(), product);
    }
}
