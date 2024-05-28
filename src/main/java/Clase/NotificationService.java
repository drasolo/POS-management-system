package Clase;

import java.util.HashSet;
import java.util.Set;

public class NotificationService {
    private Set<Client> subscribedClients;

    public NotificationService() {
        this.subscribedClients = new HashSet<>();
    }

    public void sendOrderConfirmation(Client client, Reciept receipt) {
        if (subscribedClients.contains(client)) {
            System.out.println("Sending order confirmation to " + client.getClientEmail());
            // Logic to send order confirmation email
        }
    }

    public void sendDiscountNotification(Client client, Product product) {
        if (subscribedClients.contains(client)) {
            System.out.println("Sending discount notification to " + client.getClientEmail());
            // Logic to send discount notification email
        }
    }

    public void sendAccountUpdates(Client client) {
        if (subscribedClients.contains(client)) {
            System.out.println("Sending account updates to " + client.getClientEmail());
            // Logic to send account updates email
        }
    }

    public void subscribe(Client client) {
        subscribedClients.add(client);
        System.out.println(client.getClientEmail() + " has subscribed to notifications.");
    }

    public void unsubscribe(Client client) {
        subscribedClients.remove(client);
        System.out.println(client.getClientEmail() + " has unsubscribed from notifications.");
    }
}
