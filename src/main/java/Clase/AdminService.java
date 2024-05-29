package Clase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    public void addAdmin(Admin admin) {
        String query = "INSERT INTO admins (idAdmins, nameAdmins, hashed_passwordAdmins) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getEmployeeID());
            statement.setString(2, admin.getName());
            statement.setString(3, admin.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Admin getAdminById(String id) {
        String query = "SELECT * FROM admins WHERE idAdmins = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Admin(
                        resultSet.getString("idAdmins"),
                        resultSet.getString("nameAdmins"),
                        resultSet.getString("hashed_passwordAdmins")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admins";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                admins.add(new Admin(
                        resultSet.getString("idAdmins"),
                        resultSet.getString("nameAdmins"),
                        resultSet.getString("hashed_passwordAdmins")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public void updateAdmin(Admin admin) {
        String query = "UPDATE admins SET nameAdmins = ?, hashed_passwordAdmins = ? WHERE idAdmins = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getPassword());
            statement.setString(3, admin.getEmployeeID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAdmin(String id) {
        String query = "DELETE FROM admins WHERE idAdmins = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
