package Clase;

import org.mindrot.jbcrypt.BCrypt;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Client {


//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    protected static LocalDate ClientRegisterDate;

    protected static String ClientID;

    protected static String ClientName;

    protected static String ClientSurname;

    protected static LocalDate ClientBirthDate;

    protected static String ClientEmail;
    protected static String ClientCNP;

    protected static String ClientPhoneNumber;

    private static String ClientPassword;

    private static String ClientHashedPassword;



    //variables

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    protected boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean validateTelephoneNumber(String phoneNumber) {
        return phoneNumber.matches("07[0-9]{8}");
    }

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        boolean hasUpper = false; // Check for at least one uppercase character
        boolean hasLower = false; // Check for at least one lowercase character
        boolean hasDigit = false; // Check for at least one digit
        boolean hasSpecial = false; // Check for at least one special character

        if (password.length() < 8) { // Check if password is at least 8 characters long
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

        // The password is valid if it has at least one uppercase letter, one lowercase letter, one digit, and one special character
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public static boolean validateCNP(String cnp) {
        if (cnp == null || !cnp.matches("\\d{13}")) {
            return false; // Validate that the CNP is exactly 13 digits long
        }

        // Validate the date components
        int year = Integer.parseInt(cnp.substring(1, 3));
        int month = Integer.parseInt(cnp.substring(3, 5));
        int day = Integer.parseInt(cnp.substring(5, 7));
        int decade = getDecadeFromCNP(cnp.charAt(0));
        if (decade == 0) {
            return false; // Invalid decade code
        }
        year += decade % 10;

        // Check if the date is valid
        try {
            LocalDate birthDate = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            return false; // Return false if the date is not valid
        }

        // Validate the checksum
        int checksumDigit = Integer.parseInt(cnp.substring(12));
        int[] constant = {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.digit(cnp.charAt(i), 10) * constant[i];
        }

        int computedChecksum = sum % 11;
        if (computedChecksum == 10) {
            computedChecksum = 1; // Special case for remainder 10
        }

        return computedChecksum == checksumDigit;
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.ClientHashedPassword);
    }


    //validators

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private static int getDecadeFromCNP(char decadeCode) {
        switch (decadeCode) {
            case '1':
            case '2':
                return 1980;
            case '3':
            case '4':
                return 1990;
            case '5':
            case '6':
                return 2000;
            case '7':
            case '8':
                return 2010; // Assuming all resident codes imply decade
            default:
                return 0; // Invalid code
        }




    }    // Helper method to determine the full year based on the CNP's decade indicator

    public static String getCNP() {
        return ClientCNP;
    }

    public static LocalDate getRegisterDate (){
        return ClientRegisterDate;
    }

    public static String getPassword(){
        return ClientPassword;
    }

    public static String getID(){
        return ClientID;
    }

    public static String getEmail() {
        return ClientEmail;
    }

    public static String getName() {
        return ClientName;
    }

    public static String getSurname() { return ClientSurname;}

    public int getAge() {
        int years = Period.between(ClientBirthDate, LocalDate.now()).getYears();
        return years;
    }
    public static LocalDate getBirthDate(){ return ClientBirthDate;}

    public static String getPhoneNumber() {
        return ClientPhoneNumber;
    }

//    public static Map<String, Double> getCategorySpending(List<Reciept> reciepts) {
//        Map<String, Double> spendingByCategory = new HashMap<>();
//        for (Reciept reciept : reciepts) {
//            for (Product product : reciept.getProducts()) { // Assuming there's a method to get all products on the bon
//                String category = product.getCategory(); // Assuming product has a getCategory method
//                double amount = product.getPrice() * product.getQuantity(); // Assuming product has getPrice and getQuantity methods
//                spendingByCategory.merge(category, amount, Double::sum);
//            }
//        }
//        return spendingByCategory;
//    }



    //getters

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void setClientPhoneNumber(String newPhoneNumber) {
        if (validateTelephoneNumber(newPhoneNumber)) {
            ClientPhoneNumber = newPhoneNumber;
            System.out.println("Phone number updated successfully.");
        } else {
            System.out.println("Invalid phone number format. Please ensure it matches the expected format.");
        }
    }

    public static void setClientPassword(String newPassword) {
        // Optional: Add password strength validation or hashing here
        if (validatePassword(newPassword)) {
            ClientPassword = newPassword;  // Set the new password
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Password does not meet the required criteria.");
        }

        //setters
    }
}


