package Clase;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    private List<Client> clients;

    private ClientRepository() {
        clients = new ArrayList<>();
    }

    private static ClientRepository instance;

    public static ClientRepository getInstance() {
        if (instance == null) {
            instance = new ClientRepository();
        }
        return instance;
    }//instatierea Clasei ClientRepository

    public void addClient(Client client) {
        clients.add(client);
    }

    public Client getClientByEmail(String email) {
        System.out.println(email);

        for (Client client : clients) {
            if (client.getEmail().equalsIgnoreCase(email.trim())) {  // Correct use of instance method
                return client;
            }
        }
        return null;  // Return null if no client is found
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

}
