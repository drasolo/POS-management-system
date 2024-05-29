package Clase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class ReceiptService {
    public void addReceipt(Reciept receipt) {
        String query = "INSERT INTO Receipt (client_id, purchase_date) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, Integer.parseInt(receipt.getClient().getID()));
            statement.setDate(2, java.sql.Date.valueOf(receipt.getPurchaseDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                receipt.setId("BON" + String.format("%03d", generatedId)); // Correctly format the BonID
            }
            addProductsToReceipt(receipt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addProductsToReceipt(Reciept receipt) {
        String query = "INSERT INTO Receipt_Product (receipt_id, product_barcode, quantity) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (var entry : receipt.getProductEntries()) {
                statement.setString(1, receipt.getId()); // Set the ID as string
                statement.setString(2, entry.getKey().getBarcode());
                statement.setInt(3, entry.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other methods like getReceiptById, getProductsForReceipt, getAllReceipts, updateReceipt, deleteProductsFromReceipt, and deleteReceipt
    // can be uncommented and modified similarly to handle the correct data types and method calls.

    // Example of corrected getReceiptById method
    public Reciept getReceiptById(int id) {
        String query = "SELECT * FROM Receipt WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = ClientService.getClientById(resultSet.getInt("client_id"));
                List<SimpleEntry<Product, Integer>> products = getProductsForReceipt(id);
                return new Reciept("BON" + String.format("%03d", id), resultSet.getDate("purchase_date").toLocalDate(), products);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<SimpleEntry<Product, Integer>> getProductsForReceipt(int receiptId) {
        List<SimpleEntry<Product, Integer>> products = new ArrayList<>();
        String query = "SELECT * FROM Receipt_Product WHERE receipt_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, receiptId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = ProductService.getProductByBarcode(resultSet.getString("product_barcode"));
                products.add(new SimpleEntry<>(product, resultSet.getInt("quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Other methods can follow similar adjustments
}
