package Clase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    public void addClient(Client client) {
        String query = "INSERT INTO clients (idClients, nameClients, surnameClients, birthdateClients, emailClients, cnpClients, phone_numClients, hashed_passwordClients, register_dateClients) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getClientID());
            statement.setString(2, client.getClientName());
            statement.setString(3, client.getClientSurname());
            statement.setDate(4, java.sql.Date.valueOf(client.getClientBirthDate()));
            statement.setString(5, client.getClientEmail());
            statement.setString(6, client.getClientCNP());
            statement.setString(7, client.getClientPhoneNumber());
            statement.setString(8, client.getClientPassword()); // Assuming this is the hashed password
            statement.setDate(9, java.sql.Date.valueOf(client.getClientRegisterDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Client getClientById(int id) {
        String query = "SELECT * FROM clients WHERE idClients = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Client(
                        resultSet.getString("nameClients"),
                        resultSet.getString("surnameClients"),
                        resultSet.getDate("birthdateClients").toLocalDate(),
                        resultSet.getString("emailClients"),
                        resultSet.getString("cnpClients"),
                        resultSet.getString("phone_numClients"),
                        resultSet.getString("idClients"),
                        resultSet.getString("hashed_passwordClients")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getString("nameClients"),
                        resultSet.getString("surnameClients"),
                        resultSet.getDate("birthdateClients").toLocalDate(),
                        resultSet.getString("emailClients"),
                        resultSet.getString("cnpClients"),
                        resultSet.getString("phone_numClients"),
                        resultSet.getString("idClients"),
                        resultSet.getString("hashed_passwordClients")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public void updateClient(Client client) {
        String query = "UPDATE clients SET nameClients = ?, surnameClients = ?, birthdateClients = ?, emailClients = ?, cnpClients = ?, phone_numClients = ?, hashed_passwordClients = ?, register_dateClients = ? WHERE idClients = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getClientName());
            statement.setString(2, client.getClientSurname());
            statement.setDate(3, java.sql.Date.valueOf(client.getClientBirthDate()));
            statement.setString(4, client.getClientEmail());
            statement.setString(5, client.getClientCNP());
            statement.setString(6, client.getClientPhoneNumber());
            statement.setString(7, client.getClientPassword()); // Assuming this is the hashed password
            statement.setDate(8, java.sql.Date.valueOf(client.getClientRegisterDate()));
            statement.setString(9, client.getClientID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int id) {
        String query = "DELETE FROM clients WHERE idClients = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
