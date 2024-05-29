package Clase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
//    public void addProduct(Product product) {
//        String query = "INSERT INTO Product (barcode, name, category_id, price, stock, discount) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, product.getBarcode());
//            statement.setString(2, product.getName());
//            statement.setString(3, product.getCategoryId());
//            statement.setDouble(4, product.getPrice());
//            statement.setInt(5, product.getStock());
//            statement.setInt(6, product.getDiscount());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
    public static Product getProductByBarcode(String barcode) {
        String query = "SELECT * FROM Product WHERE barcode = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, barcode);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Product(
                        resultSet.getString("barcode"),
                        resultSet.getString("name"),
                        resultSet.getString("category_id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock"),
                        resultSet.getInt("discount")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//
//    public List<Product> getAllProducts() {
//        List<Product> products = new ArrayList<>();
//        String query = "SELECT * FROM Product";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            while (resultSet.next()) {
//                products.add(new Product(
//                        resultSet.getString("barcode"),
//                        resultSet.getString("name"),
//                        resultSet.getString("category_id"),
//                        resultSet.getDouble("price"),
//                        resultSet.getInt("stock"),
//                        resultSet.getInt("discount")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return products;
//    }
//
//    public void updateProduct(Product product) {
//        String query = "UPDATE Product SET name = ?, category_id = ?, price = ?, stock = ?, discount = ? WHERE barcode = ?";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, product.getName());
//            statement.setString(2, product.getCategoryId());
//            statement.setDouble(3, product.getPrice());
//            statement.setInt(4, product.getStock());
//            statement.setInt(5, product.getDiscount());
//            statement.setString(6, product.getBarcode());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteProduct(String barcode) {
//        String query = "DELETE FROM Product WHERE barcode = ?";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, barcode);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
