package org.example;

import Clase.*;


import java.util.*;  // Wildcard import for all classes in java.util
import java.util.Scanner;

public class Main {
    static BonRepository bonrepository = BonRepository.getInstance();
    static ClientRepository clientRepository = ClientRepository.getInstance();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {


        data.initialize();



        int optiune;
        boolean ok=true;

        while(ok){
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Take action as:");
            System.out.println("\t 1 -> Client");
            System.out.println("\t 2 -> Employee");
            System.out.println("\t 3 -> Unknown");
            System.out.println("\t 4 -> Admin");
            System.out.println("\t 0 -> Exit Program");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");//Meniu Principal

            while(true) {
                try {
                    System.out.print("Optiune: ");
                    optiune = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("\n\t!!!Trebuie sa introduceti un numar intreg!!!\n\n");
                    sc.next();
                }
            }//Citire optiune

            switch(optiune){
                case 1 ->{

                    int optiune1;
                    boolean ok1 = true;
                    //variabile initiate

                    if (login()) {
                        System.out.println("Login successful!");
                        // Proceed with client-specific actions



                        while(ok1){



                            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println("Esti in modul Client");
                            System.out.println("\t 1 -> Interogare Bonuri");
                            System.out.println("\t 2 -> Adaugare Bon");         //Incepe sa scanezi
                            System.out.println("\t 3 -> Statistici");
                            System.out.println("\t 4 -> Detalii cont");
                            System.out.println("\t 0 -> Exit Client");
                            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");//Meniu 1. Client

                            while(true) {
                                try {
                                    System.out.print("Optiune: ");
                                    optiune1 = sc.nextInt();
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.print("\n\t!!!Trebuie sa introduceti un numar intreg!!!\n\n");
                                    sc.next();
                                }
                            }//Citire optiune

                            switch (optiune1)
                            {
                                case 1 -> {
                                    int optiune11;

                                    while(true){
                                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                        System.out.println("<Interogare Bonuri>");
                                        int i = 1;
                                        for (Reciept reciept : bonrepository.reciepts) {
                                            System.out.println("\t " + i + " -> " + reciept.getBonID());
                                            i += 1;
                                        }
                                        System.out.println("\t 0 -> Exit Interogare");
                                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");//Meniu Interogare bonuri

                                        while(true) {
                                            try {
                                                System.out.print("Optiune: ");
                                                optiune11 = sc.nextInt();
                                                break;
                                            } catch (InputMismatchException e) {
                                                System.out.print("\n\t!!!Trebuie sa introduceti un numar intreg!!!\n\n");
                                                sc.next();
                                            }
                                        }//Introducere optiune11


                                        if (optiune11 == 0) {
                                            System.out.println("\n Exiting Products from Bon");
                                            break;
                                        }// Exit the current loop
                                        else if (optiune11 > 0 && optiune11 <= bonrepository.reciepts.size()) {
                                            Reciept selectedReciept = bonrepository.reciepts.get(optiune11 - 1); // Getting the selected bon
                                            System.out.println("You selected Bon: " + selectedReciept.getBonID());

                                            // Display items inside the selected Bon
                                            System.out.println("Items in Bon " + selectedReciept.getBonID() + ":");
                                            int itemIndex = 1;
                                            for (var item : selectedReciept.getTupleList()) { // Assuming getTupleList() returns a list of SimpleEntry objects
                                                System.out.println("\t" + itemIndex + ") Product: " + item.getKey().getBarcode() + ", Quantity: " + item.getValue());
                                                itemIndex++;
                                            }
                                        }// 1.1.1 Interogare Produse de pe bon
                                        else {
                                            System.out.println("\n\t!!!Invalid option!!!\n\n");
                                        }
                                        //

                                    } // Interogare Bonuri while

                                }//1.1 Interogare Bonuri
                                //to do
                                //afisarea bonurilor sa fie pe luna
                                //sa se scrie totalul bonului la interogarea unuia
                                //


                                case 2 -> {
                                    int optiune12;
                                    boolean ok12 = true;
                                    Reciept selectedReciept = null;
                                    Reciept newReciept = new Reciept();  // Automatically generates a new ID with the next sequence number
                                    BonRepository.getInstance().addBon(newReciept);  // This also increments the counter
                                    selectedReciept = newReciept;  // Set the newly created Bon as the selected Bon for further operations
                                    System.out.println("New Bon created with ID: " + newReciept.getBonID());


//                                de adaugat nextId pentru metoda

//                                public static String generateBonID() {
//                                    return "BON" + nextId++;  // Increment and return the next available ID
//                                }

                                    while (ok12){
                                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                        System.out.println("<Adaugare Bon>");
                                        System.out.println("\t 1 -> Adaugare produs pe bon");
                                        System.out.println("\t 2 -> Stergere prdus de pe bon");
                                        System.out.println("\t 0 -> Inchidere Bon");

                                        while(true) {
                                            try {
                                                System.out.print("Optiune: ");
                                                optiune12 = sc.nextInt();
                                                break;
                                            } catch (InputMismatchException e) {
                                                System.out.print("\n\t!!!Trebuie sa introduceti un numar intreg!!!\n\n");
                                                sc.next();
                                            }
                                        }//Reading option for <Adaugare Bon>

                                        switch (optiune12) {
                                            case 1 -> {  // Add product to bon
                                                System.out.println("Adding product to the bon.");
                                                System.out.print("Enter product code: ");
                                                String productCode = sc.next();                     //Reading BonID
                                                System.out.print("Enter quantity to add: ");
                                                int quantityToAdd;
                                                while (true) {
                                                    try {
                                                        quantityToAdd = sc.nextInt();
                                                        break;
                                                    } catch (InputMismatchException e) {
                                                        System.out.print("\n\t!!!Please enter an integer for quantity!!!\n\n");
                                                        sc.next();  // Consume the wrong input
                                                    }
                                                }//Reading Quantity

                                                selectedReciept.addProduct(ProductRepository.getProductByBarcode(productCode), quantityToAdd);  // Assuming selectedBon is your current bon object
                                                System.out.println("Product added successfully.");
                                            }//Adding Produs to Bon
                                            case 2 -> {  // Remove product from bon
                                                System.out.println("Removing product from the bon.");
                                                System.out.print("Enter product code: ");
                                                String productCode = sc.next();                     //Reading BonID
                                                System.out.print("Enter quantity to remove: ");
                                                int quantityToRemove;

                                                while (true) {
                                                    try {
                                                        quantityToRemove = sc.nextInt();
                                                        if (quantityToRemove > 0) {
                                                            break;
                                                        } else {
                                                            System.out.print("\n\t!!!Quantity should be greater than zero!!!\n\n");
                                                        }
                                                    } catch (InputMismatchException e) {
                                                        System.out.print("\n\t!!!Please enter an integer for quantity!!!\n\n");
                                                        sc.next();  // Consume the wrong input
                                                    }
                                                }//Reading Quantity

                                                // Here you would call a method to remove a product from the bon, e.g.,
                                                selectedReciept.removeProduct(productCode, quantityToRemove);  // Assuming selectedBon is your current bon object
                                                System.out.println("Product removed successfully.");
                                            }//Removing Produs from Bon
                                            case 0 -> {
                                                System.out.println("\nExiting Modification Menu");

                                                ok12 = false;
                                            }// Exit modification menu
                                        }



                                    }//<Adaugare Bon>
                                }//1.2 Adaugare Bon  (Practic Clientul se apuca de scanat)
                                //to dos
                                //de modificat generarea de bonID ca se vada si la <Interogare Bonuri>


                                case 3 -> {

                                    int optiune13;

                                    System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
                                    System.out.println("1.3 Statistici");
                                    System.out.println("\t 1 -> In functie de suma cheltuita");
                                    System.out.println("\t 2 -> In functie de nr de achizitii");//adica de cate ori ai cumparat produse dintr-o gama
                                    System.out.println("\t 0 -> Exit Meniu Statistici");
                                    System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
                                    //Meniu
                                    //Mainly se vor afisa categoriile

                                    while(true) {
                                        try {
                                            System.out.print("Optiune: ");
                                            optiune13 = sc.nextInt();
                                            break;
                                        } catch (InputMismatchException e) {
                                            System.out.print("\n\t!!!Trebuie sa introduceti un numar intreg!!!\n\n");
                                            sc.next();
                                        }
                                    }//Citire optiune

                                    switch (optiune13){
                                        case 1 ->{
                                            //Parse all the products from the current client and rank first 10 categories that have the most money spent on
                                        }

                                        case 2 ->{}

                                        case 0 ->{
                                            System.out.println("\n Exiting Statistici");
                                            break;
                                        }

                                        default -> System.out.println("Optiune invalida. Va rugam sa alegeti 1 sau 2.");
                                    }



                                }//1.3 Statistici

                                case 4 -> {
                                    System.out.println("------------------------------------------------------------------------------------------------------------------------------");
                                    System.out.println("Detalii cont");
                                    System.out.println("\t 1.Name: " + Client.getName());
                                    System.out.println("\t 2.Surname: " + Client.getSurname());
                                    System.out.println("\t 3.Email: " + Client.getEmail());
                                    System.out.println("\t 4.CNP: " + Client.getCNP());
                                    System.out.println("\t 5.PhoneNumber: " + Client.getPhoneNumber());
                                    System.out.println("\t 6.ID: " + Client.getID());
                                    System.out.println("\t 7.Password: " + Client.getPassword());
                                    System.out.println("\t 8.Register Date: " + Client.getRegisterDate());
                                    System.out.println("\t 9.Birth Date: " + Client.getBirthDate());
                                    System.out.println("\t 0 -> Exit Details");
                                    System.out.println("------------------------------------------------------------------------------------------------------------------------------");
                                    //Meniu 1.4


                                    int optiune14;
                                    //variabile initiate

                                    while(true) {
                                        try {
                                            System.out.print("Optiune: ");
                                            optiune14 = sc.nextInt();
                                            break;
                                        } catch (InputMismatchException e) {
                                            System.out.print("\n\t!!!Trebuie sa introduceti un numar intreg!!!\n\n");
                                            sc.next();
                                        }
                                    }//Citire optiune

                                    switch (optiune14) {
                                        case 5 -> {
                                            // Option to change the phone number
                                            System.out.print("Enter new phone number: ");
                                            String newPhoneNumber;
                                            try {
                                                System.out.print("newPhoneNumber: ");
                                                newPhoneNumber = sc.nextLine();

                                                if (Client.validateTelephoneNumber(newPhoneNumber)) {
                                                    Client.setClientPhoneNumber(newPhoneNumber);
                                                    System.out.println("Phone number updated successfully.");
                                                } else {
                                                    System.out.println("Invalid phone number format. Please try again.");
                                                }
                                                break;
                                            } catch (InputMismatchException e) {
                                                System.out.print("\n\t!!!Trebuie sa introduceti un numar intreg!!!\n\n");
                                                sc.next();
                                            }

                                        }
                                        case 7 -> {
                                            // Option to change the password
                                            System.out.print("Enter current password: ");
                                            String currentPassword = sc.nextLine();
                                            System.out.print("Enter new password: ");
                                            String newPassword = sc.nextLine();
                                            if (currentPassword.equals(Client.getPassword())) {
                                                Client.setClientPassword(newPassword);
                                                System.out.println("Password changed successfully.");
                                            } else {
                                                System.out.println("Incorrect current password.");
                                            }
                                        }
                                        default -> {
                                            System.out.println("Nu poti schimba acest detaliu");
                                        }
                                    }




//
//                                Number to change:


                                }//1.4 Detalii Cont

                                case 0 -> {
                                    System.out.println("\n Exiting Client");
                                    ok1=false;
                                    break;
                                }//1.0 Exiting Client
                            }
                        } //while client



                    }
                    else {
                        System.out.println("Login failed!");
                    }


                }//1. Client

                case 2 ->{
                    int ceva2;


                    //Parola:
                    //-----------------------------------------------------------
                    //1 -> Adaugare Produs In Magazin
                    //2 -> Eliminare Produs In Magazin
                    //3 -> Modificare Stoc
                    //4 -> Adaugare Categorie
                    //5 -> Eliminare Categorie
                    //6 -> Interogare Date Clienti
                    //7 -> Modificare Date Produs

//                        1.1
//                        Nume Produs:
//                        Categorie:
//                        Barcode:
//                        Pret:
//                        Discount:
//                        Stoc:


//                        1.2
//                        1 -> Cauta Produsul:
//                        2 -> Sau sterge produsul dupa barcode:

//                        1.3
//                        Nr. Bucati venite in depozit:
//                        1 -> Cauta Produsul:
//                        2 -> Sau adauga in stoc dupa barcode:

//                        1.4
//                        Adauga Categorie:
//                        Suitable for minors? y/n

//                        1.5
//                        1 -> Elimina Categorie:
//                        2 -> Cauta Categorie

//                        1.6
//                        1 -> Client name - Client ID;
//                        2 -> Client name - Client ID;

//                            1.6.x
//                                1.1;
//                                2.3;
//                                2.4;


//                        1.7
//                        1 -> Produs name - Produs ID;
//                        2 -> Produs name - Produs ID;
//                        3 -> Produs name - Produs ID;
//                        4 -> Produs name - Produs ID;


//                            1.7.x
//                              Nume Produs:
//                              Categorie:
//                              Barcode:
//                              Pret:
//                              Discount:
//                              Stoc:



                }//2. Employee

                case 3 ->{
                    int ceva3;

//                    1 -> Devino membru;
//                    2 -> Adauga bon;

                }//3. Unknown

                case 4 ->{
                    int ceva4;

//                    idk what to do
//                    1 -> Change/See users passwords


                }//4. Admin

                case 0 ->{
                    System.out.println("\nMultumesc pentru ca mi-ati folosit programul, o zi buna! :)");
                    ok=false;
                    break;
                }//0. Exit program

            }//main switch
        }


    }


    private static boolean login() {
        sc.nextLine();  // Consume the newline leftover
        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();  // Trim the input to remove any accidental whitespace

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Client client = clientRepository.getClientByEmail(email);
        if (client != null && client.checkPassword(password)) {
            return true;
        } else {
            if (client == null) {
                System.out.println("No client found with the email: " + email);
            } else {
                System.out.println("Incorrect password.");
            }
            return false;
        }
    }


}