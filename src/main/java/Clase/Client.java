package Clase;

import org.mindrot.jbcrypt.BCrypt;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Client {

    protected LocalDate ClientRegisterDate;
    protected String ClientID;
    protected String ClientName;
    protected String ClientSurname;
    protected LocalDate ClientBirthDate;
    protected String ClientEmail;
    protected String ClientCNP;
    protected String ClientPhoneNumber;
    private String ClientPassword;
    private String ClientHashedPassword;
    private List<Reciept> receipts;

    public Client() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Name: ");
        this.ClientName = scanner.nextLine();

        System.out.print("Surname: ");
        this.ClientSurname = scanner.nextLine();

        while (true) {
            System.out.print("Birth Date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            try {
                this.ClientBirthDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("\n\t!!!Invalid date format!!!\n");
            }
        }

        System.out.print("Email: ");
        this.ClientEmail = scanner.nextLine();

        while (true) {
            System.out.print("CNP: ");
            this.ClientCNP = scanner.nextLine();
            if (!validateCNP(this.ClientCNP)) {
                System.out.println("\n\t!!!Invalid CNP structure!!!\n");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Phone Number: ");
            this.ClientPhoneNumber = scanner.nextLine();
            if (!validateTelephoneNumber(this.ClientPhoneNumber)) {
                System.out.println("\n\t!!!Invalid phone number structure!!!\n");
            } else {
                break;
            }
        }

        scanner.close(); // Note: closing the scanner will close System.in, so it should not be closed if you expect further console inputs elsewhere after this constructor.

        this.ClientID = "GeneratedID"; // Example ID generation
        this.ClientRegisterDate = LocalDate.now();
        this.ClientHashedPassword = BCrypt.hashpw("defaultPassword", BCrypt.gensalt(12));
        this.receipts = new ArrayList<>();
    }

    // Parameterized constructor
    public Client(String name, String surname, LocalDate birthDate, String email, String cnp, String phoneNumber, String ID, String password) {
        this.ClientName = name;
        this.ClientSurname = surname;
        this.ClientID = ID;
        this.ClientBirthDate = birthDate;
        this.ClientEmail = email;
        this.ClientCNP = cnp;
        this.ClientPhoneNumber = phoneNumber;
        this.ClientRegisterDate = LocalDate.now();
        this.ClientHashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.receipts = new ArrayList<>();
    }

    // Methods for handling receipts
    public void addReceipt(Reciept receipt) {
        receipts.add(receipt);
    }

    public List<Reciept> getReceipts() {
        return receipts;
    }

    public Map<String, Double> getCategorySpending() {
        Map<String, Double> spendingByCategory = new HashMap<>();
        for (Reciept receipt : receipts) {
            for (var entry : receipt.getProductEntries()) {
                String category = entry.getKey().getCategoryId();
                double amount = entry.getKey().getPrice() * entry.getValue();
                spendingByCategory.merge(category, amount, Double::sum);
            }
        }
        return spendingByCategory;
    }

    public Map<String, Integer> getCategoryPurchases() {
        Map<String, Integer> purchasesByCategory = new HashMap<>();
        for (Reciept receipt : receipts) {
            for (var entry : receipt.getProductEntries()) {
                String category = entry.getKey().getCategoryId();
                int count = entry.getValue();
                purchasesByCategory.merge(category, count, Integer::sum);
            }
        }
        return purchasesByCategory;
    }

    // Email validation
    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Phone number validation
    public static boolean validateTelephoneNumber(String phoneNumber) {
        return phoneNumber.matches("07[0-9]{8}");
    }

    // Password validation
    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        if (password.length() < 8) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    // CNP validation
    public static boolean validateCNP(String cnp) {
        if (cnp == null || !cnp.matches("\\d{13}")) {
            return false;
        }

        int year = Integer.parseInt(cnp.substring(1, 3));
        int month = Integer.parseInt(cnp.substring(3, 5));
        int day = Integer.parseInt(cnp.substring(5, 7));
        int decade = getDecadeFromCNP(cnp.charAt(0));
        if (decade == 0) {
            return false;
        }
        year += decade % 10;

        try {
            LocalDate birthDate = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            return false;
        }

        int checksumDigit = Integer.parseInt(cnp.substring(12));
        int[] constant = {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.digit(cnp.charAt(i), 10) * constant[i];
        }

        int computedChecksum = sum % 11;
        if (computedChecksum == 10) {
            computedChecksum = 1;
        }

        return computedChecksum == checksumDigit;
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.ClientHashedPassword);
    }

    private static int getDecadeFromCNP(char decadeCode) {
        switch (decadeCode) {
            case '1':
            case '2':
                return 1900;
            case '3':
            case '4':
                return 1800;
            case '5':
            case '6':
                return 2000;
            case '7':
            case '8':
                return 2000;
            default:
                return 0;
        }
    }

    // Getters
    public String getCNP() {
        return ClientCNP;
    }

    public LocalDate getRegisterDate() {
        return ClientRegisterDate;
    }

    public String getPassword() {
        return ClientPassword;
    }

    public String getID() {
        return ClientID;
    }

    public String getEmail() {
        return ClientEmail;
    }

    public String getName() {
        return ClientName;
    }

    public String getSurname() {
        return ClientSurname;
    }

    public LocalDate getBirthDate() {
        return ClientBirthDate;
    }

    public String getPhoneNumber() {
        return ClientPhoneNumber;
    }

    public String getClientID() {
        return ClientID;
    }

    public String getClientName() {
        return ClientName;
    }

    public String getClientSurname() {
        return ClientSurname;
    }

    public LocalDate getClientBirthDate() {
        return ClientBirthDate;
    }

    public String getClientEmail() {
        return ClientEmail;
    }

    public String getClientCNP() {
        return ClientCNP;
    }

    public String getClientPhoneNumber() {
        return ClientPhoneNumber;
    }

    public String getClientPassword() {
        return ClientPassword;
    }

    public LocalDate getClientRegisterDate() {
        return ClientRegisterDate;
    }

    public int getAge() {
        return Period.between(ClientBirthDate, LocalDate.now()).getYears();
    }

    // Setters
    public void setClientPhoneNumber(String newPhoneNumber) {
        if (validateTelephoneNumber(newPhoneNumber)) {
            ClientPhoneNumber = newPhoneNumber;
            System.out.println("Phone number updated successfully.");
        } else {
            System.out.println("Invalid phone number format. Please ensure it matches the expected format.");
        }
    }

    public void setClientPassword(String newPassword) {
        if (validatePassword(newPassword)) {
            ClientPassword = newPassword;
            this.ClientHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12)); // Also update the hashed password
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Password does not meet the required criteria.");
        }
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public void setClientSurname(String clientSurname) {
        ClientSurname = clientSurname;
    }

    public void setClientEmail(String clientEmail) {
        ClientEmail = clientEmail;
    }
}
